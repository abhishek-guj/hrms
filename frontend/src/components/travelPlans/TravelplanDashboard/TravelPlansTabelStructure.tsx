import type { ColumnDef } from "@tanstack/react-table";
import { Eye, Pencil, Trash2 } from "lucide-react";
import { Link } from "react-router-dom";
import { Button } from "../../ui/button";
import type { DataTabelItem } from "../types/TravelPlan.types";
import DataTableBadge from "./Shared/DataTableBadge";
import { RoleUtil } from "../../../auth/role.util";

// date checking for status of travel
const checkOngoing = (startDate, endDate) => {
  const today = new Date();

  const startDateNew = new Date(new Date(startDate).setHours(0, 0, 0));
  const endDateNew = new Date(new Date(endDate).setHours(23, 59, 59));

  if (today <= endDateNew && today >= startDateNew) {
    return "ongoing";
  } else if (today > endDateNew) {
    return "completed";
  } else {
    return "upcoming";
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
      <div className="text-center space-x-1">
        <Button asChild variant={"outline"}>
          <Link to={`${row.getValue("id")}`} className="border">
            <Eye className="h-4 w-4" />
            <span className="hidden lg:block">View</span>
          </Link>
        </Button>
        {(RoleUtil.isAdmin || RoleUtil.isHr) && (
          <>
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
          </>
        )}
      </div>
    ),
  },
];
