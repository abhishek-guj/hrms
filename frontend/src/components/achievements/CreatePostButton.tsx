import { useState } from "react";
import { Plus, X } from "lucide-react";
import { Button } from "../ui/button";
import { Dialog, DialogContent, DialogHeader, DialogTitle } from "../ui/dialog";
import { Input } from "../ui/input";
import { Textarea } from "../ui/textarea";
import { Badge } from "../ui/badge";
import { useCreateAchievementPost } from "./queries/achievements.queries";

const CreatePostButton = () => {
  const [open, setOpen] = useState(false);

  return (
    <>
      <Button
        size="sm"
        className="flex items-center gap-1.5"
        onClick={() => setOpen(true)}
      >
        <Plus className="h-4 w-4" />
        New Post
      </Button>

      <CreatePostDialog open={open} onOpenChange={setOpen} />
    </>
  );
};

interface DialogProps {
  open: boolean;
  onOpenChange: (v: boolean) => void;
}

export const CreatePostDialog = ({ open, onOpenChange }: DialogProps) => {
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [tagInput, setTagInput] = useState("");
  const [tags, setTags] = useState<string[]>([]);
  const [visibleToAll, setVisibleToAll] = useState(true);

  const createPost = useCreateAchievementPost();

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

  const reset = () => {
    setTitle("");
    setDescription("");
    setTagInput("");
    setTags([]);
    setVisibleToAll(true);
  };

  const handleSubmit = async () => {
    if (!title.trim() || !description.trim()) return;
    await createPost.mutateAsync({
      title: title.trim(),
      description: description.trim(),
      tags,
      visibleToAll,
    });
    reset();
    onOpenChange(false);
  };

  return (
    <Dialog
      open={open}
      onOpenChange={(v) => {
        if (!v) reset();
        onOpenChange(v);
      }}
    >
      <DialogContent className="w-[520px] max-w-[95vw]">
        <DialogHeader>
          <DialogTitle>Share an Achievement</DialogTitle>
        </DialogHeader>

        <div className="flex flex-col gap-4 mt-1">
          {/* Title */}
          <div className="flex flex-col gap-1.5">
            <label className="text-sm font-medium">Title</label>
            <Input
              placeholder="e.g. Completed AWS Certification"
              value={title}
              onChange={(e) => setTitle(e.target.value)}
            />
          </div>

          {/* Description */}
          <div className="flex flex-col gap-1.5">
            <label className="text-sm font-medium">Description</label>
            <Textarea
              placeholder="Share the details of your achievement..."
              value={description}
              onChange={(e) => setDescription(e.target.value)}
              className="min-h-[100px] resize-none"
            />
          </div>

          {/* Tags */}
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

          {/* Visibility */}
          <div className="flex items-center gap-2">
            <input
              type="checkbox"
              id="visibleToAll"
              checked={visibleToAll}
              onChange={(e) => setVisibleToAll(e.target.checked)}
              className="h-4 w-4 rounded border-gray-300"
            />
            <label
              htmlFor="visibleToAll"
              className="text-sm text-muted-foreground"
            >
              Visible to all employees
            </label>
          </div>

          {/* Actions */}
          <div className="flex justify-end gap-2 pt-1">
            <Button
              variant="outline"
              onClick={() => {
                reset();
                onOpenChange(false);
              }}
            >
              Cancel
            </Button>
            <Button
              onClick={handleSubmit}
              disabled={
                !title.trim() || !description.trim() || createPost.isPending
              }
            >
              {createPost.isPending ? "Posting..." : "Post"}
            </Button>
          </div>
        </div>
      </DialogContent>
    </Dialog>
  );
};

export default CreatePostButton;
