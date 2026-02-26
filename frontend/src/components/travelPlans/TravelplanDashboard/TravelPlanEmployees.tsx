import React, { useEffect, useState } from "react";
import {
  useCreateTravelEmployees,
  useDeleteTravelEmployees,
  useTravelEmployees,
  useUpdateTravelEmployees,
} from "../queries/travelPlans.queries";
import { useParams } from "react-router-dom";
import { Card, CardContent, CardHeader, CardTitle } from "../../ui/card";
import AssignEmloyeeSelect from "./forms/AssignEmloyeeSelect";
import { Badge } from "../../ui/badge";
import { Button } from "../../ui/button";
import { api } from "../../../api/apiClient";
import { Trash, Trash2 } from "lucide-react";
import AssignedEmployeeCard from "../TravelEmployees/AssignedEmployeeCard";
import { RoleUtil } from "../../../auth/role.util";
import TravelEmployeeSelect from "../TravelEmployees/TravelEmployeeSelect";
import { Controller } from "react-hook-form";
import { useEmployeesAll } from "../../shared/services/employee.queries";

const TravelPlanEmployeesOLD = () => {
  // getting id of open travel plan
  const { id } = useParams<{ id: string }>();

  // query hook
  const { data, error, isLoading } = useTravelEmployees(id!); // exclamation to supress undefined error
  const { data: travelEmployees, isLoading: travelEmployeesLoading } =
    useEmployeesAll(id);

  const createTravelEmployees = useCreateTravelEmployees();
  const deleteTravelEmployees = useDeleteTravelEmployees();

  // stats
  const [selected, setSelected] = useState([]);

  const handleRemove = async (data) => {
    console.log(data);

    await deleteTravelEmployees.mutateAsync({ id: id, employeeId: data });
  };

  const handleSubmit = async () => {
    await createTravelEmployees.mutateAsync({ id: id, payload: selected });
    setSelected([]);
  };

  if (error) {
    return <div>{error.message}</div>;
  }
  if (isLoading) {
    return <div>Loading....</div>;
  }
  return (
    <Card className="border-none shadow-none max-h-full h-full w-full px-28">
      <CardHeader>
        <CardTitle>Travel Employees</CardTitle>
      </CardHeader>
      <CardContent className="flex flex-wrap gap-5 justify-center">
        {(RoleUtil.isAdmin || RoleUtil.isHr) && (
          <div className="w-1/2">
            <TravelEmployeeSelect
              name={"hrIds"}
              onValueChange={setSelected}
              multiSelectValues={selected}
              multiSelectValuesChange={setSelected}
              // errors={errors.hrIds}
              displayName={"HRs"}
              data={travelEmployees}
              isLoading={travelEmployeesLoading}
            />

            <Button onClick={handleSubmit}>Add</Button>
          </div>
        )}
        <div className="border p-4 flex flex-col gap-1 grow max-h-max h-full overflow-auto">
          {data?.map((d) => (
            <div
              key={d.id}
              className="flex border-b p-2 justify-between items-center"
            >
              <div>{d.firstName}</div>
              {(RoleUtil.isAdmin || RoleUtil.isHr) && (
                <Button
                  size={"xs"}
                  onClick={() => {
                    handleRemove(d.id);
                  }}
                >
                  <Trash />
                </Button>
              )}
            </div>
          ))}
        </div>
      </CardContent>
    </Card>
  );
};

export default TravelPlanEmployeesOLD;
