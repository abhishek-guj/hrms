import { useNavigate, useParams } from "react-router-dom";
import { Button } from "../../ui/button";
import {
	Dialog,
	DialogClose,
	DialogContent,
	DialogDescription,
	DialogFooter,
	DialogHeader,
	DialogTitle,
} from "../../ui/dialog";
import { showSuccess } from "../../ui/toast";
import {
	useDeleteTravelExpense,
	useTravelExpenseById,
} from "../queries/travelPlans.queries";

const TravelExpenseDelete = () => {
	// getting id of open travel plan
	const { id, expenseId } = useParams<{ id: string; expenseId: string }>();

	const navigate = useNavigate();

	// query hook
	const { data, error, isLoading } = useTravelExpenseById(expenseId!);
	const deleteTravelPlan = useDeleteTravelExpense();

	// handlers
	const handleDelete = async () => {
		await deleteTravelPlan.mutateAsync({ id: expenseId! });
		showSuccess("Successfully deleted Travel Expense");
		navigate(`/travel/plans/${id}/expenses`);
	};

	const handleClose = () => {
		navigate(`/travel/plans/${id}/expenses`);
	};

	if (error) {
		return <div>{error.message}</div>;
	}
	if (isLoading) {
		return <div>Loading....</div>;
	}

	return (
		<Dialog open={open} onOpenChange={handleClose}>
			<DialogContent className="sm:max-w-md">
				<DialogHeader>
					<DialogTitle>Delete Travel Plan</DialogTitle>
					<DialogDescription>
						Are you sure you wan to delete this Travel Plan?
					</DialogDescription>
				</DialogHeader>
				<div className="space-y-2">{data?.purpose}</div>
				<DialogFooter>
					<DialogClose asChild>
						<Button variant={"outline"} type="button">
							Close
						</Button>
					</DialogClose>
					<Button type="button" variant="destructive" onClick={handleDelete}>
						Delete
					</Button>
				</DialogFooter>
			</DialogContent>
		</Dialog>
	);
};

export default TravelExpenseDelete;
