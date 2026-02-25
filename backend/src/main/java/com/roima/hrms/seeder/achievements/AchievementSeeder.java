package com.roima.hrms.seeder.achievements;

import com.roima.hrms.entities.Comment;
import com.roima.hrms.entities.EmployeeProfile;
import com.roima.hrms.entities.Post;
import com.roima.hrms.entities.PostLike;
import com.roima.hrms.entities.PostTag;
import com.roima.hrms.entities.Tag;
import com.roima.hrms.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;

@Slf4j
@Component
@Order(10) // Run after UserSeeder (@Order 2) and all other seeders
public class AchievementSeeder implements ApplicationListener<ContextRefreshedEvent> {

        private final PostRepository postRepository;
        private final CommentRepository commentRepository;
        private final PostLikeRepository postLikeRepository;
        private final TagRepository tagRepository;
        private final PostTagRepository postTagRepository;
        private final EmployeeProfileRepository employeeProfileRepository;

        public AchievementSeeder(
                        PostRepository postRepository,
                        CommentRepository commentRepository,
                        PostLikeRepository postLikeRepository,
                        TagRepository tagRepository,
                        PostTagRepository postTagRepository,
                        EmployeeProfileRepository employeeProfileRepository) {
                this.postRepository = postRepository;
                this.commentRepository = commentRepository;
                this.postLikeRepository = postLikeRepository;
                this.tagRepository = tagRepository;
                this.postTagRepository = postTagRepository;
                this.employeeProfileRepository = employeeProfileRepository;
        }

        @Override
        public void onApplicationEvent(ContextRefreshedEvent event) {
                loadAchievements();
        }

