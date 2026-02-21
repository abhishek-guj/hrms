import React, { useEffect, useState } from "react";
import {
  useTravelEmployees,
  useUpdateTravelEmployees,
} from "../queries/travelPlans.queries";
import { useParams } from "react-router-dom";
import { Card, CardContent, CardHeader, CardTitle } from "../../ui/card";
import AssignEmloyeeSelect from "./forms/AssignEmloyeeSelect";
import { Badge } from "../../ui/badge";
import { Button } from "../../ui/button";
import { api } from "../../../api/apiClient";
import { Trash2 } from "lucide-react";
import AssignedEmployeeCard from "../TravelEmployees/AssignedEmployeeCard";
import { RoleUtil } from "../../../auth/role.util";

const TravelPlanEmployeesOLD = () => {
  // getting id of open travel plan
  const { id } = useParams<{ id: string }>();

  // query hook
  const { data, error, isLoading } = useTravelEmployees(id!); // exclamation to supress undefined error

  const updateTravelEmployees = useUpdateTravelEmployees();

  // stats
  const [selected, setSelected] = useState([]);

  console.log(data);

  const employees = [
    { id: "1", name: "ad" },
    { id: "2", name: "man" },
    { id: "3", name: "man2" },
    { id: "4", name: "emp" },
    { id: "5", name: "employee" },
    { id: "6", name: "employee3" },
    { id: "7", name: "employee4" },
    { id: "8", name: "employee05" },
    { id: "9", name: "employee06" },
    { id: "10", name: "employee07" },
    { id: "11", name: "employee08" },
    { id: "12", name: "employee09" },
  ];

  useEffect(() => {
    if (!data) return;
    const ids = data?.map((d) => d.id);
    setSelected((prev) => [...prev, ...ids]);
  }, [data]);

  // handlers
  const handleChange = (value) => {
    const id = Number(value);
    if (!selected.includes(id)) {
      setSelected((prev) => [...prev, id]);
    }
  };

  const handleRemove = (id) => {
    setSelected((prev) => prev.filter((pid) => pid !== Number(id)));
  };

  const handleSubmit = () => {
    updateTravelEmployees.mutateAsync({ id: id, payload: selected });
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
      <CardContent>
        {(RoleUtil.isAdmin || RoleUtil.isHr) && (
          <AssignEmloyeeSelect onValueChange={handleChange} />
        )}
        <div className="py-4 flex flex-wrap gap-1.5">
          {employees
            ?.filter((emp) => selected.includes(Number(emp.id)))
            .map((emp) => {
              return (
                <AssignedEmployeeCard
                  key={emp.id}
                  handleRemove={handleRemove}
                  emp={emp}
                />
              );
            })}
        </div>
        {(RoleUtil.isAdmin || RoleUtil.isHr) && (
          <Button onClick={handleSubmit}>Update</Button>
        )}
      </CardContent>
    </Card>
  );
};

export default TravelPlanEmployeesOLD;
