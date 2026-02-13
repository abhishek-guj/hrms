import {
	Dialog,
	DialogContent,
	DialogHeader,
	DialogTitle
} from "@/components/ui/dialog";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import TravelPlanViewForm from "./forms/TravelPlanViewForm";

const TravelPlanView = () => {
	const [open, setOpen] = useState(true);

	const navigate = useNavigate();

	const handleClose = () => {
		navigate("/travel/plans");
	};

	return (
		<Dialog open={open} onOpenChange={handleClose}>
			<DialogHeader>
				<DialogTitle>View Travel Plan</DialogTitle>
			</DialogHeader>
			<DialogContent className="p-0">
				<TravelPlanViewForm />
			</DialogContent>
		</Dialog>
	);
};

export default TravelPlanView;
