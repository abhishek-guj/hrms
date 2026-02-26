import React from "react";
import { Controller } from "react-hook-form";
import {
  Field,
  FieldContent,
  FieldDescription,
  FieldError,
  FieldLabel,
} from "../../../ui/field";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "../../../ui/select";
import type { ExpenseTypeDto } from "../../types/TravelPlan.types";

const FormSelect = ({ data, name, value, onValueChange, type }) => {
  return (
    <Field orientation="responsive">
      <Select name={name} value={value} onValueChange={onValueChange}>
        <SelectTrigger id="form-rhf-select-language" className="min-w-[120px]">
          <SelectValue placeholder="Select" />
        </SelectTrigger>
        <SelectContent position="item-aligned">
          {data?.map((d) => (
            <FormSelectItem type={type} d={d} />
          ))}
        </SelectContent>
      </Select>
    </Field>
  );
};

const FormSelectItem = ({ type, d }) => {
  let val;

  if (type === "job") {
    val = d?.jobTitle;
  } else if (type === "travel") {
    val = `${d?.firstName} ${d?.lastName}`;
  } else {
    val = d?.name;
  }

  return (
    <SelectItem key={d?.id} value={`${d?.id}`}>
      {val}
    </SelectItem>
  );
};

export default FormSelect;
