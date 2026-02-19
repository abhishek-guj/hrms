import React, { useState } from "react";
import { TravelPlanTableColumns } from "../TravelplanDashboard/TravelPlansTabelStructure";
import {
	getCoreRowModel,
	getFilteredRowModel,
	getPaginationRowModel,
	getSortedRowModel,
	useReactTable,
	type SortingState,
} from "@tanstack/react-table";
import { useTravelPlans } from "../queries/travelPlans.queries";
import TravelDocumentsTable from "./TravelDocumentsTable";
import { Outlet } from "react-router-dom";
import { Card, CardContent, CardHeader, CardTitle } from "../../ui/card";

const TravelDocumentsPage = () => {
	return (
		<>
			<Card className="border-none shadow-none max-h-full h-full w-full px-1 sm:px-28">
				<CardHeader>
					<CardTitle>Travel Documents</CardTitle>
				</CardHeader>
				<CardContent className="flex flex-col gap-1">
					<TravelDocumentsTable />
				</CardContent>
			</Card>
			<Outlet />
		</>
	);
};

export default TravelDocumentsPage;
