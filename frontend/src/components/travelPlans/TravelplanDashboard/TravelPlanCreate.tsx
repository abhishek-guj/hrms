import {
	Dialog,
	DialogContent,
	DialogHeader,
	DialogTitle,
} from "@/components/ui/dialog";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import TravelPlanCreateForm from "./forms/TravelPlanCreateForm";

const TravelPlanCreate = () => {
	const [open, setOpen] = useState(true);

	const navigate = useNavigate();

	const handleClose = () => {
		navigate("/travel/plans");
	};

	return (
		<Dialog open={open} onOpenChange={handleClose}>
			<DialogContent className="w-96">
				<DialogHeader>
					<DialogTitle>Create Travel Plan</DialogTitle>
				</DialogHeader>
				<TravelPlanCreateForm />
			</DialogContent>
		</Dialog>
	);
};

export default TravelPlanCreate;
