import { useState } from "react";
import { format } from "date-fns";
import { Pencil, Trash2, Send, X, Check } from "lucide-react";
import { Button } from "../ui/button";
import { Textarea } from "../ui/textarea";

import {
  useAddComment,
  useDeleteComment,
  useEditComment,
} from "./queries/achievements.queries";
import type {
  AchievementPostDto,
  CommentDto,
} from "./types/achievements.types";

interface Props {
  post: AchievementPostDto;
  isHrOrAdmin: boolean;
  employeeId: number;
}

const CommentList = ({ post, isHrOrAdmin, employeeId }: Props) => {
  const [newComment, setNewComment] = useState("");
  const [editingId, setEditingId] = useState<number | null>(null);
  const [editText, setEditText] = useState("");
  const [deletingId, setDeletingId] = useState<number | null>(null);
  const [deleteReason, setDeleteReason] = useState("");

  const addComment = useAddComment();
  const editComment = useEditComment();
  const deleteComment = useDeleteComment();

  const handleSubmit = async () => {
    const trimmed = newComment.trim();
    if (!trimmed) return;
    await addComment.mutateAsync({
      postId: post.id,
      data: { commentText: trimmed },
    });
    setNewComment("");
  };

  const handleEditSave = async (commentId: number) => {
    const trimmed = editText.trim();
    if (!trimmed) return;
    await editComment.mutateAsync({
      commentId,
      data: { commentText: trimmed },
    });
    setEditingId(null);
  };

  const handleDelete = async (commentId: number) => {
    await deleteComment.mutateAsync({
      commentId,
      reason: deleteReason || undefined,
    });
    setDeletingId(null);
    setDeleteReason("");
  };

  const startEdit = (comment: CommentDto) => {
    setEditingId(comment.id);
    setEditText(comment.commentText);
    setDeletingId(null);
  };

  const startDelete = (comment: CommentDto) => {
    setDeletingId(comment.id);
    setEditingId(null);
    setDeleteReason("");
  };

  return (
    <div className="flex flex-col gap-3">
      {/* Comment list */}
      {post.comments.length === 0 ? (
        <div className="text-xs text-muted-foreground py-1">
          No comments yet. Be the first to comment!
        </div>
      ) : (
        <div className="flex flex-col gap-2.5">
          {post.comments.map((comment) => {
            const isCommentAuthor = comment.commentedById === employeeId;
            const canModify = isCommentAuthor || isHrOrAdmin;
            const isEditing = editingId === comment.id;
            const isDeleting = deletingId === comment.id;

            return (
              <div key={comment.id} className="flex gap-2.5 group">
                {/* Avatar */}
                <div className="h-7 w-7 rounded-full bg-secondary flex items-center justify-center text-xs font-semibold shrink-0 mt-0.5">
                  {comment.commenterName
                    ?.split(" ")
                    .map((n) => n[0])
                    .join("")
                    .slice(0, 2)
                    .toUpperCase()}
                </div>

                <div className="flex-1 min-w-0">
                  {isEditing ? (
                    <div className="flex flex-col gap-1.5">
                      <Textarea
                        value={editText}
                        onChange={(e) => setEditText(e.target.value)}
                        className="text-sm min-h-[60px] resize-none"
                        autoFocus
                      />
                      <div className="flex gap-1.5">
                        <Button
                          size="sm"
                          className="h-7 text-xs"
                          onClick={() => handleEditSave(comment.id)}
                          disabled={editComment.isPending}
                        >
                          <Check className="h-3 w-3 mr-1" />
                          Save
                        </Button>
                        <Button
                          size="sm"
                          variant="ghost"
                          className="h-7 text-xs"
                          onClick={() => setEditingId(null)}
                        >
                          <X className="h-3 w-3 mr-1" />
                          Cancel
                        </Button>
                      </div>
                    </div>
                  ) : isDeleting ? (
                    <div className="flex flex-col gap-1.5">
                      {isHrOrAdmin && !isCommentAuthor && (
                        <input
                          className="text-sm border rounded px-2 py-1 w-full"
                          placeholder="Reason for deletion (optional)"
                          value={deleteReason}
                          onChange={(e) => setDeleteReason(e.target.value)}
                        />
                      )}
                      <div className="flex gap-1.5 items-center">
                        <span className="text-xs text-destructive">
                          Delete this comment?
                        </span>
                        <Button
                          size="sm"
                          variant="destructive"
                          className="h-7 text-xs"
                          onClick={() => handleDelete(comment.id)}
                          disabled={deleteComment.isPending}
                        >
                          Delete
                        </Button>
                        <Button
                          size="sm"
                          variant="ghost"
                          className="h-7 text-xs"
                          onClick={() => setDeletingId(null)}
                        >
                          Cancel
                        </Button>
                      </div>
                    </div>
                  ) : (
                    <>
                      <div className="bg-muted/60 rounded-xl px-3 py-2">
                        <div className="text-xs font-semibold mb-0.5">
                          {comment.commenterName}
                        </div>
                        <div className="text-sm leading-relaxed">
                          {comment.commentText}
                        </div>
                      </div>
                      <div className="flex items-center gap-3 mt-0.5 px-1">
                        <span className="text-xs text-muted-foreground">
                          {comment.commentedOn
                            ? format(
                                new Date(comment.commentedOn),
                                "dd MMM, HH:mm",
                              )
                            : ""}
                        </span>
                        {canModify && (
                          <div className="flex gap-1 opacity-0 group-hover:opacity-100 transition-opacity">
                            {isCommentAuthor && (
                              <button
                                className="text-xs text-muted-foreground hover:text-foreground flex items-center gap-0.5"
                                onClick={() => startEdit(comment)}
                              >
                                <Pencil className="h-3 w-3" />
                                Edit
                              </button>
                            )}
                            <button
                              className="text-xs text-muted-foreground hover:text-destructive flex items-center gap-0.5"
                              onClick={() => startDelete(comment)}
                            >
                              <Trash2 className="h-3 w-3" />
                              Delete
                            </button>
                          </div>
                        )}
                      </div>
                    </>
                  )}
                </div>
              </div>
            );
          })}
        </div>
      )}

      {/* New comment input */}
      <div className="flex gap-2 items-end pt-1 border-t border-border/50">
        <Textarea
          value={newComment}
          onChange={(e) => setNewComment(e.target.value)}
          placeholder="Write a comment..."
          className="text-sm min-h-[36px] max-h-28 resize-none flex-1"
          onKeyDown={(e) => {
            if (e.key === "Enter" && !e.shiftKey) {
              e.preventDefault();
              handleSubmit();
            }
          }}
        />
        <Button
          size="sm"
          onClick={handleSubmit}
          disabled={!newComment.trim() || addComment.isPending}
          className="h-9 shrink-0"
        >
          <Send className="h-4 w-4" />
        </Button>
      </div>
    </div>
  );
};

export default CommentList;
