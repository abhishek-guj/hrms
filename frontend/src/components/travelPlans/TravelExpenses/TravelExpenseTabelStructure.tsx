import type { ColumnDef } from "@tanstack/react-table";
import { Check, Cross, Eye, Trash, X } from "lucide-react";
import { Link } from "react-router-dom";
import { Button } from "../../ui/button";
import DataTableBadge from "../TravelplanDashboard/Shared/DataTableBadge";
import type { TravelExpenseDto } from "../types/TravelPlan.types";
import type { DataTableStatus } from "../../shared/shared.types";
import StatusUpdate from "./StatusUpdate";
import { RoleUtil } from "../../../auth/role.util";

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
  approved: {
    label: "Approved",
    className:
      "bg-emerald-500/15 text-emerald-700 dark:bg-emerald-500/10 dark:text-emerald-400",
  },
  rejected: {
    label: "Rejected",
    className:
      "bg-rose-500/15 text-rose-700 dark:bg-rose-500/10 dark:text-rose-400",
  },
  upcoming: {
    label: "Upcoming",
    className:
      "bg-amber-500/15 text-amber-700 dark:bg-amber-500/10 dark:text-amber-400",
  },
  ongoing: {
    label: "Ongoing",
    className:
      "bg-blue-500/15 text-blue-700 dark:bg-blue-500/10 dark:text-blue-400",
  },
  review: {
    label: "Review",
    className:
      "bg-amber-500/15 text-amber-700 dark:bg-amber-500/10 dark:text-amber-400",
  },
  interview: {
    label: "Interview",
    className:
      "bg-blue-500/15 text-blue-700 dark:bg-blue-500/10 dark:text-blue-400",
  },
  hold: {
    label: "Hold",
    className:
      "bg-amber-500/15 text-amber-700 dark:bg-amber-500/10 dark:text-amber-400",
  },
  accepted: {
    label: "Accepted",
    className:
      "bg-emerald-500/15 text-emerald-700 dark:bg-emerald-500/10 dark:text-emerald-400",
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
    cell: ({ getValue }) => <DataTableBadge status={getValue()} />,
    // cell: ({ getValue }) => <div>{getValue() as string}</div>,
  },
  {
    accessorFn: (row) => `${row.submittedBy.managerId}`,
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

        {!RoleUtil.isThisManager(getValue()) &&
          row.getValue("status") === "Pending" && (
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

if (RoleUtil.isAdmin || RoleUtil.isHr)
  TravelExpenseTableColumns.push({
    id: "changeStatus",
    accessorFn: (row) => row.status,
    accessorKey: "changeStatus",
    header: () => <div className="text-center">Change Status</div>,
    cell: ({ row, getValue }) => (
      <StatusUpdate id={row.getValue("id")} status={getValue()} />
    ),
  });
