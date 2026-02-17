import { useState } from "react";
import { useNavigate, useOutletContext, useParams } from "react-router-dom";
import {
	Dialog,
	DialogContent,
	DialogHeader,
	DialogTitle,
} from "../../ui/dialog";
import ExpenseViewCard from "./ExpenseViewCard";
import { ScrollArea } from "../../ui/scroll-area";

const TravelExpenseView = () => {
	const [open, setOpen] = useState(true);

	const navigate = useNavigate();
	const { id } = useParams<{ id: string }>();

	const handleClose = () => {
		navigate(`/travel/plans/${id}/expenses`);
	};

	const expenseData = useOutletContext();

	return (
		<Dialog open={open} onOpenChange={handleClose}>
			<DialogContent className="max-w-fit w-fit max-h-10/12 h-screen/50">
				<DialogHeader>
					<DialogTitle>View Expense</DialogTitle>
				</DialogHeader>
				<ExpenseViewCard />
			</DialogContent>
		</Dialog>
	);
};

export default TravelExpenseView;
