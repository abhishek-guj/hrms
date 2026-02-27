import { Trash } from "lucide-react";
import { Badge } from "../../../ui/badge";
import { Button } from "../../../ui/button";
import { Field, FieldError, FieldLabel } from "../../../ui/field";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "../../../ui/select";
import { useEffect, useState } from "react";
import type { EmployeeProfileDto } from "../../../job/types/job.types";
import SearchInput from "../../TravelplanDashboard/Shared/SearchInput";
import { RoleUtil } from "../../../../auth/role.util";

const FormMultiSelect = ({
  data,
  name,
  value,
  onValueChange,
  type,
  multiSelectValues,
  multiSelectValuesChange,
  errors,
  displayName,
  removeMySelf = true,
  ...props
}: {
  data?: any,
  name?: any,
  value?: any,
  onValueChange?: any,
  type?: any,
  multiSelectValues?: any,
  multiSelectValuesChange?: any,
  errors?: any,
  displayName?: any,
  removeMySelf?: any,
}) => {
  const handleRemove = (id) => {
    const ids = multiSelectValues.filter((e) => e !== id);
    multiSelectValuesChange(ids);
  };

  const [filterKey, setFilterKey] = useState("");
  const [filterData, setFilterData] = useState<EmployeeProfileDto[]>([]);

  useEffect(() => {
    setFilterData((prev) => {
      if (!prev) return data;

      if (filterKey !== "") {
        prev = data?.filter((p) =>
          `${p?.firstName} ${p?.lastName}`.includes(filterKey),
        );
        return prev;
      }

      return data;
    });
  }, [filterKey]);

  useEffect(() => {
    setFilterData(data);
  }, [data]);

  return (
    <Field orientation="responsive">
      <FieldLabel htmlFor={name}>{displayName}</FieldLabel>
      <Select
        name={name}
        onValueChange={(event) => {
          onValueChange(event);
          if (!multiSelectValues.includes(event)) {
            multiSelectValuesChange([...multiSelectValues, event]);
          }
        }}
        {...props}
      >
        <SelectTrigger id="form-rhf-select-language" className="min-w-[120px]">
          <SelectValue placeholder={"Select"} />
        </SelectTrigger>
        <SelectContent position="popper" className="border border-red-700 h-64">
          <SearchInput globalFilter={filterKey} setGlobalFilter={setFilterKey} />
          {filterData?.map((d) => {
            if (!multiSelectValues?.includes(d.id))
              return (
                <SelectItem key={d?.id} value={`${d?.id}`}>
                  {`${d?.firstName} ${d?.lastName}`}
                </SelectItem>
              );
          })}
        </SelectContent>
      </Select>
      <div className="flex flex-col flex-wrap gap-1 max-h-40 h-40 border justify-start p-1">
        {multiSelectValues?.map((selected) => {
          const result = data?.find((d) => `${d.id}` === `${selected}`);
          return (
            <Badge
              key={selected}
              className="flex gap-2 justify-center items-center text-sm"
            >
              {`${result?.firstName} ${result?.lastName}`}
              {((!removeMySelf && `${RoleUtil.myId}` !== `${result?.id}`) || (removeMySelf)) &&
                <Button
                  variant={"outline"}
                  className="p-0 w-4 h-6"
                  onClick={() => {
                    handleRemove(selected);
                  }}
                >
                  <Trash className="h-3 w-3 m-0 p-0 text-primary" />
                </Button>
              }
            </Badge>
          );
        })}
      </div>
      {errors && <FieldError errors={[errors]} />}
    </Field>
  );
};

export default FormMultiSelect;
