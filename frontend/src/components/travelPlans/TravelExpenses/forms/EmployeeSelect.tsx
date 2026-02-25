import React from "react";
import MultipleSelector from "../../../ui/multi";
import FormMultiSelect from "./FormMultiSelect";
import { useExpenseTypes } from "../../queries/travelPlans.queries";
import { useEmployeesAll } from "../../../shared/services/employee.queries";

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
}) => {
  const { data } = useEmployeesAll();
  console.log(value,multiSelectValues)
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
