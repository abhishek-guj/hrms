import { useState } from "react";
import {
	type SortingState,
	getCoreRowModel,
	getFilteredRowModel,
	getPaginationRowModel,
	getSortedRowModel,
	getGroupedRowModel,
	useReactTable,
} from "@tanstack/react-table";

import { Table } from "../../ui/table";

import DataTableOptions from "../TravelplanDashboard/Shared/DataTableOptions";
import DataTableHeader from "../TravelplanDashboard/Shared/DataTableHeader";
import DataTableBody from "../TravelplanDashboard/Shared/DataTableBody";
import DataTableFooter from "../TravelplanDashboard/Shared/DataTableFooter";

import { TravelPlanTableColumns } from "../TravelplanDashboard/TravelPlansTabelStructure";
import {
	useTravelExpenses,
	useTravelPlans,
} from "../queries/travelPlans.queries";
import type { DataTabelItem } from "../types/TravelPlan.types";
import { TravelExpenseTableColumns } from "./TravelExpenseTabelStructure";
import { Button } from "../../ui/button";
import { Link } from "react-router-dom";
import { Plus } from "lucide-react";

const TravelExpensesTable = () => {
	// states
	const [sorting, setSorting] = useState<SortingState>([]);
	const [rowSelection, setRowSelection] = useState({});
	const [globalFilter, setGlobalFilter] = useState("");
	// states

	// const { data, error, isLoading } = useTravelExpenses(id!); // exclamation to supress undefined error
	const { data, error, isLoading } = useTravelExpenses(1); // exclamation to supress undefined error

	// defining react table
	const [grouping, setGrouping] = useState<string[]>(["startDate"]);

	const table = useReactTable({
		data: data ? data : [],
		columns: TravelExpenseTableColumns,
		onGroupingChange: setGrouping,
		getCoreRowModel: getCoreRowModel(),
		getGroupedRowModel: getGroupedRowModel(),
		getPaginationRowModel: getPaginationRowModel(),
		getSortedRowModel: getSortedRowModel(),
		getFilteredRowModel: getFilteredRowModel(),
		onSortingChange: setSorting,
		onRowSelectionChange: setRowSelection,
		onGlobalFilterChange: setGlobalFilter,
		globalFilterFn: "includesString",
		state: {
			sorting,
			rowSelection,
			globalFilter,
			grouping,
		},
		initialState: {
			pagination: { pageSize: 5 },
		},
	});
	// defining react table

	// getting footer details, currently not used
	const pageCount = table.getPageCount();
	const currentPage = table.getState().pagination.pageIndex + 1;
	// getting footer details, currently not used

	return (
		<div className="w-full space-y-4">
			<div className="flex justify-between gap-8 px-4">
				<DataTableOptions
					table={table}
					globalFilter={globalFilter}
					setGlobalFilter={setGlobalFilter}
				/>
				<Button asChild>
					<Link to={"new"}>
						New <Plus />
					</Link>
				</Button>
			</div>

			<div className="flex w-full rounded-4xl p-1 px-2 pb-4 border ">
				<Table className="w-full">
					<DataTableHeader table={table} />
					<DataTableBody table={table} columns={TravelExpenseTableColumns} />
				</Table>
			</div>

			<DataTableFooter
				table={table}
				pageCount={pageCount}
				currentPage={currentPage}
			/>
		</div>
	);
};

export default TravelExpensesTable;
