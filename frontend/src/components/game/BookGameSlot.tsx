import { useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { Dialog, DialogContent, DialogHeader, DialogTitle } from "../ui/dialog";
import ExpenseCreateForm from "../travelPlans/TravelExpenses/forms/ExpenseCreateForm";
import BookGameSlotForm from "./forms/BookGameSlotForm";

const BookGameSlot = () => {
  const [open, setOpen] = useState(true);

  const navigate = useNavigate();

  const { id } = useParams<{ id: string }>();

  const handleClose = () => {
    navigate(`/games`);
  };
  return (
    <Dialog open={open} onOpenChange={handleClose}>
      <DialogContent className="w-96">
        <DialogHeader>
          <DialogTitle>New Expense</DialogTitle>
        </DialogHeader>
        <BookGameSlotForm />
      </DialogContent>
    </Dialog>
  );
};

export default BookGameSlot;
