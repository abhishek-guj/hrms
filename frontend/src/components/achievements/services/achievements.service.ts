import { api } from "../../../api/apiClient";
import type { IApiResponse } from "../../../api/apiResponse.types";
import { ACHIEVEMENT_ENDPOINTS } from "../../../api/endpoints";
import type {
    AchievementPostDto,
    AchievementPostRequestDto,
    AchievementFilterParams,
    CommentDto,
    CommentRequestDto,
    PagedResponse,
} from './../types/achievements.types';

export const AchievementService = {
    async getFeed(
        params: AchievementFilterParams = {}
    ): Promise<PagedResponse<AchievementPostDto>> {
        const query = new URLSearchParams();
        if (params.page !== undefined) query.set("page", String(params.page));
        if (params.size !== undefined) query.set("size", String(params.size));
        if (params.authorId) query.set("authorId", String(params.authorId));
        if (params.tag) query.set("tag", params.tag);
        if (params.from) query.set("from", params.from);
        if (params.to) query.set("to", params.to);

        const res = await api.get<IApiResponse<PagedResponse<AchievementPostDto>>>(
            ACHIEVEMENT_ENDPOINTS.getFeed(query.toString())
        );
        return res.data.data;
    },

    async getById(id: number): Promise<AchievementPostDto> {
        const res = await api.get<IApiResponse<AchievementPostDto>>(
            ACHIEVEMENT_ENDPOINTS.getById(id)
        );
        return res.data.data;
    },

    async create(data: AchievementPostRequestDto): Promise<AchievementPostDto> {
        const res = await api.post<IApiResponse<AchievementPostDto>>(
            ACHIEVEMENT_ENDPOINTS.create(),
            data
        );
        return res.data.data;
    },

    async update(
        id: number,
        data: AchievementPostRequestDto
    ): Promise<AchievementPostDto> {
        const res = await api.put<IApiResponse<AchievementPostDto>>(
            ACHIEVEMENT_ENDPOINTS.update(id),
            data
        );
        return res.data.data;
    },

    async deletePost(id: number, reason?: string): Promise<void> {
        await api.delete(ACHIEVEMENT_ENDPOINTS.delete(id, reason));
    },

    async toggleLike(postId: number): Promise<number> {
        const res = await api.post<IApiResponse<number>>(
            ACHIEVEMENT_ENDPOINTS.toggleLike(postId)
        );
        return res.data.data;
    },

    async addComment(
        postId: number,
        data: CommentRequestDto
    ): Promise<CommentDto> {
        const res = await api.post<IApiResponse<CommentDto>>(
            ACHIEVEMENT_ENDPOINTS.addComment(postId),
            data
        );
        return res.data.data;
    },

    async editComment(
        commentId: number,
        data: CommentRequestDto
    ): Promise<CommentDto> {
        const res = await api.put<IApiResponse<CommentDto>>(
            ACHIEVEMENT_ENDPOINTS.editComment(commentId),
            data
        );
        return res.data.data;
    },

    async deleteComment(commentId: number, reason?: string): Promise<void> {
        await api.delete(ACHIEVEMENT_ENDPOINTS.deleteComment(commentId, reason));
    },
};
