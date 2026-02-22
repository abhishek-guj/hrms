
export interface CommentDto {
    id: number;
    postId: number;
    parentCommentId: number | null;
    commentText: string;
    commentedById: number;
    commenterName: string;
    commentedOn: string;
    updatedOn: string;
    isDeleted: boolean;
}

export interface AchievementPostDto {
    id: number;
    authorId: number;
    authorName: string;
    title: string;
    description: string;
    tags: string[];
    createdDate: string;
    updatedDate: string;
    visibleToAll: boolean;
    isSystemGenerated: boolean;
    likeCount: number;
    recentLikers: string[];
    commentCount: number;
    comments: CommentDto[];
    likedByCurrentUser: boolean;
}

export interface PagedResponse<T> {
    content: T[];
    totalElements: number;
    totalPages: number;
    number: number;
    size: number;
    last: boolean;
    first: boolean;
}

export interface AchievementPostRequestDto {
    title: string;
    description: string;
    tags: string[];
    visibleToAll: boolean;
}

export interface CommentRequestDto {
    commentText: string;
    parentCommentId?: number | null;
}

export interface AchievementFilterParams {
    page?: number;
    size?: number;
    authorId?: number;
    tag?: string;
    from?: string;
    to?: string;
}