        private void loadAchievements() {

                // ── Idempotency guard ─────────────────────────────────────────────────
                // Check for the title of the very first seed post.
                // If it already exists this seeder has already run — skip entirely.
                if (postRepository.existsByTitleAndIsSystemGeneratedFalse("Passed AWS Solutions Architect Exam! 🎉")) {
                        log.info("AchievementSeeder: seed data already present, skipping.");
                        return;
                }

                // ── Resolve employee profiles created by UserSeeder ───────────────────
                EmployeeProfile emp1 = employeeProfileRepository.getEmployeeProfileByUser_Email("emp@exp.com");
                EmployeeProfile emp2 = employeeProfileRepository.getEmployeeProfileByUser_Email("emp2@exp.com");
                EmployeeProfile emp3 = employeeProfileRepository.getEmployeeProfileByUser_Email("emp3@exp.com");
                EmployeeProfile emp4 = employeeProfileRepository.getEmployeeProfileByUser_Email("emp4@exp.com");
                EmployeeProfile hr1 = employeeProfileRepository.getEmployeeProfileByUser_Email("hr1@exp.com");
                EmployeeProfile hr2 = employeeProfileRepository.getEmployeeProfileByUser_Email("hr2@exp.com");
                EmployeeProfile admin = employeeProfileRepository.getEmployeeProfileByUser_Email("abhi@exp.com");

                // ── Tags (get-or-create so re-runs stay idempotent) ───────────────────
                Tag tagCert = getOrCreateTag("certification");
                Tag tagMilestone = getOrCreateTag("milestone");
                Tag tagTeamwork = getOrCreateTag("teamwork");
                Tag tagInnovation = getOrCreateTag("innovation");
                Tag tagLearning = getOrCreateTag("learning");
                Tag tagLeadership = getOrCreateTag("leadership");
                Tag tagClient = getOrCreateTag("client");
                Tag tagAward = getOrCreateTag("award");

                // ══════════════════════════════════════════════════════════════════════
                // POST 1 — emp1: AWS certification
                // ══════════════════════════════════════════════════════════════════════
                Post post1 = savePost(buildPost(
                                emp1.getId(),
                                "Passed AWS Solutions Architect Exam! ",
                                "",
                                LocalDate.now().minusDays(12)));
                attachTag(post1, tagCert);
                attachTag(post1, tagMilestone);
                attachTag(post1, tagLearning);

                addLike(post1.getId(), emp2.getId());
                addLike(post1.getId(), emp3.getId());
                addLike(post1.getId(), hr1.getId());
                addLike(post1.getId(), admin.getId());

                addComment(post1.getId(), emp2.getId(),
                                "Congrats! That exam is no joke — you should be super proud! ");
                addComment(post1.getId(), hr1.getId(),
                                "Amazing achievement! We'll add this to your profile right away.");
                addComment(post1.getId(), emp3.getId(),
                                "Inspiring! I'm planning to take the same cert next quarter.");
                addComment(post1.getId(), emp1.getId(),
                                "Thanks everyone! Happy to share my study resources if anyone needs them. ");

                // ══════════════════════════════════════════════════════════════════════
                // POST 2 — emp2: Project delivery ahead of schedule
                // ══════════════════════════════════════════════════════════════════════
                Post post2 = savePost(buildPost(
                                emp2.getId(),
                                "Team Orion Delivered Project Ahead of Schedule ",

                                "4 departments. Absolutely couldn't have done it without the incredible team.",
                                LocalDate.now().minusDays(9)));
                attachTag(post2, tagTeamwork);
                attachTag(post2, tagMilestone);
                attachTag(post2, tagLeadership);

                addLike(post2.getId(), emp1.getId());
                addLike(post2.getId(), emp4.getId());
                addLike(post2.getId(), hr2.getId());

                addComment(post2.getId(), emp1.getId(),
                                "What a delivery! The whole company was watching that timeline. 🏆");
                addComment(post2.getId(), hr2.getId(),
                                "Excellent work — this will be highlighted in the next all-hands.");
                addComment(post2.getId(), emp4.getId(),
                                "2 weeks early is wild — well done everyone! ");

                // ══════════════════════════════════════════════════════════════════════
                // POST 3 — emp3: Published internal tech blog
                // ══════════════════════════════════════════════════════════════════════
                Post post3 = savePost(buildPost(
                                emp3.getId(),
                                "Published My First Internal Tech Blog Post ",
                                "implementation. Highly recommend for anyone building event-driven systems!",
                                LocalDate.now().minusDays(7)));
                attachTag(post3, tagLearning);
                attachTag(post3, tagInnovation);

                addLike(post3.getId(), emp1.getId());
                addLike(post3.getId(), emp2.getId());

                addComment(post3.getId(), emp2.getId(),
                                "Read it already — fantastic write-up. Bookmarked for the whole team!");
                addComment(post3.getId(), admin.getId(),
                                "Great initiative! We need more people sharing knowledge like this.");

                // ══════════════════════════════════════════════════════════════════════
                // POST 4 — hr1: Client appreciation letter
                // ══════════════════════════════════════════════════════════════════════
                Post post4 = savePost(buildPost(
                                hr1.getId(),
                                "Received Client Appreciation Letter ",
                                "We received an unsolicited appreciation letter from GlobalTech praising our HR team.",
                                LocalDate.now().minusDays(5)));
                attachTag(post4, tagClient);
                attachTag(post4, tagAward);
                attachTag(post4, tagTeamwork);

                addLike(post4.getId(), emp1.getId());
                addLike(post4.getId(), emp2.getId());
                addLike(post4.getId(), emp3.getId());
                addLike(post4.getId(), emp4.getId());
                addLike(post4.getId(), admin.getId());

                addComment(post4.getId(), emp3.getId(),
                                "So well deserved — the HR team has been absolutely stellar lately!");
                addComment(post4.getId(), emp4.getId(),
                                "Client-side recognition is the best. Congratulations! 🎉");
                addComment(post4.getId(), admin.getId(),
                                "Really proud of the entire HR team. Keep it up!");

                // ══════════════════════════════════════════════════════════════════════
                // POST 5 — emp4: Completed leadership training
                // ══════════════════════════════════════════════════════════════════════
                Post post5 = savePost(buildPost(
                                emp4.getId(),
                                "Completed 'Leading with Influence' Training Program",
                                "recommend to anyone looking to grow into a leadership role.",
                                LocalDate.now().minusDays(3)));
                attachTag(post5, tagLeadership);
                attachTag(post5, tagLearning);

                addLike(post5.getId(), hr1.getId());
                addLike(post5.getId(), emp1.getId());

                addComment(post5.getId(), hr1.getId(),
                                "Fantastic! We'll add this certification to your profile right away.");
                addComment(post5.getId(), emp2.getId(),
                                "I've heard great things about that programme — was it fully online?");
                addComment(post5.getId(), emp4.getId(),
                                "Yes, fully online and very flexible! Happy to share the details.");

                // ══════════════════════════════════════════════════════════════════════
                // POST 6 — emp2: Internal hackathon winner
                // ══════════════════════════════════════════════════════════════════════
                Post post6 = savePost(buildPost(
                                emp2.getId(),
                                "Won Internal Hackathon — Team InnovateFast",
                                "Our team 'InnovateFast'",
                                LocalDate.now().minusDays(1)));
                attachTag(post6, tagInnovation);
                attachTag(post6, tagAward);
                attachTag(post6, tagTeamwork);

                addLike(post6.getId(), emp1.getId());
                addLike(post6.getId(), emp3.getId());
                addLike(post6.getId(), emp4.getId());
                addLike(post6.getId(), hr1.getId());
                addLike(post6.getId(), hr2.getId());
                addLike(post6.getId(), admin.getId());

                addComment(post6.getId(), emp3.getId(),
                                "The AI dashboard idea is brilliant — will it ship to production?");
                addComment(post6.getId(), admin.getId(),
                                "We are actively looking at productionising this — watch this space!");
                addComment(post6.getId(), hr2.getId(),
                                "First place — outstanding! What a team.");

                // ══════════════════════════════════════════════════════════════════════
                // SYSTEM POST 7 — Birthday auto-post for emp1 (back-dated, seed only)
                // ══════════════════════════════════════════════════════════════════════
                if (!postRepository.existsByTitleAndIsSystemGeneratedTrue(" Birthday Celebration - Seed")) {
                        Post birthdayPost = savePost(buildSystemPost(
                                        emp1.getId(),
                                        "Birthday Celebration - Seed",
                                        "Today is " + emp1.getFirstName() + " " + emp1.getLastName() +
                                                        "'s birthday! Wish them a wonderful day!",
                                        LocalDate.now().minusDays(20)));

                        addLike(birthdayPost.getId(), emp2.getId());
                        addLike(birthdayPost.getId(), emp3.getId());
                        addLike(birthdayPost.getId(), hr1.getId());

                        addComment(birthdayPost.getId(), emp2.getId(),
                                        "Happy Birthday! Hope you have a fantastic day!");
                        addComment(birthdayPost.getId(), hr1.getId(),
                                        "Wishing you all the best!");
                }

                // ══════════════════════════════════════════════════════════════════════
                // SYSTEM POST 8 — Work anniversary for hr2 (3 years, seed only)
                // ══════════════════════════════════════════════════════════════════════
                if (!postRepository.existsByTitleAndIsSystemGeneratedTrue("Work Anniversary - Seed")) {
                        Post anniversaryPost = savePost(buildSystemPost(
                                        hr2.getId(),
                                        "Work Anniversary - Seed",
                                        "Congratulations to " + hr2.getFirstName() + " " + hr2.getLastName() +
                                                        " for completing 3 years at the organization! Thank you for your dedication.",
                                        LocalDate.now().minusDays(14)));

                        addLike(anniversaryPost.getId(), emp1.getId());
                        addLike(anniversaryPost.getId(), emp2.getId());
                        addLike(anniversaryPost.getId(), admin.getId());

                        addComment(anniversaryPost.getId(), emp1.getId(),
                                        "3 years already! Congratulations!");
                        addComment(anniversaryPost.getId(), admin.getId(),
                                        "Thank you for your dedication and hard work. Here's to many more!");
                }

                log.info("AchievementSeeder: seeded 8 posts (6 user + 2 system) successfully.");
        }

