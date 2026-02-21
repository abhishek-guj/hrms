import React from "react";
import GameSlot from "./GameSlot";
import { ScrollArea, ScrollBar } from "../ui/scroll-area";

export const GameSlotScroll = ({ slotList }) => {
  return (
    <ScrollArea className="w-full h-full grow">
      <div className="flex gap-2.5 py-4">
        {slotList?.map((slot) => (
          <GameSlot slot={slot} />
        ))}
      </div>
      <ScrollBar orientation="horizontal" />
    </ScrollArea>
  );
};
