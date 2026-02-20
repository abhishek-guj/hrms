import { useNavigate, useOutletContext, useParams } from "react-router-dom";
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
import {
	useDeleteJob,
	useJobsById,
	useUpdateStatus,
} from "./queries/job.queries";
import FormSelect from "../travelPlans/TravelExpenses/forms/FormSelect";
import { useState } from "react";

const status_options = [
	{ id: "review", name: "Review" },
	{ id: "interview", name: "Interview" },
	{ id: "hold", name: "Hold" },
	{ id: "accepted", name: "Accepted" },
	{ id: "rejected", name: "Rejected" },
];

const ReferralStatusChange = () => {
	const { referId } = useParams<{ referId: string }>();
	const navigate = useNavigate();

	// context
	const { data: rawData } = useOutletContext();
	const data = rawData?.filter((d) => `${d.id}` === referId)[0];
	const status = data?.status?.toLowerCase();

	console.log(data);


	// states
	const [value, setValue] = useState(status);

	// query hook
	const changeReferStatus = useUpdateStatus();

	// handlers
	const handleChange = async () => {
		await changeReferStatus.mutateAsync({ id: referId!, status: value });
		navigate(`/referrals`);
	};

	const handleClose = () => {
		navigate(`/referrals`);
	};

	return (
		<Dialog open={open} onOpenChange={handleClose}>
			<DialogContent className="sm:max-w-md">
				<DialogHeader>
					<DialogTitle>Change Referral Status</DialogTitle>
					<DialogDescription>
						Change Referal Status of Refer Id. {data?.id} from Status :{" "}
						{data?.status} to
					</DialogDescription>
				</DialogHeader>
				<FormSelect
					value={value}
					onValueChange={setValue}
					data={status_options}
					type={"status"}
				/>
				<DialogFooter>
					<DialogClose asChild>
						<Button variant={"outline"} type="button">
							Close
						</Button>
					</DialogClose>
					<Button type="button" variant="destructive" onClick={handleChange}>
						Change
					</Button>
				</DialogFooter>
			</DialogContent>
		</Dialog>
	);
};

export default ReferralStatusChange;
