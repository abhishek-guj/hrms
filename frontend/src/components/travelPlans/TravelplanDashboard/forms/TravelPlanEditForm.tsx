import { Button } from "@/components/ui/button";

import { Field, FieldGroup, FieldLabel } from "@/components/ui/field";
import { Input } from "@/components/ui/input";

import { zodResolver } from "@hookform/resolvers/zod";
import { Controller, useForm, type SubmitHandler } from "react-hook-form";
import { useNavigate, useParams } from "react-router-dom";
import {
  TravelPlanUpdateSchema,
  type TravelPlanUpdateSchemaType,
} from "../../../login/schema";
import { DatePickerInput } from "../../../ui/date-picker";
import { FieldError, FieldSet } from "../../../ui/field";
import { Separator } from "../../../ui/separator";
import {
  useTravelPlan,
  useUpdateTravelPlan,
} from "../../queries/travelPlans.queries";
import TravelTypeSelect from "./TravelTypeSelect";
const TravelPlanEditForm = () => {
  // getting id of open travel plan
  const { id } = useParams<{ id: string }>();

  // query hook
  const { data, error, isLoading } = useTravelPlan(id!);

  // navigate
  const navigate = useNavigate();
  // navigate

  // query hooks
  const updateTravelPlan = useUpdateTravelPlan();
  // query hooks

  const travelType = data?.travelType;
  const startDate = data?.startDate;
  const endDate = data?.endDate;
  const lastDateOfExpenseSubmission = data?.lastDateOfExpenseSubmission;
  const newData = {
    ...data,
    id: `${data?.id}`,
    travelTypeId: `${travelType?.id}`,
    startDate: new Date(startDate!),
    endDate: new Date(endDate!),
    lastDateOfExpenseSubmission: new Date(lastDateOfExpenseSubmission!),
  };

  // react form
  const {
    register,
    handleSubmit,
    formState: { errors },
    control,
  } = useForm<TravelPlanUpdateSchemaType>({
    mode: "onBlur",
    defaultValues: { ...newData },
    resolver: zodResolver(TravelPlanUpdateSchema),
  });

  // handlers
  const onSubmit: SubmitHandler<TravelPlanUpdateSchemaType> = async (data) => {
    const { id, ...reqData } = data;

    await updateTravelPlan.mutateAsync({
      id: id,
      payload: reqData,
    });

    navigate("/travel/plans");
  };
  // handlers



  if (isLoading) {
    return (
      <div className="p-4 px-8 flex flex-col min-w-96 min-h-96 justify-center items-center">
        Loading
      </div>
    );
  }
  if (error) {
    return (
      <div className="p-4 px-8 flex flex-col min-w-96 min-h-96 justify-center items-center">
        No Data Found...
      </div>
    );
  }
  //
  return (
    <form onSubmit={handleSubmit(onSubmit)}>
      <FieldGroup>
        {/*  */}
        <FieldSet className="grid grid-cols-2">
          <Field>
            <FieldLabel htmlFor="email">Email</FieldLabel>
            <Input
              id="purpose"
              type="text"
              placeholder="To finallize deal....."
              {...register("purpose")}
            />
            {errors.purpose && <FieldError errors={[errors.purpose]} />}
          </Field>
          <Field>
            <FieldLabel htmlFor="travelTypeId">Travel Type</FieldLabel>
            <Controller
              name="travelTypeId"
              control={control}
              defaultValue={control._defaultValues.travelTypeId}
              render={({ field, fieldState }) => (
                <TravelTypeSelect
                  value={field.value}
                  onValueChange={field.onChange}
                />
              )}
            />
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

export default TravelPlanEditForm;
