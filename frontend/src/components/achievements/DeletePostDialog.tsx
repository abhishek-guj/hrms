import { useState } from "react";
import { Button } from "../ui/button";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
} from "../ui/dialog";
import { Input } from "../ui/input";
import { useDeleteAchievementPost } from "./queries/achievements.queries";
import type { AchievementPostDto } from "./types/achievements.types";

interface Props {
  post: AchievementPostDto;
  open: boolean;
  onOpenChange: (v: boolean) => void;
  isHr: boolean;
}

const DeletePostDialog = ({ post, open, onOpenChange, isHr }: Props) => {
  const [reason, setReason] = useState("");
  const deletePost = useDeleteAchievementPost();

  const employeeId = Number(localStorage.getItem("employeeId"));
  const isDeletingOthersPost = post.authorId !== employeeId;

  const handleDelete = async () => {
    await deletePost.mutateAsync({
      id: post.id,
      reason: reason || undefined,
    });
    onOpenChange(false);
  };

  return (
    <Dialog open={open} onOpenChange={onOpenChange}>
      <DialogContent className="sm:max-w-[420px]">
        <DialogHeader>
          <DialogTitle>Delete Post</DialogTitle>
          <DialogDescription>
            Are you sure you want to delete this post? This action cannot be
            undone.
            {isHr && isDeletingOthersPost && (
              <span className="block mt-1 text-amber-600 font-medium">
                A warning email will be sent to the author.
              </span>
            )}
          </DialogDescription>
        </DialogHeader>

        <div className="py-1">
          <div className="text-sm font-medium text-muted-foreground mb-1">
            Post
          </div>
          <div className="text-sm bg-muted/50 rounded-lg px-3 py-2 line-clamp-2">
            {post.title}
          </div>
        </div>

        {isHr && isDeletingOthersPost && (
          <div className="flex flex-col gap-1.5">
            <label className="text-sm font-medium">
              Reason{" "}
              <span className="text-muted-foreground font-normal">
                (optional)
              </span>
            </label>
            <Input
              placeholder="Reason for removal..."
              value={reason}
              onChange={(e) => setReason(e.target.value)}
            />
          </div>
        )}

        <DialogFooter>
          <Button variant="outline" onClick={() => onOpenChange(false)}>
            Cancel
          </Button>
          <Button
            variant="destructive"
            onClick={handleDelete}
            disabled={deletePost.isPending}
          >
            {deletePost.isPending ? "Deleting..." : "Delete"}
          </Button>
        </DialogFooter>
      </DialogContent>
    </Dialog>
  );
};

export default DeletePostDialog;
