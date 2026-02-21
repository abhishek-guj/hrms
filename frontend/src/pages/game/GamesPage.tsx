import React from "react";
import { useTimeSlotsAll } from "../../components/game/queries/game.queries";
import { ScrollArea, ScrollBar } from "../../components/ui/scroll-area";
import GameSlotSelector from "../../components/game/GameSlotSelector";
import { Separator } from "../../components/ui/separator";
import { Outlet } from "react-router-dom";

const GamesPage = () => {
  const { data, error, isLoading } = useTimeSlotsAll(); // exclamation to supress undefined error

  //
  //

  if (error) {
    return <div>{error.message}</div>;
  }
  if (isLoading) {
    return <div>Loading....</div>;
  }

  return (
    <div className="w-full h-full p-10 flex flex-col gap-8">
      {data?.map((game) => {
        return (
          <>
            <GameSlotSelector game={game} />
            <Separator />
          </>
        );
      })}
      <Outlet />
    </div>
  );
};

export default GamesPage;
