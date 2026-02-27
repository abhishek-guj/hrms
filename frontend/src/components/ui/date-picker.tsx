"use client";

import { Button } from "@/components/ui/button";
import { Calendar } from "@/components/ui/calendar";
import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from "@/components/ui/popover";
import { format } from "date-fns";
import { ChevronDownIcon } from "lucide-react";
import { Controller } from "react-hook-form";

export function DatePickerInput({ name, control, ...props }) {

  return (
    <Controller
      name={name}
      control={control}
      rules={{ required: `Please select a date.` }}
      render={({ field }) => (
        <Popover>
          <PopoverTrigger asChild>
            <Button
              variant="outline"
              data-empty={!field.value}
              className="data-[empty=true]:text-muted-foreground w-[212px] justify-between text-left font-normal"
            >
              {field.value ? (
                format(field.value, "dd/MM/yyyy")
              ) : (
                <span>Pick a date</span>
              )}
              <ChevronDownIcon />
            </Button>
          </PopoverTrigger>
          <PopoverContent className="w-auto p-0" align="start">
            <Calendar
              mode="single"
              selected={field.value ?? new Date()}
              onSelect={field.onChange}
              noonSafe
              today={field.value ?? new Date()}
              {...props}
            />
          </PopoverContent>
        </Popover>
      )}
    />
  );
}
// />
