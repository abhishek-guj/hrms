import { useForm, type UseFormRegister } from "react-hook-form";
import { useNavigate, useParams } from "react-router-dom";
import type { LoginFormType } from "../../login/loginForm.types";
import type { EmployeeExpenseSchemaType } from "../../login/schema";
import { useCreateTravelExpense } from "../../travelPlans/queries/travelPlans.queries";
import { Field, FieldError, FieldLabel } from "../../ui/field";
import { Input } from "../../ui/input";
import { useSlotDetails } from "../queries/game.queries";

const BookGameSlotForm = () => {
  // navigate
  const navigate = useNavigate();
  // navigate

  const { id, slotId } = useParams<{ slotId: string }>();

  // query hooks
  const createTravelExpense = useCreateTravelExpense();
  // query hooks

  console.log("slotDetails");
  // const { data: slotDetails } = useSlotDetails(slotId!);
  // console.log("slotDetails", slotDetails);

  // react form
  const {
    register,
    handleSubmit,
    formState: { errors },
    control,
  } = useForm<EmployeeExpenseSchemaType>({
    mode: "onBlur",
    defaultValues: { travelPlanId: id },
  });
  // react form

  // handlers
  const onSubmit: SubmitHandler<LoginFormType> = async (data) => {
    const resData = await createTravelExpense.mutateAsync({ payload: data });
  };
  // handlers
  return <form onSubmit={handleSubmit(onSubmit)}></form>;
};

export default BookGameSlotForm;

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
