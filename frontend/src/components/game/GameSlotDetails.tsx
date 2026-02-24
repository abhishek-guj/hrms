import React from "react";
import type { GameSlotDto } from "./types/game.types";
import { format } from "date-fns";
import { Separator } from "../ui/separator";
import AssignEmloyeeSelect from "../travelPlans/TravelplanDashboard/forms/AssignEmloyeeSelect";

const GameSlotDetails = ({
  slotDetails,
  slotSizes,
}: {
  slotDetails: GameSlotDto;
  slotSizes: Number[];
}) => {
  console.log(slotDetails, slotSizes);

  const startDate = format(slotDetails?.slotStart.toString(), "dd/MM");
  const startTime = format(slotDetails?.slotStart.toString(), "HH:mm");
  const endTime = format(slotDetails?.slotEnd.toString(), "HH:mm");
  const slotSizesView = slotSizes?.join(", ");
  const booked = slotDetails?.booked ? "Booked" : "Not Booked";
  const lowPriority = slotDetails?.booked
    ? slotDetails?.lowPriority
      ? "You can Book"
      : "You can't Book"
    : "You can Book";

  return (
    <div className="flex flex-col gap-2 justify-center m-auto w-fit ">
      <div className="grid grid-cols-2 gap-8">
        <ViewField name={"Game"} value={slotDetails?.gameTypeName} />
        <ViewField name={"Date"} value={startDate} />
      </div>
      <Separator />
      <div className="grid grid-cols-2 gap-8">
        <ViewField name={"Slot Start"} value={startTime} />
        <ViewField name={"Slot End"} value={endTime} />
      </div>
      <Separator />
      <div className="grid grid-cols-2 gap-8">
        <ViewField name={"Booking Status"} value={booked} />
        <ViewField name={"Priority"} value={lowPriority} />
      </div>
      <div className="grid grid-cols-2 gap-8">
        <ViewField name={"Slot Sizes"} value={slotSizesView} />
      </div>
    </div>
  );
};

export default GameSlotDetails;

export function ViewField({
  name,
  value,
}: Readonly<{ name: string; value: string }>) {
  console.log(value)
  return (
    <div className="flex flex-col gap-0.5">
      <div className="text-black/70 text-sm">{name}</div>
      <div className="capitalize font-bold">{value}</div>
    </div>
  );
}
