import React from "react";
import { ScrollArea, ScrollBar } from "../ui/scroll-area";
import { GameSlotScroll } from "./GameSlotScroll";

const GameSlotSelector = ({ game }) => {
  return (
    <div className="flex flex-col gap-1 w-full h-fit min-h-fit max-w-full">
      <div className="capitalize text-xl font-bold">{game.gameTypeName}</div>
      <GameSlotScroll slotList={game.gameSlots} />
    </div>
  );
};

export default GameSlotSelector;
