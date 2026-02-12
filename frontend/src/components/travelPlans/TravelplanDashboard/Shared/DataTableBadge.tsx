import React from "react";
import type { DataTableStatus } from "../types/TravelPlan.types";
import { Badge } from "@/components/ui/badge";
import { cn } from "@/lib/utils";
import { DataTableStatusConfig } from "../TravelPlansTabelStructure";

const DataTableBadge = ({ status }: { status: DataTableStatus }) => {
	// select status and its className
	const config =
		DataTableStatusConfig[status as keyof typeof DataTableStatusConfig];
	return (
		<Badge variant="outline" className={cn("border-0", config.className)}>
			{config.label}
		</Badge>
	);
};

export default DataTableBadge;
