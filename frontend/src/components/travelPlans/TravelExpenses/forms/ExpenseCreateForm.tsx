import { useForm, type SubmitHandler } from "react-hook-form";
import {
	useNavigate,
	useOutlet,
	useOutletContext,
	useParams,
} from "react-router-dom";
import {
	EmployeeExpenseSchema,
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
import { zodResolver } from "@hookform/resolvers/zod";
import type { TravelExpenseRequestDto, TravelPlanDto } from "../../types/TravelPlan.types";
import { formatDate } from "../../../../lib/utils";

const ExpenseCreateForm = () => {
	// navigate
	const navigate = useNavigate();
	// navigate

	const { id } = useParams<{ id: string }>();

	// query hooks
	const createTravelExpense = useCreateTravelExpense();
	// query hooks
	const { travel } = useOutletContext<{ travel: TravelPlanDto }>();
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
		resolver: zodResolver(EmployeeExpenseSchema),
	});
	console.log(errors)
	// react form

	// handlers
	const onSubmit: SubmitHandler<EmployeeExpenseSchemaType> = async (data) => {
		const formattedData: TravelExpenseRequestDto = { ...data, expenseDate: formatDate(data?.expenseDate) }
		const resData = await createTravelExpense.mutateAsync({ payload: formattedData });
		navigate(`/travel/plans/${id}/expenses`)
	};
	// handlers


	// renders
	if (travel && new Date(travel.lastDateOfExpenseSubmission) <= new Date()) {
		return (
			<div className="w-96 h-96 flex flex-col justify-center items-center">
				Cant add after submission date
			</div>
		);
	}
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
									type={"expenseTypeId"}
								/>
							)}
						/>
						{errors.expenseTypeId && (
							<FieldError errors={[{ message: "need to select expense type" }]} />
						)}
					</Field>
					<Field>
						<FieldLabel htmlFor="expenseAmount">Expense aaAmount</FieldLabel>
						<Input
							id="expenseAmount"
							type="number"
							placeholder="100..."
							{...register("expenseAmount", {
								max: {
									value: travel?.maxAmountPerDay,
									message: "cant be more than max amount of day",
								},
							})}
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
						<FieldLabel htmlFor="files">Expense Proof</FieldLabel>
						<Input id="files" type="file" multiple {...register("files")} />
						{errors.files && <FieldError errors={[errors.files]} />}
					</Field>
				</FieldSet>

				<Separator />

				<FieldSet className="grid grid-cols-1">
					<Field>
						<Button type="submit">Save</Button>
					</Field>
				</FieldSet>
			</FieldGroup>
		</form>
	);
};

export default ExpenseCreateForm;
