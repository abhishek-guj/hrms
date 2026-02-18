import { useNavigate, useParams } from "react-router-dom";
import { Button } from "../../../ui/button";
import {
	DialogClose,
	DialogContent,
	DialogDescription,
	DialogFooter,
	DialogHeader,
	DialogTitle,
} from "../../../ui/dialog";
import {
	useDeleteTravelPlan,
	useTravelPlan,
} from "../../queries/travelPlans.queries";
import { showSuccess } from "../../../ui/toast";

const TravelPlanDeleteDialog = () => {
	// getting id of open travel plan
	const { id } = useParams<{ id: string }>();

	const navigate = useNavigate();

	console.log(id);
	// query hook
	const { data, error, isLoading } = useTravelPlan(id!);
	const deleteTravelPlan = useDeleteTravelPlan();

	console.log(data);

	// handlers
	const handleDelete = async () => {
		await deleteTravelPlan.mutateAsync({ id: id });
		showSuccess("Successfully deleted Travel Plan");
		navigate("/travel/plans");
	};

	if (error) {
		return <div>{error.message}</div>;
	}
	if (isLoading) {
		return <div>Loading....</div>;
	}

	//
	return (
		<>
			{/* // <Card className="border-none"> */}
			{/* <CardHeader className="border">
				<CardTitle>Delete Travel Plan</CardTitle>
			</CardHeader> */}
			<DialogContent className="sm:max-w-md">
				<DialogHeader>
					<DialogTitle>Delete Travel Plan</DialogTitle>
					<DialogDescription>
						Are you sure you want to delete this Travel Plan?
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
			{/* <CardContent className="border">
				<div className="grid grid-cols-1 py-3">
					<div className="text-black/50 text-sm my-1">
						Are you sure you want to delete?
					</div>
					<div className="p-1 pb-2">{data?.purpose}</div>
				</div>
			</CardContent> */}
		</>
	);
};

export default TravelPlanDeleteDialog;
