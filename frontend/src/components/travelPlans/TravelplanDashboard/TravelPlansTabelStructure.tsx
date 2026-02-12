import type { ColumnDef } from "@tanstack/react-table";
import type { DataTabelItem, DataTableStatus } from "../types/TravelPlan.types";
import { Button } from "../../ui/button";
import { Eye, Pencil, Trash2 } from "lucide-react";
import DataTableBadge from "./Shared/DataTableBadge";

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
};

//  define structure of each column,
//  in cell it defines, what should be displayed in there.
export const TravelPlanTableColumns: ColumnDef<DataTabelItem>[] = [
	{
		accessorKey: "name",
		header: "Name",
		cell: ({ row }) => (
			<span className="font-medium">{row.getValue("name")}</span>
		),
	},
	{
		accessorKey: "date",
		header: "Date",
	},
	{
		accessorKey: "status",
		header: "Status",
		cell: ({ row }) => <DataTableBadge status={row.getValue("status")} />,
	},
	{
		accessorKey: "amount",
		header: () => <div className="text-right">Amount</div>,
		cell: ({ row }) => (
			<div className="text-right font-medium">{row.getValue("amount")}</div>
		),
	},
	{
		id: "actions",
		cell: () => (
			<div className="text-right">
				<Button variant={"outline"}>
					<Eye className="mr-2 h-4 w-4" />
				</Button>
				<Button variant={"outline"}>
					<Pencil className="mr-2 h-4 w-4" />
				</Button>
				<Button variant={"outline"} className="text-destructive">
					<Trash2 className="mr-2 h-4 w-4" />
				</Button>
			</div>
		),
	},
];

export const TravelPlanTableData: DataTabelItem[] = [
	{
		id: "1",
		name: "Project Alpha",
		date: "Jan 15, 2024",
		status: "completed",
		amount: "$2,500",
	},
	{
		id: "2",
		name: "Website Redesign",
		date: "Feb 3, 2024",
		status: "processing",
		amount: "$4,200",
	},
	{
		id: "3",
		name: "Mobile App MVP",
		date: "Feb 18, 2024",
		status: "pending",
		amount: "$8,750",
	},
	{
		id: "4",
		name: "Brand Identity",
		date: "Mar 5, 2024",
		status: "completed",
		amount: "$1,800",
	},
	{
		id: "5",
		name: "Marketing Campaign",
		date: "Mar 22, 2024",
		status: "cancelled",
		amount: "$3,400",
	},
	{
		id: "6",
		name: "Analytics Dashboard",
		date: "Apr 8, 2024",
		status: "processing",
		amount: "$5,600",
	},
	{
		id: "7",
		name: "E-commerce Platform",
		date: "Apr 25, 2024",
		status: "pending",
		amount: "$12,000",
	},
	{
		id: "8",
		name: "API Integration",
		date: "May 10, 2024",
		status: "completed",
		amount: "$3,200",
	},
];
