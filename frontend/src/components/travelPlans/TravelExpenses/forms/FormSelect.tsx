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
          {data?.map((d) => {
            return (
              <SelectItem key={d?.id} value={`${d?.id}`}>
                {type === "job" ? d?.jobTitle : d?.name}
              </SelectItem>
            );
          })}
        </SelectContent>
      </Select>
    </Field>
  );
};

export default FormSelect;
