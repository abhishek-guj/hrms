import { useEffect, useState } from "react";
import { type UseFormRegister } from "react-hook-form";
import { useParams } from "react-router-dom";
import { RoleUtil } from "../../../auth/role.util";
import { useEmployeesAll } from "../../shared/services/employee.queries";
import TravelEmployeeSelect from "../../travelPlans/TravelEmployees/TravelEmployeeSelect";
import { Button } from "../../ui/button";
import { Field, FieldError, FieldLabel } from "../../ui/field";
import { Input } from "../../ui/input";
import { Separator } from "../../ui/separator";
import GameSlotDetails from "../GameSlotDetails";
import { useBookSlot, useSlotDetails } from "../queries/game.queries";

const BookGameSlotForm = () => {

  const [selected, setSelected] = useState([Number(RoleUtil.myId)]);
  const [countError, setCountError] = useState(false);
  const { slotId } = useParams<{ slotId: string }>();


  // query hooks
  const bookSlot = useBookSlot();
  // query hooks

  const { data: slotDetails, isLoading, error } = useSlotDetails(slotId!);

  useEffect(() => {
    const count = selected.length;
    console.log(count);
    if (slotDetails?.slotSizes?.includes(count)) {
      setCountError(false);
    } else {
      setCountError(true);
    }
  }, [slotDetails, selected]);

  // handlers
  const onSubmit = async (e) => {
    console.log("submit", selected);
    e.preventDefault();
    await bookSlot.mutateAsync({
      id: slotId!,
      playerIds: selected,
    });
  };
  // handlers

  // derieved
  const disabled = countError;
  const alreadyBooked = slotDetails?.inGroup;
  const cantBook = slotDetails?.canBook;
  const isInQueue = slotDetails?.inQueue;



  // fetching all employees from backend
  const { data: employees, isLoading: employeesLoading } = useEmployeesAll();


  // renders
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

  return (

    <div className="p-5">
      <GameSlotDetails
        slotDetails={slotDetails?.gameSlot}
        slotSizes={slotDetails?.slotSizes}
      />
      <Separator />
      <form onSubmit={onSubmit} className="flex flex-col p-2 gap-2 ">
        <Field>
          {/* <FieldInput
          name="playerId"
          displayName="Emploayee Id"
          register={register}
          /> */}
          {alreadyBooked && (
            <div className="flex justify-center">
              You are already Booked for this slot
            </div>
          )}
          {isInQueue && (
            <div className="flex justify-center">
              You are already in Queue for this slot
            </div>
          )}
          {!alreadyBooked && (
            <>
              <TravelEmployeeSelect
                name={"Players"}
                onValueChange={setSelected}
                multiSelectValues={selected}
                multiSelectValuesChange={setSelected}
                displayName={"Select Player"}
                data={employees}
                isLoading={employeesLoading}
                removeMySelf={false}
              />
              <div className="py-4 flex flex-wrap gap-1.5">
                {countError && (
                  <div className="text-sm text-red-500 items-center">
                    Selected players not equal to any slot size...!
                  </div>
                )}
              </div>
            </>
          )}
        </Field>

        <Button
          variant={"default"}
          disabled={disabled || alreadyBooked || !cantBook}
          onClick={onSubmit}
        >
          Request Slot
        </Button>
      </form></div>
  );
};

export default BookGameSlotForm;

// trying out dynamic and reusable components
export function FieldInput({
  register,
  name,
  displayName,
  errors,
  type,
  ...props
}: Readonly<{
  register: UseFormRegister<any>;
  name: string;
  displayName: string;
  errors: { message?: string } | undefined;
  type?: string;
}>) {
  // types

  if (type === "file") {
    return (
      <Field>
        <FieldLabel htmlFor={name}>{displayName}</FieldLabel>
        <Input id={name} type="file" multiple={false} {...register(name)} />
        {errors && <FieldError errors={[errors]} />}
      </Field>
    );
  }

  if (type === "time") {
    return (
      <Field>
        <FieldLabel htmlFor={name}>{displayName}</FieldLabel>
        <Input
          type="time"
          id={name}
          step="60"
          defaultValue="00:00"
          required
          pattern="^([01]\d|2[0-3]):([0-5]\d)$"
          className="bg-background appearance-none [&::-webkit-calendar-picker-indicator]:hidden [&::-webkit-calendar-picker-indicator]:appearance-none"
          {...register(name)}
          {...props}
        />
        {errors && <FieldError errors={[errors]} />}
      </Field>
    );
  }

  return (
    <Field>
      <FieldLabel htmlFor={name}>{displayName}</FieldLabel>
      <Input id={name} type={type} {...register(name)} {...props} />
      {errors && <FieldError errors={[errors]} />}
    </Field>
  );
}
