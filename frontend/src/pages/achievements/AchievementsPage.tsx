import { useState } from "react";
import { Outlet } from "react-router-dom";
import {
  Card,
  CardContent,
  CardHeader,
  CardTitle,
} from "../../components/ui/card";
import AchievementFeed from "../../components/achievements/AchievementFeed";
import type { AchievementFilterParams } from "../../components/achievements/types/achievements.types";
import CreatePostButton from "../../components/achievements/CreatePostButton";
import AchievementFilters from "../../components/achievements/AchievementFilter";

const AchievementsPage = () => {
  const [filters, setFilters] = useState<AchievementFilterParams>({
    page: 0,
    size: 10,
  });

  return (
    <div className="flex flex-1 flex-col gap-4 p-4">
      <Card className="border-none shadow-none w-full">
        <CardHeader className="flex flex-row items-center justify-between pb-2">
          <CardTitle className="text-2xl font-bold">
            Achievements &amp; Celebrations
          </CardTitle>
          <CreatePostButton />
        </CardHeader>
        <CardContent className="flex flex-col gap-4">
          <AchievementFilters filters={filters} onFiltersChange={setFilters} />
          <AchievementFeed filters={filters} onFiltersChange={setFilters} />
        </CardContent>
      </Card>
      <Outlet />
    </div>
  );
};

export default AchievementsPage;
