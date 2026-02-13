import {
	Dialog,
	DialogContent,
	DialogHeader,
	DialogTitle,
} from "@/components/ui/dialog";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import TravelPlanDeleteDialog from "./forms/TravelPlanDeleteDialog";

const TravelPlanDelete = () => {
	const [open, setOpen] = useState(true);

	const navigate = useNavigate();

	const handleClose = () => {
		navigate("/travel/plans");
	};

	return (
		<Dialog open={open} onOpenChange={handleClose}>
			<TravelPlanDeleteDialog />
		</Dialog>
	);
};

export default TravelPlanDelete;
