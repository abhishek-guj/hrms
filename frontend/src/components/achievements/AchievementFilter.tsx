import { useState } from "react";
import { Input } from "../ui/input";
import { Button } from "../ui/button";
import { Badge } from "../ui/badge";
import { Search, X, SlidersHorizontal } from "lucide-react";
import type { AchievementFilterParams } from "./types/achievement_types";

interface Props {
  filters: AchievementFilterParams;
  onFiltersChange: (f: AchievementFilterParams) => void;
}

const AchievementFilters = ({ filters, onFiltersChange }: Props) => {
  const [tagInput, setTagInput] = useState(filters.tag ?? "");
  const [fromInput, setFromInput] = useState(filters.from ?? "");
  const [toInput, setToInput] = useState(filters.to ?? "");
  const [showFilters, setShowFilters] = useState(false);

  const activeCount = [filters.tag, filters.from, filters.to].filter(
    Boolean,
  ).length;

  const applyFilters = () => {
    onFiltersChange({
      ...filters,
      page: 0,
      tag: tagInput || undefined,
      from: fromInput || undefined,
      to: toInput || undefined,
    });
  };

  const clearFilters = () => {
    setTagInput("");
    setFromInput("");
    setToInput("");
    onFiltersChange({ page: 0, size: filters.size });
  };

  return (
    <div className="flex flex-col gap-2">
      <div className="flex items-center gap-2">
        <Button
          variant="outline"
          size="sm"
          onClick={() => setShowFilters((v) => !v)}
          className="flex items-center gap-1.5"
        >
          <SlidersHorizontal className="h-4 w-4" />
          Filters
          {activeCount > 0 && (
            <Badge className="ml-1 h-5 w-5 rounded-full p-0 flex items-center justify-center text-xs bg-primary text-primary-foreground">
              {activeCount}
            </Badge>
          )}
        </Button>
        {activeCount > 0 && (
          <Button
            variant="ghost"
            size="sm"
            onClick={clearFilters}
            className="text-muted-foreground hover:text-foreground"
          >
            <X className="h-4 w-4 mr-1" />
            Clear
          </Button>
        )}
      </div>

      {showFilters && (
        <div className="flex flex-wrap gap-3 items-end p-4 rounded-lg border bg-muted/30">
          <div className="flex flex-col gap-1 min-w-[160px]">
            <label className="text-xs text-muted-foreground font-medium">
              Tag
            </label>
            <Input
              placeholder="e.g. milestone"
              value={tagInput}
              onChange={(e) => setTagInput(e.target.value)}
              className="h-8 text-sm"
              onKeyDown={(e) => e.key === "Enter" && applyFilters()}
            />
          </div>

          <div className="flex flex-col gap-1">
            <label className="text-xs text-muted-foreground font-medium">
              From
            </label>
            <Input
              type="date"
              value={fromInput}
              onChange={(e) => setFromInput(e.target.value)}
              className="h-8 text-sm"
            />
          </div>

          <div className="flex flex-col gap-1">
            <label className="text-xs text-muted-foreground font-medium">
              To
            </label>
            <Input
              type="date"
              value={toInput}
              onChange={(e) => setToInput(e.target.value)}
              className="h-8 text-sm"
            />
          </div>

          <Button size="sm" onClick={applyFilters} className="flex gap-1.5">
            <Search className="h-4 w-4" />
            Apply
          </Button>
        </div>
      )}
    </div>
  );
};

export default AchievementFilters;
