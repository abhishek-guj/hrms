import React from "react";

const TravelPlansTable = () => {
	return (
		<div>
			<DataTableOptions
				table={table}
				globalFilter={globalFilter}
				setGlobalFilter={setGlobalFilter}
			/>

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

export default TravelPlansTable;
