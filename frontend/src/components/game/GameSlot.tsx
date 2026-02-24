import { format } from "date-fns";
import React from "react";
import { Button } from "../ui/button";
import { showInfo } from "../ui/toast";
import { useNavigate, useParams } from "react-router-dom";

const GameSlot = ({ slot }) => {
  // derieved fields
  const startTime = format(slot.slotStart.toString(), "HH:mm");
  const endTime = format(slot.slotEnd.toString(), "HH:mm");

  const startDate = format(slot.slotStart.toString(), "dd/MM");
  const startDayName = format(slot.slotStart.toString(), "EEEE");

  const slotColor = getSlotColor(slot);
  const confirmed = slot?.confirmed;

  // hoooks
  const navigate = useNavigate();

  // handlers
  const handleOpenSlotBooking = () => {
    showInfo(slot.id);
    navigate(`${slot.id}`);
  };

  return (
    <Button
      variant={"outline"}
      className={`w-fit p-3 h-fit flex flex-col gap-0 items-center rounded-2xl ${slotColor}`}
      //   className=
      onClick={handleOpenSlotBooking}
      disabled={confirmed}
    >
      <div className="text-sm font-bold text-black/50 pt-4">{startDate}</div>
      <div className="text-base font-bold">{startDayName}</div>
      <div className="w-full text-xl font-black p-2">{`${startTime}  -  ${endTime}`}</div>
    </Button>
  );
};

const getSlotColor = (slot) => {
  if (slot.booked && slot.lowPriority) {
    console.log(slot);
    return "bg-amber-100";
  } else if (slot.booked) {
    return "bg-red-100";
  } else if (slot.confirmed) {
    return "bg-white-100";
  } else {
    return "bg-neutral-100";
  }
};

export default GameSlot;
