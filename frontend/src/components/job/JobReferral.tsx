import {
	Dialog,
	DialogContent,
	DialogHeader,
	DialogTitle,
} from "@/components/ui/dialog";
import { useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import ExpenseCreateForm from "./forms/JobReferralForm";
import JobReferralForm from "./forms/JobReferralForm";

const JobReferral = () => {
	const [open, setOpen] = useState(true);

	const navigate = useNavigate();

	const { id } = useParams<{ id: string }>();

	const handleClose = () => {
		navigate(`/jobs`);
	};
	return (
		<Dialog open={open} onOpenChange={handleClose}>
			<DialogContent className="w-1/2">
				<DialogHeader>
					<DialogTitle>Refer</DialogTitle>
				</DialogHeader>
				<JobReferralForm />
			</DialogContent>
		</Dialog>
	);
};

export default JobReferral;
