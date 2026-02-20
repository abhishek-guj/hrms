import type { ColumnDef } from "@tanstack/react-table";
import { Eye, Pencil, Trash2 } from "lucide-react";
import { Link } from "react-router-dom";
import { Button } from "../../ui/button";
import type { DataTabelItem } from "../types/TravelPlan.types";
import DataTableBadge from "./Shared/DataTableBadge";

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

const checkOngoing = (startDate, endDate) => {
	const today = new Date();
	// console.log(today, new Date(endDate), today < new Date(endDate));
	if (today < new Date(endDate) && today > new Date(startDate)) {
		console.log(today, new Date(endDate), today > new Date(endDate));
		return "ongoing";
	} else if (today > new Date(endDate)) {
		return "completed";
	} else {
		return "upcoming";
		console.log("not start");
	}
};

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
		accessorKey: "travelType.name",
		header: "TravelType",
		cell: ({ getValue, row }) => {
			// Show group label for grouped rows
			if (row.getIsGrouped()) {
				return (
					<span className="font-bold">
						{getValue() as string} ({row.subRows.length})
					</span>
				);
			}
			return getValue() as string;
		},
		// cell: (row) => <span className="font-medium">{row.getValue()}</span>,
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
		cell: ({ row }) => {
			checkOngoing(row.getValue("startDate"), row.getValue("endDate"));
			return (
				<DataTableBadge
					status={checkOngoing(
						row.getValue("startDate"),
						row.getValue("endDate"),
					)}
				/>
			);
		},
	},
	{
		id: "actions",
		header: () => <div className="text-center">Actions</div>,
		cell: ({ row }) => (
			<div className="text-right space-x-1">
				<Button asChild variant={"outline"}>
					<Link to={`${row.getValue("id")}`} className="border">
						<Eye className="h-4 w-4" />
						<span className="hidden lg:block">View</span>
					</Link>
				</Button>
				<Button asChild variant={"outline"}>
					<Link to={`${row.getValue("id")}/edit`} className="border">
						<Pencil className="h-4 w-4" />
						<span className="hidden lg:block">Edit</span>
					</Link>
				</Button>
				<Button asChild variant={"outline"} className="text-destructive">
					<Link to={`${row.getValue("id")}/delete`} className="border">
						<Trash2 className="h-4 w-4" />
						<span className="hidden lg:block">Delete</span>
					</Link>
				</Button>
			</div>
		),
	},
];
