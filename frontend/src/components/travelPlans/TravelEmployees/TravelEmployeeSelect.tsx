import { useParams } from "react-router-dom";
import {
  useEmployeesAll,
  useEmployeesTravelPlan,
} from "../../shared/services/employee.queries";
import FormMultiSelect from "../TravelExpenses/forms/FormMultiSelect";

const TravelEmployeeSelect = ({
  name,
  onValueChange,
  type,
  multiSelectValues,
  multiSelectValuesChange,
  errors,
  displayName,
  isLoading,
  data,
  ...props
}: {
  data?: any;
  name?: any;
  onValueChange?: any;
  type?: any;
  multiSelectValues?: any;
  multiSelectValuesChange?: any;
  errors?: any;
  displayName?: any;
  isLoading?: any;
}) => {
  if (isLoading) {
    <div className="flex flex-row justify-center items-center min-w-30 border">
      Loading employees
    </div>;
  }
  return (
    <FormMultiSelect
      data={data}
      name={name}
      onValueChange={onValueChange}
      multiSelectValues={multiSelectValues}
      multiSelectValuesChange={multiSelectValuesChange}
      errors={errors}
      displayName={displayName}
      type={null}
    />
  );
};

export default TravelEmployeeSelect;
