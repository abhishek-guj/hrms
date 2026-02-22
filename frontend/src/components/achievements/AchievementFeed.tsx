import { useAchievementFeed } from "./queries/achievements.queries";
import AchievementPostCard from "./AchievementPostCard";
import { Button } from "../ui/button";
import { Skeleton } from "../ui/skeleton";
import { ChevronLeft, ChevronRight } from "lucide-react";
import type { AchievementFilterParams } from "./types/achievements.types";

interface Props {
  filters: AchievementFilterParams;
  onFiltersChange: (f: AchievementFilterParams) => void;
}

const AchievementFeed = ({ filters, onFiltersChange }: Props) => {
  const { data, isLoading, error } = useAchievementFeed(filters);
  console.log("Fetched achievements:", data);
  const handlePage = (delta: number) => {
    // onFiltersChange({ ...filters, page: (filters.page ?? 0) + delta });
  };

  if (error) {
    return (
      <div className="text-center py-12 text-muted-foreground">
        Failed to load achievements. Please try again.
      </div>
    );
  }

  if (isLoading) {
    return (
      <div className="flex flex-col gap-4">
        {Array.from({ length: 3 }).map((_, i) => (
          <div key={i} className="rounded-xl border p-5 flex flex-col gap-3">
            <div className="flex items-center gap-3">
              <Skeleton className="h-10 w-10 rounded-full" />
              <div className="flex flex-col gap-1.5">
                <Skeleton className="h-4 w-32" />
                <Skeleton className="h-3 w-20" />
              </div>
            </div>
            <Skeleton className="h-5 w-3/4" />
            <Skeleton className="h-4 w-full" />
            <Skeleton className="h-4 w-5/6" />
            <div className="flex gap-2 mt-1">
              <Skeleton className="h-6 w-16 rounded-full" />
              <Skeleton className="h-6 w-16 rounded-full" />
            </div>
          </div>
        ))}
      </div>
    );
  }

  if (!data || data.content.length === 0) {
    return (
      <div className="text-center py-16 text-muted-foreground">
        <div className="text-4xl mb-3">🏆</div>
        <div className="font-medium text-base">No achievements yet</div>
        <div className="text-sm mt-1">Be the first to share one!</div>
      </div>
    );
  }

  return (
    <div className="flex flex-col gap-4">
      {/* Role-based visibility filtering */}
      {data.content
        .filter((post) => {
          // If visibleToAll, show to everyone
          if (post.visibleToAll) return true;
          // Always show if current user is the author
          const employeeId = Number(localStorage.getItem("employeeId"));
          if (post.authorId === employeeId) return true;
          // Otherwise, check role-based visibility
          const role = localStorage.getItem("role") ?? "";
          if (post.visibleRoles && Array.isArray(post.visibleRoles)) {
            return post.visibleRoles.includes(role);
          }
          // If not provided, fallback to show
          return false;
        })
        .map((post) => (
          <AchievementPostCard key={post.id} post={post} />
        ))}

      {/* Pagination */}
      {data.totalPages > 1 && (
        <div className="flex items-center justify-center gap-3 pt-2">
          <Button
            variant="outline"
            size="sm"
            disabled={data.first}
            onClick={() => handlePage(-1)}
          >
            <ChevronLeft className="h-4 w-4" />
            Previous
          </Button>
          <span className="text-sm text-muted-foreground">
            Page {data.number + 1} of {data.totalPages}
          </span>
          <Button
            variant="outline"
            size="sm"
            disabled={data.last}
            onClick={() => handlePage(1)}
          >
            Next
            <ChevronRight className="h-4 w-4" />
          </Button>
        </div>
      )}
    </div>
  );
};

export default AchievementFeed;
