import { Button } from "@/components/ui/button";

import { Field, FieldGroup, FieldLabel } from "@/components/ui/field";
import { Input } from "@/components/ui/input";

import { Controller, useForm, type SubmitHandler } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import {
  TravelPlanSchema,
  type TravelPlanSchemaType,
} from "../../../login/schema";
import { DatePickerInput } from "../../../ui/date-picker";
import { FieldError, FieldSet } from "../../../ui/field";
import { Separator } from "../../../ui/separator";
import type { LoginFormType } from "../../loginForm.types";
import { useCreateTravelPlan } from "../../queries/travelPlans.queries";
import TravelTypeSelect from "./TravelTypeSelect";
import { zodResolver } from "@hookform/resolvers/zod";
import { FieldInput } from "../../../game/forms/BookGameSlotForm";

const TravelPlanCreateForm = () => {
  // navigate
  const navigate = useNavigate();
  // navigate

  // query hooks
  const create = useCreateTravelPlan();
  // query hooks

  // react form
  const {
    register,
    handleSubmit,
    formState: { errors },
    control,
  } = useForm<TravelPlanSchemaType>({
    mode: "onBlur",
    defaultValues: {},
    resolver: zodResolver(TravelPlanSchema),
  });
  // react form

  // handlers
  const onSubmit: SubmitHandler<TravelPlanSchemaType> = async (data) => {
    const resData = await create.mutateAsync({ payload: data });
    navigate("/travel/plans");
  };
  // handlers

  //
  return (
    <form onSubmit={handleSubmit(onSubmit)}>
      <FieldGroup>
        {/*  */}
        <FieldSet className="grid grid-cols-2">
          <FieldInput
            displayName={"Purpose"}
            name="purpose"
            errors={errors.purpose}
            register={register}
            type="text"
            placeholder="For this Business..."
          />

          <Field>
            <FieldLabel htmlFor="travelTypeId">Travel Type</FieldLabel>
            <Controller
              name="travelTypeId"
              control={control}
              render={({ field, fieldState }) => (
                <TravelTypeSelect
                  value={field.value}
                  onValueChange={field.onChange}
                />
              )}
            />
            {errors.travelTypeId && (
              <FieldError errors={[errors.travelTypeId]} />
            )}
          </Field>
        </FieldSet>

        <Separator />
        {/* dates */}

        <FieldSet className="grid grid-cols-2">
          <Field>
            <FieldLabel htmlFor="startDate">Start Date</FieldLabel>
            <DatePickerInput
              id="startDate"
              type="date"
              placeholder="Travel Start Date"
              {...register("startDate")}
              control={control}
            />
            {errors.startDate && <FieldError errors={[errors.startDate]} />}
          </Field>
          <Field>
            <FieldLabel htmlFor="endDate">End Date</FieldLabel>
            <DatePickerInput
              id="endDate"
              type="text"
              placeholder="Travel End Date"
              control={control}
              {...register("endDate")}
            />
            {errors.endDate && <FieldError errors={[errors.endDate]} />}
          </Field>
        </FieldSet>

        {/* place */}
        <Separator />

        <FieldSet className="grid grid-cols-3">
          <Field>
            <FieldLabel htmlFor="destinationCity">City</FieldLabel>
            <Input
              id="destinationCity"
              type="text"
              placeholder="Mumbai...."
              {...register("destinationCity")}
            />
            {errors.destinationCity && (
              <FieldError errors={[errors.destinationCity]} />
            )}
          </Field>
          <Field>
            <FieldLabel htmlFor="destinationState">State</FieldLabel>
            <Input
              id="destinationState"
              type="text"
              placeholder="Maharashtra...."
              {...register("destinationState")}
            />
            {errors.destinationState && (
              <FieldError errors={[errors.destinationState]} />
            )}
          </Field>
          <Field>
            <FieldLabel htmlFor="destinationCountry">Country</FieldLabel>
            {/* <TravelTypeSelect name="travelTypeId" control={control} /> */}
            <Input
              id="destinationCountry"
              type="text"
              placeholder="India"
              {...register("destinationCountry")}
            />
            {errors.destinationCountry && (
              <FieldError errors={[errors.destinationCountry]} />
            )}
          </Field>
        </FieldSet>

        {/* place */}
        <Separator />

        <FieldSet className="grid grid-cols-2">
          <Field>
            <FieldLabel htmlFor="lastDateOfExpenseSubmission">
              Expense Submit Last Date
            </FieldLabel>
            <DatePickerInput
              id="lastDateOfExpenseSubmission"
              type="date"
              placeholder="Travel Start Date"
              control={control}
              {...register("lastDateOfExpenseSubmission")}
            />
            {errors.lastDateOfExpenseSubmission && (
              <FieldError errors={[errors.lastDateOfExpenseSubmission]} />
            )}
          </Field>
          <Field>
            <FieldLabel htmlFor="maxAmountPerDay">
              Max Amount Per Day
            </FieldLabel>
            <Input
              id="maxAmountPerDay"
              type="number"
              placeholder="1000"
              {...register("maxAmountPerDay")}
            />
            {errors.maxAmountPerDay && (
              <FieldError errors={[errors.maxAmountPerDay]} />
            )}
          </Field>
        </FieldSet>
        {/* </FieldGroup> */}

        <Field>
          <Button type="submit">Save</Button>
        </Field>
      </FieldGroup>
    </form>
  );
};

export default TravelPlanCreateForm;
