import {
	Dialog,
	DialogContent,
	DialogHeader,
	DialogTitle,
} from "@/components/ui/dialog";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import TravelPlanEditForm from "./forms/TravelPlanEditForm";

const TravelPlanEdit = () => {
	const [open, setOpen] = useState(true);

	const navigate = useNavigate();

	const handleClose = () => {
		navigate("/travel/plans");
	};

	return (
		<Dialog open={open} onOpenChange={handleClose}>
			<DialogContent className="w-96">
				<DialogHeader>
					<DialogTitle>Edit Travel Plan</DialogTitle>
				</DialogHeader>
				<TravelPlanEditForm />
			</DialogContent>
		</Dialog>
	);
};

export default TravelPlanEdit;
