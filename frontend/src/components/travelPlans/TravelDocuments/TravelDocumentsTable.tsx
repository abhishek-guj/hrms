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

import { Table } from "../../ui/table";

import { Plus } from "lucide-react";
import { Link, useOutletContext, useParams } from "react-router-dom";
import { Button } from "../../ui/button";
import DataTableBody from "../TravelplanDashboard/Shared/DataTableBody";
import DataTableFooter from "../TravelplanDashboard/Shared/DataTableFooter";
import DataTableHeader from "../TravelplanDashboard/Shared/DataTableHeader";
import DataTableOptions from "../TravelplanDashboard/Shared/DataTableOptions";
import { TravelPlanTableColumns } from "../TravelplanDashboard/TravelPlansTabelStructure";
import { useTravelPlans } from "../queries/travelPlans.queries";
import { useTravelDocuments } from "../queries/travelDocuments.queries";
import { TravelDocumentTableColumns } from "./TravelDocumentsTableStructure";
import { RoleUtil } from "../../../auth/role.util";

const TravelDocumentsTable = () => {
	// states
	const [sorting, setSorting] = useState<SortingState>([]);
	const [rowSelection, setRowSelection] = useState({});
	const [globalFilter, setGlobalFilter] = useState("");
	// states

	const { id } = useParams<{ id: string }>();

	const { data: travelData } = useOutletContext();

	const { data, isLoading, error } = useTravelDocuments(id!);
	console.log(data);

	// defining react table

	const table = useReactTable({
		data: data ? data : [],
		columns: TravelDocumentTableColumns,
		getCoreRowModel: getCoreRowModel(),
		getGroupedRowModel: getGroupedRowModel(),
		getPaginationRowModel: getPaginationRowModel(),
		getSortedRowModel: getSortedRowModel(),
		getFilteredRowModel: getFilteredRowModel(),
		onSortingChange: setSorting,
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
				{(RoleUtil.isAdmin ||
					RoleUtil.isHr ||
					RoleUtil.isAssigned(travelData?.assigned)) && (
					<Button asChild>
						<Link to={"new"}>
							New <Plus />
						</Link>
					</Button>
				)}
			</div>

			<div className="flex w-full rounded-4xl p-1 px-2 pb-4 border ">
				<Table className="w-full">
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

export default TravelDocumentsTable;
