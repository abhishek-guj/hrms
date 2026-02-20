import { IconShare3 } from "@tabler/icons-react";
import type { ColumnDef } from "@tanstack/react-table";
import { Eye, FileIcon, Pencil, PlusIcon, Trash, Trash2 } from "lucide-react";
import { Link } from "react-router-dom";
import DataTableBadge from "../travelPlans/TravelplanDashboard/Shared/DataTableBadge";
import { Button } from "../ui/button";
import type { JobDto, JobReferralDto } from "./types/job.types";
import { format } from "date-fns";

export const ReferralsTableColumns: ColumnDef<JobReferralDto>[] = [
	{
		accessorKey: "id",
		header: "Refer Id",
		cell: ({ row }) => (
			<span className="font-medium">{row.getValue("id")}</span>
		),
	},
	{
		accessorKey: "jobId",
		header: "Job Id",
		cell: ({ row }) => (
			<span className="font-medium">{row.getValue("id")}</span>
		),
	},
	{
		accessorFn: (row) => `${row.firstName} ${row.lastName}`,
		accessorKey: "fullName",
		header: "Name",
		cell: ({ getValue }) => (
			<span className="font-medium">{getValue() as string}</span>
		),
	},
	{
		accessorKey: "contactNumber",
		header: "Number",
		cell: ({ getValue }) => (
			<span className="font-medium text-center">{getValue() as string}</span>
		),
	},
	{
		accessorKey: "email",
		header: "Email",
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
		accessorKey: "statusChangedOn",
		header: "Status Changed On",
		cell: ({ getValue }) => (
			<span className="font-medium text-center">
				{format(getValue(), "dd/MM/yyyy HH:mm:ss")}
			</span>
		),
	},
	{
		id: "actions",
		header: () => <div className="text-center">Update Status</div>,
		cell: ({ row }) => (
			<div className="text-center ">
				<Button asChild variant={"outline"}>
					<Link to={`${row.getValue("id")}`} className="border">
						<Pencil className="h-4 w-4" />
						<span className="hidden lg:block">Update</span>
					</Link>
				</Button>
			</div>
		),
	},
	{
		accessorFn: (row) =>
			`${row.referredBy.firstName} ${row.referredBy.lastName}`,
		accessorKey: "referredBy",
		header: "Referred By",
		cell: ({ getValue }) => (
			<span className="font-medium text-center">{getValue() as string}</span>
		),
	},
	{
		accessorKey: "referredOn",
		header: "Referred On",
		cell: ({ getValue }) => (
			<span className="font-medium text-center">
				{format(getValue(), "dd/MM/yyyy HH:mm:ss")}
			</span>
		),
	},
	{
		accessorKey: "cvFile",
		header: () => <div className="text-center">CV</div>,
		cell: ({ row }) => (
			<div className="text-center">
				<Button asChild variant={"outline"}>
					<Link to={`${row.getValue("id")}/cv`} className="border">
						<FileIcon className="h-4 w-4" />
						<span className="hidden lg:block">View</span>
					</Link>
				</Button>
			</div>
		),
	},
];
