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
  removeMySelf = true,
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
  removeMySelf?: any,
}) => {



  // renders 

  if (isLoading) {
    return <div className="flex flex-row justify-center items-center min-w-30 border">
      Loading employees
    </div>;
  }

  // render
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
      removeMySelf={removeMySelf}
    />
  );
};

export default TravelEmployeeSelect;
