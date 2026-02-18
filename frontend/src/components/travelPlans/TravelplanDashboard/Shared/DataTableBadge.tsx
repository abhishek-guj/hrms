import React from "react";
import { Badge } from "@/components/ui/badge";
import { cn } from "@/lib/utils";
import {
	DataTableStatusConfig,
	DEFAULT_STATUS,
} from "../../TravelExpenses/TravelExpenseTabelStructure";
import type { DataTableStatus } from "../../../shared/shared.types";

const DataTableBadge = ({ status }: { status: DataTableStatus }) => {
	// select status and its className
	const config =
		DataTableStatusConfig[
			status?.toLowerCase() as keyof typeof DataTableStatusConfig
		] ?? DEFAULT_STATUS;
	return (
		<Badge variant="outline" className={cn("border-0", config?.className)}>
			{config?.label}
		</Badge>
	);
};

export default DataTableBadge;
