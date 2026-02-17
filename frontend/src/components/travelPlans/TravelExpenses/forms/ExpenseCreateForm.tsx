import { useForm, type SubmitHandler } from "react-hook-form";
import { useNavigate, useParams } from "react-router-dom";
import {
	type EmployeeExpenseSchemaType,
	type TravelPlanSchemaType,
} from "../../../login/schema";
import type { LoginFormType } from "../../loginForm.types";
import {
	useCreateTravelExpense,
	useCreateTravelPlan,
	useExpenseTypes,
} from "../../queries/travelPlans.queries";
import { Field, FieldGroup, FieldLabel } from "@/components/ui/field";
import { Input } from "@/components/ui/input";
import { Controller } from "react-hook-form";
import { FieldError, FieldSet } from "../../../ui/field";
import { Separator } from "../../../ui/separator";
import TravelTypeSelect from "../../TravelplanDashboard/forms/TravelTypeSelect";
import FormSelect from "./FormSelect";
import { DatePickerInput } from "../../../ui/date-picker";
import { Button } from "../../../ui/button";

const ExpenseCreateForm = () => {
	// navigate
	const navigate = useNavigate();
	// navigate

	const { id } = useParams<{ id: string }>();

	// query hooks
	const createTravelExpense = useCreateTravelExpense();
	// query hooks

	//
	const { data: expenseData } = useExpenseTypes();

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
		alert();

		console.log("before save expense", data);
		const resData = await createTravelExpense.mutateAsync({ payload: data });
		console.log("save expense", resData);
		// navigate("/travel/plans");
	};
	// handlers
	return (
		<form onSubmit={handleSubmit(onSubmit)}>
			<FieldGroup>
				{/*  */}
				<FieldSet className="grid grid-cols-2">
					<Field>
						<FieldLabel htmlFor="expenseTypeId">Expense Type</FieldLabel>
						<Controller
							name="expenseTypeId"
							control={control}
							render={({ field, fieldState }) => (
								<FormSelect
									data={expenseData}
									name={"expenseTypeId"}
									value={field.value}
									onValueChange={field.onChange}
								/>
							)}
						/>
						{errors.expenseTypeId && (
							<FieldError errors={[errors.expenseTypeId]} />
						)}
					</Field>
					<Field>
						<FieldLabel htmlFor="expenseAmount">Expense Amount</FieldLabel>
						<Input
							id="expenseAmount"
							type="number"
							placeholder="100..."
							{...register("expenseAmount")}
						/>
						{errors.expenseAmount && (
							<FieldError errors={[errors.expenseAmount]} />
						)}
					</Field>
				</FieldSet>
				<Separator />

				<FieldSet>
					<Field>
						<FieldLabel htmlFor="expenseDate">Expense Date</FieldLabel>
						<DatePickerInput
							id="expenseDate"
							type="text"
							placeholder="Expense Date"
							control={control}
							{...register("expenseDate")}
						/>
						{errors.expenseDate && <FieldError errors={[errors.expenseDate]} />}
					</Field>
				</FieldSet>
				<Separator />

				{/* files */}
				<FieldSet>
					<Field>
						<FieldLabel htmlFor="files">Expense Date</FieldLabel>
						<Input id="files" type="file" multiple {...register("files")} />
						{errors.files && <FieldError errors={[errors.files]} />}
					</Field>
				</FieldSet>

				<Separator />

				<FieldSet className="grid grid-cols-2">
					<Field>
						<Button type="submit" variant={"outline"}>
							Save
						</Button>
					</Field>
					<Field>
						<Button type="submit">Submit</Button>
					</Field>
				</FieldSet>
			</FieldGroup>
		</form>
	);
};

export default ExpenseCreateForm;
