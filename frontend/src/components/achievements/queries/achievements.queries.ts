import {
    useMutation,
    useQuery,
    useQueryClient,
    type UseQueryResult,
} from "@tanstack/react-query";
import { AchievementService } from "../services/achievements.service";
import { showError, showSuccess } from "../../ui/toast";
import type {
    AchievementFilterParams,
    AchievementPostDto,
    AchievementPostRequestDto,
    CommentRequestDto,
    PagedResponse,
} from './../types/achievements.types';
export const ACHIEVEMENT_QUERY_KEY = "achievements";

// ─── Queries ──────────────────────────────────────────────────────────────────

export const useAchievementFeed = (
    params: AchievementFilterParams = {}
): UseQueryResult<PagedResponse<AchievementPostDto>> => {
    return useQuery({
        queryKey: [ACHIEVEMENT_QUERY_KEY, "feed", params],
        queryFn: () => AchievementService.getFeed(params),
    });
};

export const useAchievementById = (
    id: number
): UseQueryResult<AchievementPostDto> => {
    return useQuery({
        queryKey: [ACHIEVEMENT_QUERY_KEY, id],
        queryFn: () => AchievementService.getById(id),
        enabled: !!id,
    });
};

// ─── Mutations ────────────────────────────────────────────────────────────────

export const useCreateAchievementPost = () => {
    const queryClient = useQueryClient();
    return useMutation({
        mutationFn: (data: AchievementPostRequestDto) =>
            AchievementService.create(data),
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: [ACHIEVEMENT_QUERY_KEY] });
            showSuccess("Post created successfully");
        },
        onError: () => {
            showError("Failed to create post");
        },
    });
};

export const useUpdateAchievementPost = () => {
    const queryClient = useQueryClient();
    return useMutation({
        mutationFn: ({
            id,
            data,
        }: {
            id: number;
            data: AchievementPostRequestDto;
        }) => AchievementService.update(id, data),
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: [ACHIEVEMENT_QUERY_KEY] });
            showSuccess("Post updated successfully");
        },
        onError: () => {
            showError("Failed to update post");
        },
    });
};

export const useDeleteAchievementPost = () => {
    const queryClient = useQueryClient();
    return useMutation({
        mutationFn: ({ id, reason }: { id: number; reason?: string }) =>
            AchievementService.deletePost(id, reason),
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: [ACHIEVEMENT_QUERY_KEY] });
            showSuccess("Post deleted");
        },
        onError: () => {
            showError("Failed to delete post");
        },
    });
};

export const useToggleLike = () => {
    const queryClient = useQueryClient();
    return useMutation({
        mutationFn: (postId: number) => AchievementService.toggleLike(postId),
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: [ACHIEVEMENT_QUERY_KEY] });
        },
        onError: () => {
            showError("Failed to update like");
        },
    });
};

export const useAddComment = () => {
    const queryClient = useQueryClient();
    return useMutation({
        mutationFn: ({
            postId,
            data,
        }: {
            postId: number;
            data: CommentRequestDto;
        }) => AchievementService.addComment(postId, data),
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: [ACHIEVEMENT_QUERY_KEY] });
            showSuccess("Comment added");
        },
        onError: () => {
            showError("Failed to add comment");
        },
    });
};

export const useEditComment = () => {
    const queryClient = useQueryClient();
    return useMutation({
        mutationFn: ({
            commentId,
            data,
        }: {
            commentId: number;
            data: CommentRequestDto;
        }) => AchievementService.editComment(commentId, data),
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: [ACHIEVEMENT_QUERY_KEY] });
            showSuccess("Comment updated");
        },
        onError: () => {
            showError("Failed to update comment");
        },
    });
};

export const useDeleteComment = () => {
    const queryClient = useQueryClient();
    return useMutation({
        mutationFn: ({
            commentId,
            reason,
        }: {
            commentId: number;
            reason?: string;
        }) => AchievementService.deleteComment(commentId, reason),
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: [ACHIEVEMENT_QUERY_KEY] });
            showSuccess("Comment deleted");
        },
        onError: () => {
            showError("Failed to delete comment");
        },
    });
};
