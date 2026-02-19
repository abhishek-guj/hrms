import { IconShare3 } from "@tabler/icons-react";
import type { ColumnDef } from "@tanstack/react-table";
import { Eye, Pencil, PlusIcon, Trash, Trash2 } from "lucide-react";
import { Link } from "react-router-dom";
import DataTableBadge from "../travelPlans/TravelplanDashboard/Shared/DataTableBadge";
import { Button } from "../ui/button";
import type { JobDto } from "./types/job.types";

export const DEFAULT_STATUS = {
	label: "Empty",
	className:
		"bg-rose-500/15 text-rose-700 dark:bg-rose-500/10 dark:text-rose-400",
};

// id: string;
// jobTitle: string;
// jobDetails: string;
// experienceYears: number;
// numberOfVaccancy: number;
// createdBy: EmployeeProfileDto;
// createdOn: string;
// updatedBy: EmployeeProfileDto;
// updatedOn: string;
// status: string; // active or close
// statusChangedOn: string;

export const JobsTableColumns: ColumnDef<JobDto>[] = [
	{
		accessorKey: "id",
		header: "Job Id",
		cell: ({ row }) => (
			<span className="font-medium">{row.getValue("id")}</span>
		),
	},
	// {
	// 	accessorFn: (row) => `${row.createdBy.firstName} ${row.createdBy.lastName}`,
	// 	header: "Name",
	// 	cell: ({ row }) => (
	// 		<span className="font-medium">{row.getValue("Name")}</span>
	// 	),
	// },
	{
		accessorKey: "jobTitle",
		header: "Job Title",
		cell: ({ getValue }) => (
			<span className="font-medium">{getValue() as string}</span>
		),
	},
	{
		accessorKey: "experienceYears",
		header: "Exp. Years",
		cell: ({ getValue }) => (
			<span className="font-medium text-center">{getValue() as string}</span>
		),
	},
	{
		accessorKey: "numberOfVaccancy",
		header: "No. Of Vaccancy",
		cell: ({ getValue }) => (
			<span className="font-medium text-center">{getValue() as string}</span>
		),
	},
	{
		accessorKey: "status",
		header: "Status",
		cell: ({ getValue }) => <DataTableBadge status={getValue() ?? "pending"} />,
	},
	{
		accessorFn: (row) => `${row.createdBy.firstName} ${row.createdBy.lastName}`,
		accessorKey: "createdBy",
		header: "Created By",
		cell: ({ getValue }) => (
			<span className="font-medium text-center">{getValue() as string}</span>
		),
	},
	{
		accessorFn: (row) => `${row.updatedBy.firstName} ${row.updatedBy.lastName}`,
		accessorKey: "updatedBy",
		header: "Updated By",
		cell: ({ getValue }) => (
			<span className="font-medium text-center">{getValue() as string}</span>
		),
	},
	{
		id: "refer",
		header: () => <div className="text-center">Refer</div>,
		cell: ({ row }) => (
			<div className="text-right space-x-1">
				<Button asChild variant={"outline"}>
					<Link to={`${row.getValue("id")}/refer`} className="border">
						<PlusIcon className="h-4 w-4" />
						<span className="hidden lg:block">Refer</span>
					</Link>
				</Button>
			</div>
		),
	},
	{
		id: "share",
		header: () => <div className="text-center">Share</div>,
		cell: ({ row }) => (
			<div className="text-right space-x-1">
				<Button asChild variant={"outline"}>
					<Link to={`${row.getValue("id")}/share`} className="border">
						<IconShare3 className="h-4 w-4" />
						<span className="hidden lg:block">Share</span>
					</Link>
				</Button>
			</div>
		),
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
					<Link to={`${row.getValue("id")}/refer`} className="border">
						<Pencil className="h-4 w-4" />
						<span className="hidden lg:block">Edit</span>
					</Link>
				</Button>
				<Button asChild variant={"outline"}>
					<Link to={`${row.getValue("id")}/share`} className="border">
						<Trash2 className="h-4 w-4" />
						<span className="hidden lg:block">Delete</span>
					</Link>
				</Button>
			</div>
		),
	},
];
