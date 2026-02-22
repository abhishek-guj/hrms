import { useState } from "react";
import { format } from "date-fns";
import {
  Heart,
  MessageCircle,
  Pencil,
  Trash2,
  Bot,
  MoreHorizontal,
} from "lucide-react";
import { Badge } from "../ui/badge";
import { Button } from "../ui/button";
import { Separator } from "../ui/separator";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuTrigger,
} from "../ui/dropdown-menu";
import CommentList from "./CommentList";
import EditPostDialog from "./EditPostDialog";
import DeletePostDialog from "./DeletePostDialog";
import type { AchievementPostDto } from "./types/achievements.types";
import { useToggleLike } from "./queries/achievements.queries";

interface Props {
  post: AchievementPostDto;
}

const AchievementPostCard = ({ post }: Props) => {
  const [showComments, setShowComments] = useState(false);
  const [showEditDialog, setShowEditDialog] = useState(false);
  const [showDeleteDialog, setShowDeleteDialog] = useState(false);

  const toggleLike = useToggleLike();

  const role = localStorage.getItem("role") ?? "";
  const employeeId = Number(localStorage.getItem("employeeId"));
  const isAuthor = post.authorId === employeeId;
  const isHrOrAdmin = role === "Hr" || role === "Admin";
  const canModify = isAuthor || isHrOrAdmin;

  const handleLike = () => {
    toggleLike.mutate(post.id);
  };

  return (
    <div
      className={`rounded-xl border bg-card shadow-sm flex flex-col gap-0 overflow-hidden transition-shadow hover:shadow-md ${
        post.isSystemGenerated
          ? "border-amber-200 bg-amber-50/40 dark:border-amber-800 dark:bg-amber-950/20"
          : ""
      }`}
    >
      {/* System-generated banner */}
      {post.isSystemGenerated && (
        <div className="flex items-center gap-1.5 px-5 pt-3 pb-0">
          <Bot className="h-3.5 w-3.5 text-amber-600" />
          <span className="text-xs text-amber-700 font-medium">
            System Notification
          </span>
        </div>
      )}

      <div className="p-5 flex flex-col gap-3">
        {/* Header */}
        <div className="flex items-start justify-between gap-2">
          <div className="flex items-center gap-3">
            {/* Avatar */}
            <div className="h-9 w-9 rounded-full bg-primary/10 flex items-center justify-center text-primary font-semibold text-sm shrink-0">
              {post.isSystemGenerated
                ? "🤖"
                : post.authorName
                    ?.split(" ")
                    .map((n) => n[0])
                    .join("")
                    .slice(0, 2)
                    .toUpperCase()}
            </div>
            <div>
              <div className="font-medium text-sm leading-tight">
                {post.isSystemGenerated ? "HRMS System" : post.authorName}
              </div>
              <div className="text-xs text-muted-foreground">
                {post.createdDate
                  ? format(new Date(post.createdDate), "dd MMM yyyy")
                  : ""}
              </div>
            </div>
          </div>

          {/* Actions menu */}
          {canModify && !post.isSystemGenerated && (
            <DropdownMenu>
              <DropdownMenuTrigger asChild>
                <Button variant="ghost" size="icon" className="h-8 w-8 -mt-1">
                  <MoreHorizontal className="h-4 w-4" />
                </Button>
              </DropdownMenuTrigger>
              <DropdownMenuContent align="end">
                {isAuthor && (
                  <DropdownMenuItem
                    onClick={() => setShowEditDialog(true)}
                    className="cursor-pointer"
                  >
                    <Pencil className="h-4 w-4 mr-2" />
                    Edit
                  </DropdownMenuItem>
                )}
                <DropdownMenuItem
                  onClick={() => setShowDeleteDialog(true)}
                  className="text-destructive cursor-pointer focus:text-destructive"
                >
                  <Trash2 className="h-4 w-4 mr-2" />
                  Delete
                </DropdownMenuItem>
              </DropdownMenuContent>
            </DropdownMenu>
          )}
        </div>

        {/* Title */}
        <div className="font-semibold text-base leading-snug">{post.title}</div>

        {/* Description */}
        <div className="text-sm text-muted-foreground leading-relaxed">
          {post.description}
        </div>

        {/* Tags */}
        {post.tags && post.tags.length > 0 && (
          <div className="flex flex-wrap gap-1.5 mt-0.5">
            {post.tags.map((tag) => (
              <Badge
                key={tag}
                variant="secondary"
                className="text-xs px-2 py-0.5 rounded-full"
              >
                #{tag}
              </Badge>
            ))}
          </div>
        )}

        <Separator className="mt-1" />

        {/* Like / Comment bar */}
        <div className="flex items-center gap-1">
          <Button
            variant="ghost"
            size="sm"
            onClick={handleLike}
            disabled={toggleLike.isPending}
            className={`flex items-center gap-1.5 h-8 px-3 ${
              post.likedByCurrentUser
                ? "text-red-500 hover:text-red-600"
                : "text-muted-foreground hover:text-foreground"
            }`}
          >
            <Heart
              className="h-4 w-4"
              fill={post.likedByCurrentUser ? "currentColor" : "none"}
            />
            <span className="text-sm">{post.likeCount}</span>
          </Button>

          <Button
            variant="ghost"
            size="sm"
            onClick={() => setShowComments((v) => !v)}
            className="flex items-center gap-1.5 h-8 px-3 text-muted-foreground hover:text-foreground"
          >
            <MessageCircle className="h-4 w-4" />
            <span className="text-sm">{post.commentCount}</span>
          </Button>

          {/* Recent likers */}
          {post.recentLikers && post.recentLikers.length > 0 && (
            <span className="text-xs text-muted-foreground ml-2">
              Liked by{" "}
              <span className="font-medium text-foreground">
                {post.recentLikers[0]}
              </span>
              {post.recentLikers.length > 1 &&
                ` and ${post.recentLikers.length - 1} others`}
            </span>
          )}
        </div>
      </div>

      {/* Comments section */}
      {showComments && (
        <div className="border-t bg-muted/20 px-5 py-4">
          <CommentList
            post={post}
            isHrOrAdmin={isHrOrAdmin}
            employeeId={employeeId}
          />
        </div>
      )}

      {/* Dialogs */}
      {showEditDialog && (
        <EditPostDialog
          post={post}
          open={showEditDialog}
          onOpenChange={setShowEditDialog}
        />
      )}
      {showDeleteDialog && (
        <DeletePostDialog
          post={post}
          open={showDeleteDialog}
          onOpenChange={setShowDeleteDialog}
          isHr={isHrOrAdmin}
        />
      )}
    </div>
  );
};

export default AchievementPostCard;
