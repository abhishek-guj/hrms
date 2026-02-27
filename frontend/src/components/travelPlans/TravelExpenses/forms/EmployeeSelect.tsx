import React, { useEffect, useState } from "react";
import MultipleSelector from "../../../ui/multi";
import FormMultiSelect from "./FormMultiSelect";
import { useExpenseTypes } from "../../queries/travelPlans.queries";
import { useEmployeesAll } from "../../../shared/services/employee.queries";
import SearchInput from "../../TravelplanDashboard/Shared/SearchInput";

const EmployeeSelect = ({
  name,
  value,
  onValueChange,
  type,
  multiSelectValues,
  multiSelectValuesChange,
  errors,
  displayName,
  ...props
}: {

  name?:any,
  value?:any,
  onValueChange?:any,
  type?:any,
  multiSelectValues?:any,
  multiSelectValuesChange?:any,
  errors?:any,
  displayName?:any,
}) => {
  const { data, isLoading } = useEmployeesAll();

  if (isLoading) {
    <div className="flex flex-row justify-center items-center min-w-30 border">
      Loading employees
    </div>;
  }
  return (
    <FormMultiSelect
      data={data}
      name={name}
      value={value || []}
      onValueChange={onValueChange}
      multiSelectValues={multiSelectValues}
      multiSelectValuesChange={multiSelectValuesChange}
      errors={errors}
      displayName={displayName}
      type={null}
    />
  );
};

export default EmployeeSelect;
