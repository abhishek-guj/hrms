import { useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { Dialog, DialogContent, DialogDescription, DialogHeader, DialogTitle } from "../ui/dialog";
import ExpenseCreateForm from "../travelPlans/TravelExpenses/forms/ExpenseCreateForm";
import BookGameSlotForm from "./forms/BookGameSlotForm";
import { ScrollArea } from "../ui/scroll-area";

const BookGameSlot = () => {
  const [open, setOpen] = useState(true);

  const navigate = useNavigate();

  const { id } = useParams<{ id: string }>();

  const handleClose = () => {
    navigate(`/games`);
  };
  return (
    <Dialog open={open} onOpenChange={handleClose}>
      <DialogContent className="flex flex-col w-1/2 h-5/6 overflow-hidden">
        <DialogHeader>
          <DialogTitle>Slot Details</DialogTitle>
        </DialogHeader>
        <ScrollArea className="h-full">

        <BookGameSlotForm />
        </ScrollArea>
      </DialogContent>
    </Dialog>
  );
};

export default BookGameSlot;
