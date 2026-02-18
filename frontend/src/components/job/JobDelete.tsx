import { useNavigate, useParams } from "react-router-dom";
import { Button } from "../ui/button";
import {
	Dialog,
	DialogClose,
	DialogContent,
	DialogDescription,
	DialogFooter,
	DialogHeader,
	DialogTitle,
} from "../ui/dialog";
import { useDeleteJob, useJobsById } from "./queries/job.queries";

const JobDelete = () => {
	// getting id of open travel plan
	const { jobId } = useParams<{ jobId: string }>();

	const navigate = useNavigate();

	// query hook
	const { data, error, isLoading } = useJobsById(jobId!);
	const deleteTravelPlan = useDeleteJob();

	// handlers
	const handleDelete = async () => {
		await deleteTravelPlan.mutateAsync({ id: jobId! });
		navigate(`/jobs`);
	};

	const handleClose = () => {
		navigate(`/jobs`);
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
						Are you sure you want to delete this Job?
					</DialogDescription>
				</DialogHeader>
				<div className="space-y-2">{data?.jobTitle}</div>
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

export default JobDelete;