        // ── Builder helpers ───────────────────────────────────────────────────────

        private Post buildPost(Long authorId, String title, String description, LocalDate date) {
                Post post = new Post();
                post.setAuthorId(authorId);
                post.setTitle(title);
                post.setText(description);
                post.setCreatedDate(date);
                post.setUpdateDate(date);
                post.setPostType(1L);
                post.setMediaType(0L);
                post.setIsSystemGenerated(false);
                post.setIsDeleted(false);
                post.setVisibleToAll(true);
                return post;
        }

        private Post buildSystemPost(Long employeeId, String title, String text, LocalDate date) {
                Post post = new Post();
                post.setAuthorId(employeeId);
                post.setTitle(title);
                post.setText(text);
                post.setCreatedDate(date);
                post.setUpdateDate(date);
                post.setPostType(2L);
                post.setMediaType(0L);
                post.setIsSystemGenerated(true);
                post.setIsDeleted(false);
                post.setVisibleToAll(true);
                return post;
        }

        private Post savePost(Post post) {
                return postRepository.save(post);
        }

        private Tag getOrCreateTag(String name) {
                return tagRepository.findByName(name).orElseGet(() -> {
                        Tag t = new Tag();
                        t.setName(name);
                        return tagRepository.save(t);
                });
        }

        private void attachTag(Post post, Tag tag) {
                PostTag pt = new PostTag();
                pt.setPost(post);
                pt.setTag(tag);
                postTagRepository.save(pt);
        }

        private void addLike(Long postId, Long employeeId) {
                // existsByPostIdAndLikedById defined in the updated PostLikeRepository
                if (!postLikeRepository.existsByPostIdAndLikedById(postId, employeeId)) {
                        PostLike like = new PostLike();
                        like.setPostId(postId);
                        like.setLikedById(employeeId);
                        like.setUpdateOn(Instant.now());
                        postLikeRepository.save(like);
                }
        }

        private void addComment(Long postId, Long employeeId, String text) {
                Comment comment = new Comment();
                comment.setPostId(postId);
                comment.setCommentedById(employeeId);
                comment.setCommentText(text);
                comment.setCommentedOn(Instant.now());
                comment.setUpdatedOn(Instant.now());
                comment.setIsDeleted(false);
                commentRepository.save(comment);
        }
}