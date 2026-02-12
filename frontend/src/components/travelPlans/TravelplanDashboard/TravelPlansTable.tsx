import { useState } from "react";
import {
	type SortingState,
	getCoreRowModel,
	getFilteredRowModel,
	getPaginationRowModel,
	getSortedRowModel,
	useReactTable,
} from "@tanstack/react-table";

import { Table } from "../../ui/table";

import DataTableOptions from "./Shared/DataTableOptions";
import DataTableHeader from "./Shared/DataTableHeader";
import DataTableBody from "./Shared/DataTableBody";
import DataTableFooter from "./Shared/DataTableFooter";

import {
	TravelPlanTableColumns,
	TravelPlanTableData,
} from "./TravelPlansTabelStructure";

const TravelPlansTable = () => {
	// states
	const [sorting, setSorting] = useState<SortingState>([]);
	const [rowSelection, setRowSelection] = useState({});
	const [globalFilter, setGlobalFilter] = useState("");
	// states

	// defining react table
	const table = useReactTable({
		data: TravelPlanTableData,
		columns: TravelPlanTableColumns,
		getCoreRowModel: getCoreRowModel(),
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
			<DataTableOptions
				table={table}
				globalFilter={globalFilter}
				setGlobalFilter={setGlobalFilter}
			/>

			<div className="rounded-lg border p-0.5">
				<Table>
					<DataTableHeader table={table} />
					<DataTableBody table={table} columns={TravelPlanTableColumns} />
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

export default TravelPlansTable;
