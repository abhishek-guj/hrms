import React, { useEffect, useState } from "react";
import { Card, CardContent, CardHeader, CardTitle } from "../../ui/card";
import AssignEmloyeeSelect from "./forms/AssignEmloyeeSelect";
import AssignedEmployeeCard from "../TravelEmployees/AssignedEmployeeCard";
import { Button } from "../../ui/button";
import {
  useTravelEmployees,
  useTravelExpenses,
  useUpdateTravelEmployees,
} from "../queries/travelPlans.queries";
import { Outlet, useOutletContext, useParams } from "react-router-dom";
import { Trash2 } from "lucide-react";
import TravelExpensesTable from "../TravelExpenses/TravelExpensesTable";

const TravelExpenses = () => {
  // getting id of open travel plan
  const { id } = useParams<{ id: string }>();

  // query hook
  console.log("id", id);
  const { data, error, isLoading } = useTravelExpenses(id!); // exclamation to supress undefined error
  console.log("data", data);
  const { data: travel } = useOutletContext();

  // states

  if (error) {
    return <div>{error.message}</div>;
  }
  if (isLoading) {
    return <div>Loading....</div>;
  }
  return (
    <>
      <Card className="border-none shadow-none max-h-full h-full w-full px-2 sm:px-28">
        <CardHeader>
          <CardTitle>Travel Expenses</CardTitle>
        </CardHeader>
        <CardContent className="flex flex-col gap-1">
          <TravelExpensesTable />
        </CardContent>
      </Card>
      <Outlet context={{ data: data, travel: travel }} />
    </>
  );
};

export default TravelExpenses;
