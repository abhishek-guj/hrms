package com.roima.hrms.services;

import com.roima.hrms.dtos.AchievementPostDto;
import com.roima.hrms.dtos.AchievementPostRequestDto;
import com.roima.hrms.dtos.CommentDto;
import com.roima.hrms.dtos.CommentRequestDto;
import com.roima.hrms.entities.*;
import com.roima.hrms.exceptions.EmployeeNotFoundException;
import com.roima.hrms.repository.*;
import com.roima.hrms.utils.RoleUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AchievementPostService {

    private static final int RECENT_LIKERS_LIMIT = 5;

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final PostLikeRepository postLikeRepository;
    private final TagRepository tagRepository;
    private final PostTagRepository postTagRepository;
    private final EmployeeProfileRepository employeeProfileRepository;
    private final RoleUtil roleUtil;
    private final EmailService emailService;

    public AchievementPostService(PostRepository postRepository,
            CommentRepository commentRepository,
            PostLikeRepository postLikeRepository,
            TagRepository tagRepository,
            PostTagRepository postTagRepository,
            EmployeeProfileRepository employeeProfileRepository,
            RoleUtil roleUtil,
            EmailService emailService) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.postLikeRepository = postLikeRepository;
        this.tagRepository = tagRepository;
        this.postTagRepository = postTagRepository;
        this.employeeProfileRepository = employeeProfileRepository;
        this.roleUtil = roleUtil;
        this.emailService = emailService;
    }

    // ─────────────────────────────────────────────
    // POST CRUD
    // ─────────────────────────────────────────────

    /** Create an achievement post by the current user. */
    @Transactional
    public AchievementPostDto createPost(AchievementPostRequestDto req) {
        EmployeeProfile author = roleUtil.getCurrentEmployee();

        Post post = new Post();
        post.setAuthorId(author.getId());
        post.setTitle(req.getTitle());
        post.setText(req.getDescription());
        post.setCreatedDate(LocalDate.now());
        post.setUpdateDate(LocalDate.now());
        post.setIsSystemGenerated(false);
        post.setIsDeleted(false);
        post.setDeletedBy(null);
        post.setDeleteReason(null);
        post.setVisibleToAll(req.getVisibleToAll() != null ? req.getVisibleToAll() : true);
        // postType / mediaType – using 1L as default "achievement" type
        post.setPostType(1L);
        post.setMediaType(0L);

        post = postRepository.save(post);
        attachTags(post, req.getTags());
        return toDto(post, author.getId());
    }

    /** Get paginated achievement feed (reverse chronological). */
    public Page<AchievementPostDto> getFeed(int page, int size) {
        Long currentEmployeeId = roleUtil.getCurrentEmployeeId();
        return postRepository.findAllActivePosts(PageRequest.of(page, size))
                .map(p -> toDto(p, currentEmployeeId));
    }

    /** Get posts filtered by author, tag, and/or date range. */
    public Page<AchievementPostDto> getFilteredFeed(Long authorId, String tag,
            LocalDate from, LocalDate to,
            int page, int size) {
        Long currentEmployeeId = roleUtil.getCurrentEmployeeId();
        PageRequest pageable = PageRequest.of(page, size);
        Page<Post> posts;

        if (tag != null && !tag.isBlank()) {
            posts = postRepository.findByTagName(tag, pageable);
        } else if (authorId != null && from != null && to != null) {
            posts = postRepository.findByAuthorIdAndDateRange(authorId, from, to, pageable);
        } else if (authorId != null) {
            posts = postRepository.findByAuthorId(authorId, pageable);
        } else if (from != null && to != null) {
            posts = postRepository.findByDateRange(from, to, pageable);
        } else {
            posts = postRepository.findAllActivePosts(pageable);
        }

        return posts.map(p -> toDto(p, currentEmployeeId));
    }

    /** Get a single post by ID. */
    public AchievementPostDto getPostById(Long postId) {
        Post post = getActivePost(postId);
        return toDto(post, roleUtil.getCurrentEmployeeId());
    }

    /** Edit an existing post – only the author can edit. */
    @Transactional
    public AchievementPostDto editPost(Long postId, AchievementPostRequestDto req) {
        Post post = getActivePost(postId);
        Long currentEmployeeId = roleUtil.getCurrentEmployeeId();

        if (!post.getAuthorId().equals(currentEmployeeId)) {
            throw new SecurityException("Only the author can edit this post");
        }

        post.setTitle(req.getTitle());
        post.setText(req.getDescription());
        post.setUpdateDate(LocalDate.now());
        if (req.getVisibleToAll() != null) {
            post.setVisibleToAll(req.getVisibleToAll());
        }

        // Replace tags
        postTagRepository.deleteAll(post.getPostTags());
        post.getPostTags().clear();
        post = postRepository.save(post);
        attachTags(post, req.getTags());

        return toDto(post, currentEmployeeId);
    }

    /**
     * Delete a post.
     * – Author can delete their own posts.
     * – HR can delete any post (inappropriate content removal → warning email
     * sent).
     */
    @Transactional
    public void deletePost(Long postId, String reason) {
        Post post = getActivePost(postId);
        Long currentEmployeeId = roleUtil.getCurrentEmployeeId();
        boolean isHr = roleUtil.isHr() || roleUtil.isAdmin();

        if (!isHr && !post.getAuthorId().equals(currentEmployeeId)) {
            throw new SecurityException("You are not authorized to delete this post");
        }

        post.setIsDeleted(true);
        post.setDeletedBy(currentEmployeeId);
        post.setDeleteReason(reason != null ? reason : "");
        postRepository.save(post);

        // If HR deletes someone else's post → send warning email
        if (isHr && !post.getAuthorId().equals(currentEmployeeId)) {
            EmployeeProfile author = employeeProfileRepository.findById(post.getAuthorId())
                    .orElse(null);
            if (author != null && author.getUser() != null) {
                emailService.sendContentWarningEmail(
                        author.getUser().getEmail(),
                        author.getFirstName() + " " + author.getLastName(),
                        "post",
                        post.getTitle(),
                        reason);
            }
        }
    }

    // ─────────────────────────────────────────────
    // LIKES
    // ─────────────────────────────────────────────

    /** Toggle like/unlike on a post. Returns current like count. */
    @Transactional
    public Long toggleLike(Long postId) {
        getActivePost(postId); // validate exists
        Long currentEmployeeId = roleUtil.getCurrentEmployeeId();

        if (postLikeRepository.existsByPostIdAndLikedById(postId, currentEmployeeId)) {
            // Unlike
            PostLike existing = postLikeRepository.findByPostIdAndLikedById(postId, currentEmployeeId)
                    .orElseThrow();
            postLikeRepository.delete(existing);
        } else {
            // Like
            PostLike like = new PostLike();
            like.setPostId(postId);
            like.setLikedById(currentEmployeeId);
            like.setUpdateOn(Instant.now());
            postLikeRepository.save(like);
        }
        return postLikeRepository.countByPostId(postId);
    }

    // ─────────────────────────────────────────────
    // COMMENTS
    // ─────────────────────────────────────────────

    /** Add a comment to a post. */
    @Transactional
    public CommentDto addComment(Long postId, CommentRequestDto req) {
        getActivePost(postId); // validate exists
        Long currentEmployeeId = roleUtil.getCurrentEmployeeId();

        Comment comment = new Comment();
        comment.setPostId(postId);
        comment.setCommentedById(currentEmployeeId);
        comment.setCommentText(req.getCommentText());
        comment.setParentCommentId(req.getParentCommentId());
        comment.setCommentedOn(Instant.now());
        comment.setUpdatedOn(Instant.now());
        comment.setIsDeleted(false);

        comment = commentRepository.save(comment);
        return toCommentDto(comment);
    }

    /** Edit a comment – only the author can edit. */
    @Transactional
    public CommentDto editComment(Long commentId, CommentRequestDto req) {
        Comment comment = getActiveComment(commentId);
        Long currentEmployeeId = roleUtil.getCurrentEmployeeId();

        if (!comment.getCommentedById().equals(currentEmployeeId)) {
            throw new SecurityException("Only the author can edit this comment");
        }

        comment.setCommentText(req.getCommentText());
        comment.setUpdatedOn(Instant.now());
        comment = commentRepository.save(comment);
        return toCommentDto(comment);
    }

    /**
     * Delete a comment.
     * – Author can delete their own comments.
     * – HR can delete any comment → warning email sent.
     */
    @Transactional
    public void deleteComment(Long commentId, String reason) {
        Comment comment = getActiveComment(commentId);
        Long currentEmployeeId = roleUtil.getCurrentEmployeeId();
        boolean isHr = roleUtil.isHr() || roleUtil.isAdmin();

        if (!isHr && !comment.getCommentedById().equals(currentEmployeeId)) {
            throw new SecurityException("You are not authorized to delete this comment");
        }

        comment.setIsDeleted(true);
        comment.setDeletedBy(currentEmployeeId);
        comment.setDeletedOn(Instant.now());
        commentRepository.save(comment);

        // Warning email for HR-initiated deletion of another user's comment
        if (isHr && !comment.getCommentedById().equals(currentEmployeeId)) {
            EmployeeProfile commenter = employeeProfileRepository.findById(comment.getCommentedById())
                    .orElse(null);
            if (commenter != null && commenter.getUser() != null) {
                emailService.sendContentWarningEmail(
                        commenter.getUser().getEmail(),
                        commenter.getFirstName() + " " + commenter.getLastName(),
                        "comment",
                        comment.getCommentText(),
                        reason);
            }
        }
    }

    // ─────────────────────────────────────────────
    // SYSTEM POSTS (Birthday / Work Anniversary)
    // ─────────────────────────────────────────────

    /**
     * Called by the scheduler to auto-create celebration posts.
     * Uses authorId = 0L (system) or the employee's own ID as a marker.
     */
    @Transactional
    public void createBirthdayPost(EmployeeProfile employee) {
        String title = "🎂 Birthday Celebration";
        if (postRepository.existsSystemPostForToday(employee.getId(), LocalDate.now(), title + "%")) {
            return; // already posted today
        }
        String name = employee.getFirstName() + " " + employee.getLastName();
        Post post = buildSystemPost(
                employee.getId(),
                title,
                "🎉 Today is " + name + "'s birthday! Wish them a wonderful day! 🎂");
        postRepository.save(post);
    }

    @Transactional
    public void createWorkAnniversaryPost(EmployeeProfile employee, int years) {
        String title = "🏆 Work Anniversary";
        if (postRepository.existsSystemPostForToday(employee.getId(), LocalDate.now(), title + "%")) {
            return;
        }
        String name = employee.getFirstName() + " " + employee.getLastName();
        Post post = buildSystemPost(
                employee.getId(),
                title,
                "🎊 Congratulations to " + name + " for completing " + years + " year" + (years > 1 ? "s" : "")
                        + " at the organization! 🏆");
        postRepository.save(post);
    }

    // ─────────────────────────────────────────────
    // PRIVATE HELPERS
    // ─────────────────────────────────────────────

    private Post buildSystemPost(Long employeeId, String title, String text) {
        Post post = new Post();
        post.setAuthorId(employeeId);
        post.setTitle(title);
        post.setText(text);
        post.setCreatedDate(LocalDate.now());
        post.setUpdateDate(LocalDate.now());
        post.setIsSystemGenerated(true);
        post.setIsDeleted(false);
        post.setDeletedBy(null);
        post.setDeleteReason(null);
        post.setVisibleToAll(true);
        post.setPostType(2L); // 2 = system/celebration type
        post.setMediaType(0L);
        return post;
    }

    private void attachTags(Post post, List<String> tagNames) {
        if (tagNames == null || tagNames.isEmpty())
            return;
        for (String tagName : tagNames) {
            String cleanName = tagName.trim().toLowerCase();
            if (cleanName.isEmpty())
                continue;
            Tag tag = tagRepository.findByName(cleanName).orElseGet(() -> {
                Tag t = new Tag();
                t.setName(cleanName);
                return tagRepository.save(t);
            });
            PostTag postTag = new PostTag();
            postTag.setPost(post);
            postTag.setTag(tag);
            postTagRepository.save(postTag);
        }
    }

    private AchievementPostDto toDto(Post post, Long currentEmployeeId) {
        AchievementPostDto dto = new AchievementPostDto();
        dto.setId(post.getId());
        dto.setAuthorId(post.getAuthorId());
        dto.setTitle(post.getTitle());
        dto.setDescription(post.getText());
        dto.setCreatedDate(post.getCreatedDate());
        dto.setUpdatedDate(post.getUpdateDate());
        dto.setVisibleToAll(post.getVisibleToAll());
        dto.setIsSystemGenerated(post.getIsSystemGenerated());

        // Author name
        EmployeeProfile author = employeeProfileRepository.findById(post.getAuthorId()).orElse(null);
        if (author != null) {
            dto.setAuthorName(author.getFirstName() + " " + author.getLastName());
        } else {
            dto.setAuthorName("System");
        }

        // Tags
        dto.setTags(post.getPostTags().stream()
                .map(pt -> pt.getTag().getName())
                .collect(Collectors.toList()));

        // Likes
        long likeCount = postLikeRepository.countByPostId(post.getId());
        dto.setLikeCount(likeCount);
        dto.setLikedByCurrentUser(postLikeRepository.existsByPostIdAndLikedById(post.getId(), currentEmployeeId));

        // Recent likers (up to 5)
        List<Long> recentLikerIds = postLikeRepository.findRecentLikerIdsByPostId(post.getId())
                .stream().limit(RECENT_LIKERS_LIMIT).collect(Collectors.toList());
        List<String> recentLikerNames = new ArrayList<>();
        for (Long likerId : recentLikerIds) {
            EmployeeProfile liker = employeeProfileRepository.findById(likerId).orElse(null);
            if (liker != null) {
                recentLikerNames.add(liker.getFirstName() + " " + liker.getLastName());
            }
        }
        dto.setRecentLikers(recentLikerNames);

        // Comments
        List<Comment> comments = commentRepository.findActiveCommentsByPostId(post.getId());
        dto.setCommentCount((long) comments.size());
        dto.setComments(comments.stream().map(this::toCommentDto).collect(Collectors.toList()));

        return dto;
    }

    private CommentDto toCommentDto(Comment comment) {
        CommentDto dto = new CommentDto();
        dto.setId(comment.getId());
        dto.setPostId(comment.getPostId());
        dto.setParentCommentId(comment.getParentCommentId());
        dto.setCommentText(comment.getCommentText());
        dto.setCommentedById(comment.getCommentedById());
        dto.setCommentedOn(comment.getCommentedOn());
        dto.setUpdatedOn(comment.getUpdatedOn());
        dto.setIsDeleted(comment.getIsDeleted());

        EmployeeProfile commenter = employeeProfileRepository.findById(comment.getCommentedById()).orElse(null);
        if (commenter != null) {
            dto.setCommenterName(commenter.getFirstName() + " " + commenter.getLastName());
        }
        return dto;
    }

    private Post getActivePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));
        if (Boolean.TRUE.equals(post.getIsDeleted())) {
            throw new RuntimeException("Post has been deleted");
        }
        return post;
    }

    private Comment getActiveComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found with id: " + commentId));
        if (Boolean.TRUE.equals(comment.getIsDeleted())) {
            throw new RuntimeException("Comment has been deleted");
        }
        return comment;
    }
}
