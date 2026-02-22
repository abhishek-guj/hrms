import { useState } from "react";
import { X } from "lucide-react";
import { Button } from "../ui/button";
import { Dialog, DialogContent, DialogHeader, DialogTitle } from "../ui/dialog";
import { Input } from "../ui/input";
import { Textarea } from "../ui/textarea";
import { Badge } from "../ui/badge";
import { useUpdateAchievementPost } from "./queries/achievements.queries";
import type { AchievementPostDto } from "./types/achievements.types";

interface Props {
  post: AchievementPostDto;
  open: boolean;
  onOpenChange: (v: boolean) => void;
}

const EditPostDialog = ({ post, open, onOpenChange }: Props) => {
  const [title, setTitle] = useState(post.title);
  const [description, setDescription] = useState(post.description);
  const [tagInput, setTagInput] = useState("");
  const [tags, setTags] = useState<string[]>(post.tags ?? []);
  const [visibleToAll, setVisibleToAll] = useState(post.visibleToAll ?? true);

  const updatePost = useUpdateAchievementPost();

  const addTag = () => {
    const cleaned = tagInput.trim().toLowerCase();
    if (cleaned && !tags.includes(cleaned)) {
      setTags((prev) => [...prev, cleaned]);
    }
    setTagInput("");
  };

  const removeTag = (tag: string) => {
    setTags((prev) => prev.filter((t) => t !== tag));
  };

  const handleSubmit = async () => {
    if (!title.trim() || !description.trim()) return;
    await updatePost.mutateAsync({
      id: post.id,
      data: {
        title: title.trim(),
        description: description.trim(),
        tags,
        visibleToAll,
      },
    });
    onOpenChange(false);
  };

  return (
    <Dialog open={open} onOpenChange={onOpenChange}>
      <DialogContent className="w-[520px] max-w-[95vw]">
        <DialogHeader>
          <DialogTitle>Edit Post</DialogTitle>
        </DialogHeader>

        <div className="flex flex-col gap-4 mt-1">
          <div className="flex flex-col gap-1.5">
            <label className="text-sm font-medium">Title</label>
            <Input
              value={title}
              onChange={(e) => setTitle(e.target.value)}
              placeholder="Title"
            />
          </div>

          <div className="flex flex-col gap-1.5">
            <label className="text-sm font-medium">Description</label>
            <Textarea
              value={description}
              onChange={(e) => setDescription(e.target.value)}
              className="min-h-[100px] resize-none"
            />
          </div>

          <div className="flex flex-col gap-1.5">
            <label className="text-sm font-medium">Tags</label>
            <div className="flex gap-2">
              <Input
                placeholder="Add a tag..."
                value={tagInput}
                onChange={(e) => setTagInput(e.target.value)}
                onKeyDown={(e) => {
                  if (e.key === "Enter") {
                    e.preventDefault();
                    addTag();
                  }
                }}
                className="flex-1"
              />
              <Button
                type="button"
                variant="outline"
                size="sm"
                onClick={addTag}
              >
                Add
              </Button>
            </div>
            {tags.length > 0 && (
              <div className="flex flex-wrap gap-1.5 mt-1">
                {tags.map((tag) => (
                  <Badge
                    key={tag}
                    variant="secondary"
                    className="rounded-full flex items-center gap-1 pr-1.5"
                  >
                    #{tag}
                    <button
                      onClick={() => removeTag(tag)}
                      className="ml-0.5 hover:text-destructive"
                    >
                      <X className="h-3 w-3" />
                    </button>
                  </Badge>
                ))}
              </div>
            )}
          </div>

          <div className="flex items-center gap-2">
            <input
              type="checkbox"
              id="editVisibleToAll"
              checked={visibleToAll}
              onChange={(e) => setVisibleToAll(e.target.checked)}
              className="h-4 w-4 rounded border-gray-300"
            />
            <label
              htmlFor="editVisibleToAll"
              className="text-sm text-muted-foreground"
            >
              Visible to all employees
            </label>
          </div>

          <div className="flex justify-end gap-2 pt-1">
            <Button variant="outline" onClick={() => onOpenChange(false)}>
              Cancel
            </Button>
            <Button
              onClick={handleSubmit}
              disabled={
                !title.trim() || !description.trim() || updatePost.isPending
              }
            >
              {updatePost.isPending ? "Saving..." : "Save Changes"}
            </Button>
          </div>
        </div>
      </DialogContent>
    </Dialog>
  );
};

export default EditPostDialog;
