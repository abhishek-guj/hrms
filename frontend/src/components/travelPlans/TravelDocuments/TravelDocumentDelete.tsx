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
import {
	useDeleteTravelDocument,
	useTravelDocumentById,
} from "../queries/travelDocuments.queries";

const TravelDocumentDelete = () => {
	// getting id of open travel plan
	const { id, documentId } = useParams<{ id: string; documentId: string }>();

	const navigate = useNavigate();

	// query hook
	const { data, error, isLoading } = useTravelDocumentById(id!, documentId!);
	const deleteDoc = useDeleteTravelDocument();

	// handlers
	const handleDelete = async () => {
		await deleteDoc.mutateAsync({ id: id!, docId: documentId! });
		showSuccess("Successfully deleted Travel Doc");
		navigate(`/travel/plans/${id}/documents`);
	};

	const handleClose = () => {
		navigate(`/travel/plans/${id}/documents`);
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
						Are you sure you want to delete this Travel delete?
					</DialogDescription>
				</DialogHeader>
				{console.log(data)}
				<div className="space-y-2">
					Document {data?.documentTypeName} uploaded by {data?.uploadedByName}{" "}
					for {data?.uploadedForName}
				</div>
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

export default TravelDocumentDelete;
