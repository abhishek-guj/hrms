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

const AssignEmloyeeSelect = ({ value, onValueChange }) => {
  const data = [
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

  return (
    <Field orientation="responsive">
      <Select name={"travelTypeID"} value={value} onValueChange={onValueChange}>
        <SelectTrigger id="form-rhf-select-language" className="min-w-[120px]">
          <SelectValue placeholder="Select" />
        </SelectTrigger>
        <SelectContent position="item-aligned">
          {data.map((d) => {
            return (
              <SelectItem value={d.id} key={d.id}>
                {d.name}
              </SelectItem>
            );
          })}
        </SelectContent>
      </Select>
    </Field>
  );
};

export default AssignEmloyeeSelect;
