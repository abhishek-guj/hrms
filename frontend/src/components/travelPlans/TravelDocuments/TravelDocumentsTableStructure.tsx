import type { ColumnDef } from "@tanstack/react-table";
import type { TravelDocument } from "../types/TravelPlan.types";
import DataTableBadge from "../TravelplanDashboard/Shared/DataTableBadge";
import { Button } from "../../ui/button";
import { Link } from "react-router-dom";
import { Eye, Trash } from "lucide-react";
import { format } from "date-fns";
import { RoleUtil } from "../../../auth/role.util";

export const TravelDocumentTableColumns: ColumnDef<TravelDocument>[] = [
	{
		accessorKey: "id",
		header: "Doc Id",
		cell: ({ row }) => (
			<span className="font-medium">{row.getValue("id")}</span>
		),
	},
	{
		accessorKey: "ownerType",
		header: "Owner",

		cell: ({ getValue }) => (
			<span className="font-medium">{getValue() as string}</span>
		),
	},
	{
		accessorKey: "documentTypeName",
		header: "Document Type",
		cell: ({ getValue }) => <div>{getValue() as string}</div>,
	},
	{
		accessorKey: "uploadDate",
		header: "Upload Date",
		cell: ({ getValue }) => <div>{format(getValue(), "dd/MM/yyyy HH:mm")}</div>,
	},
	{
		accessorKey: "uploadedByName",
		header: "By",
		cell: ({ getValue }) => (
			<span className="font-medium">{getValue() as string}</span>
		),
	},
	{
		accessorKey: "uploadedForName",
		header: "For",

		cell: ({ getValue }) => (
			<span className="font-medium">{getValue() as string}</span>
		),
	},
	{
		accessorFn: (row) => row.uploadedForId,
		id: "actions",
		header: () => <div className="text-center">Actions</div>,
		cell: ({ row, getValue }) => (
			<div className="text-center space-x-1">
				<Button asChild variant={"outline"}>
					<Link to={`${row.getValue("id")}`} className="border">
						<Eye className="h-4 w-4" />
						<span className="hidden lg:block">View</span>
					</Link>
				</Button>
				{(RoleUtil.isThisManager(getValue()) ||
					RoleUtil.isAdmin ||
					RoleUtil.isHr ||
					RoleUtil.isEmplpoyee) && (
					<Button asChild variant={"outline"}>
						<Link to={`${row.getValue("id")}/delete`} className="border">
							<Trash className="h-4 w-4" />
							<span className="hidden lg:block">Delete</span>
						</Link>
					</Button>
				)}
			</div>
		),
	},
];
