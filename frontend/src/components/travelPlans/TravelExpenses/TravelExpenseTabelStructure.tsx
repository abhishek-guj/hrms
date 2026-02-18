import type { ColumnDef } from "@tanstack/react-table";
import { Eye, Trash } from "lucide-react";
import { Link } from "react-router-dom";
import { Button } from "../../ui/button";
import DataTableBadge from "../TravelplanDashboard/Shared/DataTableBadge";
import type { TravelExpenseDto } from "../types/TravelPlan.types";
import type { DataTableStatus } from "../../shared/shared.types";

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
	active: {
		label: "active",
		className:
			"bg-emerald-500/15 text-emerald-700 dark:bg-emerald-500/10 dark:text-emerald-400",
	},
	closed: {
		label: "Pending",
		className:
			"bg-amber-500/15 text-amber-700 dark:bg-amber-500/10 dark:text-amber-400",
	},
};

export const DEFAULT_STATUS = {
	label: "Empty",
	className:
		"bg-rose-500/15 text-rose-700 dark:bg-rose-500/10 dark:text-rose-400",
};

export const TravelExpenseTableColumns: ColumnDef<TravelExpenseDto>[] = [
	{
		accessorKey: "id",
		header: "Expense Id",
		cell: ({ row }) => (
			<span className="font-medium">{row.getValue("id")}</span>
		),
	},
	{
		accessorFn: (row) =>
			`${row.submittedBy.firstName} ${row.submittedBy.lastName}`,
		header: "Name",
		cell: ({ row }) => (
			<span className="font-medium">{row.getValue("Name")}</span>
		),
	},
	{
		accessorKey: "expenseUploadDate",
		header: "Upload Date",

		cell: ({ getValue }) => (
			<span className="font-medium">{getValue() as string}</span>
		),
	},
	{
		accessorKey: "expenseAmount",
		header: "Amount",
		cell: ({ getValue }) => (
			<span className="font-medium">{getValue() as string}</span>
		),
	},
	{
		accessorKey: "expenseDate",
		header: "Expense Date",

		cell: ({ getValue }) => (
			<span className="font-medium">{getValue() as string}</span>
		),
	},
	{
		accessorKey: "status",
		header: "Status",
		cell: ({ getValue }) => <DataTableBadge status={getValue() ?? "pending"} />,
		// cell: ({ getValue }) => <div>{getValue() as string}</div>,
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
					<Link to={`${row.getValue("id")}/delete`} className="border">
						<Trash className="h-4 w-4" />
						<span className="hidden lg:block">Delete</span>
					</Link>
				</Button>
			</div>
		),
	},
];
