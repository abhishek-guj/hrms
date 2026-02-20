import {
	type SortingState,
	getCoreRowModel,
	getFilteredRowModel,
	getGroupedRowModel,
	getPaginationRowModel,
	getSortedRowModel,
	useReactTable,
} from "@tanstack/react-table";
import { useState } from "react";

import { Table } from "../ui/table";

import DataTableBody from "../travelPlans/TravelplanDashboard/Shared/DataTableBody";
import DataTableFooter from "../travelPlans/TravelplanDashboard/Shared/DataTableFooter";
import DataTableHeader from "../travelPlans/TravelplanDashboard/Shared/DataTableHeader";
import DataTableOptions from "../travelPlans/TravelplanDashboard/Shared/DataTableOptions";

import { Plus } from "lucide-react";
import { Link } from "react-router-dom";
import { Button } from "../ui/button";
import { JobsTableColumns } from "./JobsTableStructure";
import { useAllReferrals, useJobsAll } from "./queries/job.queries";
import { ReferralsTableColumns } from "./ReferralsTableStructure";

const ReferralsViewTable = ({ data }) => {
	// states
	const [sorting, setSorting] = useState<SortingState>([]);
	const [rowSelection, setRowSelection] = useState({});
	const [globalFilter, setGlobalFilter] = useState("");
	// states

	// defining react table

	const table = useReactTable({
		data: data ? data : [],
		columns: ReferralsTableColumns,
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
			</div>

			<div className="flex w-full rounded-4xl p-1 px-2 pb-4 border overflow-hidden ">
				<Table className="w-full">
					<DataTableHeader table={table} />
					<DataTableBody table={table} columns={JobsTableColumns} />
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

export default ReferralsViewTable;
