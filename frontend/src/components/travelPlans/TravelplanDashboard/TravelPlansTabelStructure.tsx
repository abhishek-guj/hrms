import type { ColumnDef } from "@tanstack/react-table";
import type { DataTabelItem, DataTableStatus } from "../types/TravelPlan.types";
import { Button } from "../../ui/button";
import { Eye, Pencil, Trash2 } from "lucide-react";
import DataTableBadge from "./Shared/DataTableBadge";
import { Link, useNavigate } from "react-router-dom";

export const DataTableStatusConfig: Record<
	DataTableStatus,
	{ label: string; className: string }
> = {
	completed: {
		label: "Completed",
		className:
			"bg-emerald-500/15 text-emerald-700 dark:bg-emerald-500/10 dark:text-emerald-400",
	},
	pending: {
		label: "Pending",
		className:
			"bg-amber-500/15 text-amber-700 dark:bg-amber-500/10 dark:text-amber-400",
	},
	processing: {
		label: "Processing",
		className:
			"bg-blue-500/15 text-blue-700 dark:bg-blue-500/10 dark:text-blue-400",
	},
	cancelled: {
		label: "Cancelled",
		className:
			"bg-rose-500/15 text-rose-700 dark:bg-rose-500/10 dark:text-rose-400",
	},
	undefined: {
		label: "Cancelled",
		className:
			"bg-rose-500/15 text-rose-700 dark:bg-rose-500/10 dark:text-rose-400",
	},
};

//  define structure of each column,
//  in cell it defines, what should be displayed in there.

// {
//     "id": 1,
//     "purpose": "Travel 1",
//     "travelType": {
//         "id": 1,
//         "name": "Business"
//     },
//     "startDate": "2026-02-15",
//     "endDate": "2026-02-23",
//     "destinationCity": "Mumbai",
//     "destinationState": "Maharashtra",
//     "destinationCountry": "India",
//     "lastDateOfExpenseSubmission": "2026-02-28",
//     "maxAmountPerDay": 2000
// }

export const TravelPlanTableColumns: ColumnDef<DataTabelItem>[] = [
	{
		accessorKey: "id",
		header: "Travel Id",
		cell: ({ row }) => (
			<span className="font-medium">{row.getValue("id")}</span>
		),
	},
	{
		accessorKey: "purpose",
		header: "Purpose",
		cell: ({ row }) => (
			<span className="font-medium">{row.getValue("purpose")}</span>
		),
	},
	{
		accessorKey: "travelType",
		header: "TravelType",
		cell: ({ row }) => (
			<span className="font-medium">{row.getValue("travelType")?.name}</span>
		),
	},
	{
		accessorKey: "startDate",
		header: "Start Date",
		cell: ({ row }) => (
			<span className="font-medium">{row.getValue("startDate")}</span>
		),
	},
	{
		accessorKey: "endDate",
		header: "End Date",
		cell: ({ row }) => (
			<span className="font-medium">{row.getValue("endDate")}</span>
		),
	},
	{
		accessorKey: "status",
		header: "Status",
		cell: ({ row }) => <DataTableBadge status={row.getValue("status")} />,
	},
	{
		id: "actions",
		header: () => <div className="text-center">Actions</div>,
		cell: ({ row }) => (
			<div className="text-right space-x-1">
				<Button asChild variant={"outline"}>
					<Link to={`${row.getValue("id")}`} className="border">
						<Eye className="mr-2 h-4 w-4" />
						View
					</Link>
				</Button>
				<Button asChild variant={"outline"}>
					<Link to={`${row.getValue("id")}/edit`} className="border">
						<Pencil className="mr-2 h-4 w-4" />
						Edit
					</Link>
				</Button>
				<Button asChild variant={"outline"} className="text-destructive">
					<Link to={`${row.getValue("id")}/delete`} className="border">
						<Trash2 className="mr-2 h-4 w-4" />
						Delete
					</Link>
				</Button>
			</div>
		),
	},
];
