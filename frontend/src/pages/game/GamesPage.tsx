import React from "react";
import { useTimeSlotsAll } from "../../components/game/queries/game.queries";
import { ScrollArea, ScrollBar } from "../../components/ui/scroll-area";
import GameSlotSelector from "../../components/game/GameSlotSelector";
import { Separator } from "../../components/ui/separator";
import { Link, Outlet } from "react-router-dom";
import { Button } from "../../components/ui/button";
import { RoleUtil } from "../../auth/role.util";

const GamesPage = () => {
  const { data, error, isLoading } = useTimeSlotsAll(); // exclamation to supress undefined error
  console.log(data);

  //
  //

  if (error) {
    return <div>{error.message}</div>;
  }
  if (isLoading) {
    return <div>Loading....</div>;
  }

  return (
    <div className="w-full h-full p-10 flex flex-col gap-8 items-end">
      {(RoleUtil.isHr || RoleUtil.isAdmin) && (
        <Button className="w-fit">
          <Link to={"/game-config"}>Game Config</Link>
        </Button>
      )}
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
