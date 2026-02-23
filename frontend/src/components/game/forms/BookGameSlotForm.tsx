import { useEffect, useState } from "react";
import { type UseFormRegister } from "react-hook-form";
import { useNavigate, useParams } from "react-router-dom";
import { RoleUtil } from "../../../auth/role.util";
import AssignedEmployeeCard from "../../travelPlans/TravelEmployees/AssignedEmployeeCard";
import AssignEmloyeeSelect from "../../travelPlans/TravelplanDashboard/forms/AssignEmloyeeSelect";
import { Button } from "../../ui/button";
import { Field, FieldError, FieldLabel } from "../../ui/field";
import { Input } from "../../ui/input";
import { Separator } from "../../ui/separator";
import GameSlotDetails from "../GameSlotDetails";
import { useBookSlot, useSlotDetails } from "../queries/game.queries";

const BookGameSlotForm = () => {
  // navigate
  const navigate = useNavigate();
  // navigate

  const [selected, setSelected] = useState([Number(RoleUtil.myId)]);
  const [countError, setCountError] = useState(false);
  const { id, slotId } = useParams<{ slotId: string }>();

  console.log("init", selected);

  // query hooks
  const bookSlot = useBookSlot();
  // query hooks

  const { data: slotDetails, isLoading, error } = useSlotDetails(slotId!);
  console.log(slotDetails);

  useEffect(() => {
    const count = selected.length;
    console.log(count);
    if (!slotDetails?.slotSizes?.includes(count)) {
      setCountError(true);
    } else {
      setCountError(false);
    }
  }, [slotDetails, selected]);

  // handlers
  const onSubmit = async (e) => {
    console.log("submit", selected);
    e.preventDefault();
    // alert();
    const resData = await bookSlot.mutateAsync({
      id: slotId,
      playerIds: selected,
    });
  };

  const handleChange = (value) => {
    const id = Number(value);
    if (!selected.includes(id)) {
      setSelected((prev) => [...prev, id]);
    }
  };

  const handleRemove = (value) => {
    console.log(value);
    setSelected(selected.filter((select) => select != value));
  };
  // handlers

  // derieved
  const disabled = countError;
  const alreadyBooked = slotDetails?.inGroup;
  const cantBook = slotDetails?.canBook;
  const isInQueue = slotDetails?.inQueue;

  const employees = [
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

  const myEmployee = employees.filter(
    (emp) => Number(emp.id) === Number(RoleUtil.myId),
  )[0];

  console.log("my ", myEmployee);

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
    <div className="px-8 py-2 flex flex-col gap-6">
      <GameSlotDetails
        slotDetails={slotDetails?.gameSlot}
        slotSizes={slotDetails?.slotSizes}
      />
      <Separator />
      <form onSubmit={onSubmit} className="flex flex-col p-2 gap-2">
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
              <FieldLabel>Select Players</FieldLabel>
              <AssignEmloyeeSelect onValueChange={handleChange} />
              <div className="py-4 flex flex-wrap gap-1.5">
                {countError && (
                  <div className="text-sm text-red-500 items-center">
                    Selected players not equal to any slot size...!
                  </div>
                )}
                <AssignedEmployeeCard
                  handleRemove={handleRemove}
                  emp={myEmployee}
                  showDesignation={false}
                  disableRemove={true}
                />
                {employees
                  ?.filter((emp) => selected.includes(Number(emp.id)))
                  .filter((emp) => Number(emp.id) !== Number(myEmployee.id))
                  .map((emp) => {
                    return (
                      <AssignedEmployeeCard
                        key={emp.id}
                        handleRemove={handleRemove}
                        emp={emp}
                        showDesignation={false}
                      />
                    );
                  })}
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
      </form>
    </div>
  );
};

export default BookGameSlotForm;

// trying out dynamic and reusable components
export function FieldInput({
  register,
  name,
  displayName,
  errors,
  ...props
}: Readonly<{
  register: UseFormRegister<any>;
  name: string;
  displayName: string;
  errors: { message?: string } | undefined;
}>) {
  return (
    <Field>
      <FieldLabel htmlFor={name}>{displayName}</FieldLabel>
      <Input id={name} {...register(name)} {...props} />
      {errors && <FieldError errors={[errors]} />}
    </Field>
  );
}
