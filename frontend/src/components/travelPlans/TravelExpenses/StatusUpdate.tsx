import React from "react";
import { Button } from "../../ui/button";
import { Check, X } from "lucide-react";
import { api } from "../../../api/apiClient";
import { useQueryClient } from "@tanstack/react-query";
import { useChangeStatus } from "../queries/travelPlans.queries";

const StatusUpdate = ({ id }) => {
	const queryClient = useQueryClient();
	const updateStatus = useChangeStatus();
	const handleAccept = async () => {
		// api.put(`/travel-expense/${id}/status`, { status: true });
		updateStatus.mutateAsync({ id: id, payload: true });
	};
	const handleReject = async () => {
		updateStatus.mutateAsync({ id: id, payload: false });
	};
	return (
		<div className="text-center space-x-1">
			<Button variant={"outline"} onClick={handleAccept}>
				<Check className="h-4 w-4" />
				<span className="hidden lg:block">Accept</span>
			</Button>
			<Button variant={"outline"} onClick={handleReject}>
				<X className="h-4 w-4" />
				<span className="hidden lg:block">Reject</span>
			</Button>
		</div>
	);
};

export default StatusUpdate;
