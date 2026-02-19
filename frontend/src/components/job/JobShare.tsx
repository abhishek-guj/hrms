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
import { useDeleteJob, useJobsById, useShareJob } from "./queries/job.queries";
import { Input } from "../ui/input";
import { useState } from "react";

const JobShare = () => {
	const [email, setEmail] = useState("");
	const [emailLoading, setEmailLoading] = useState(false);

	// getting id of open travel plan
	const { jobId } = useParams<{ jobId: string }>();

	const navigate = useNavigate();

	// query hook
	const { data, error, isLoading } = useJobsById(jobId!);

	const shareJob = useShareJob();

	// handlers
	const handleShare = async () => {
		setEmailLoading(true);
		await shareJob.mutateAsync({ id: jobId!, email: email });
		navigate(`/jobs/${jobId}/share`);
		setEmailLoading(false);
		setEmail("");
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
					<DialogTitle>Share Job</DialogTitle>
					<DialogDescription>
						Please mention email ids of all.
					</DialogDescription>
				</DialogHeader>
				<Input
					type="email"
					onChange={(e) => {
						setEmail(e.target.value);
					}}
					value={email}
				></Input>
				<DialogFooter>
					<DialogClose asChild>
						<Button variant={"outline"} type="button">
							Close
						</Button>
					</DialogClose>
					<Button
						type="button"
						variant="default"
						onClick={handleShare}
						disabled={emailLoading}
					>
						Share
					</Button>
				</DialogFooter>
			</DialogContent>
		</Dialog>
	);
};

export default JobShare;
