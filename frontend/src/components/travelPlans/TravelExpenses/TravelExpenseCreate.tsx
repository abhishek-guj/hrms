import {
	Dialog,
	DialogContent,
	DialogHeader,
	DialogTitle,
} from "@/components/ui/dialog";
import { useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import ExpenseCreateForm from "./forms/ExpenseCreateForm";

const TravelExpenseCreate = () => {
	const [open, setOpen] = useState(true);

	const navigate = useNavigate();

	const { id } = useParams<{ id: string }>();

	const handleClose = () => {
		navigate(`/travel/plans/${id}/expenses`);
	};
	return (
		<Dialog open={open} onOpenChange={handleClose}>
			<DialogContent className="w-96">
				<DialogHeader>
					<DialogTitle>New Expense</DialogTitle>
				</DialogHeader>
				<ExpenseCreateForm />
			</DialogContent>
		</Dialog>
	);
};

export default TravelExpenseCreate;
