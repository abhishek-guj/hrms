import { Button } from "@/components/ui/button";

import { Field, FieldGroup, FieldLabel } from "@/components/ui/field";
import { Input } from "@/components/ui/input";

import { useForm, type SubmitHandler } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import { type TravelPlanSchemaType } from "../../../login/schema";
import { DatePickerInput } from "../../../ui/date-picker";
import { FieldError, FieldSet } from "../../../ui/field";
import { Separator } from "../../../ui/separator";
import type { LoginFormType } from "../../loginForm.types";
import { useCreateTravelPlan } from "../../queries/travelPlans.queries";

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
		// resolver: zodResolver(TravelPlanSchema),
		// defaultValues: JSON.parse(sessionStorage.getItem("prod")),
	});
	// react form

	// handlers
	const onSubmit: SubmitHandler<LoginFormType> = async (data) => {
		alert();

		const resData = await create.mutateAsync({ payload: data });
		console.log("data update", resData);
		navigate("/travel/plans");
	};
	// handlers

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
						{console.log(errors)}
						{errors.purpose && <FieldError errors={[errors.purpose]} />}
					</Field>
					<Field>
						<FieldLabel htmlFor="password">Password</FieldLabel>
						{/* <TravelTypeSelect name="travelTypeId" control={control} /> */}
						<Input
							id="travelTypeId"
							type="number"
							placeholder="Travel type id"
							{...register("travelTypeId")}
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
						{errors.purpose && <FieldError errors={[errors.purpose]} />}
						{/*  */}

						{/* <Controller
							name="startDate" // The name of the field in your form data
							control={control} // The control object from useForm
							rules={{ required: "Please select a date." }} // Validation rules
							render={({ field }) => (
								<Calendar
									mode="single" // Use "single" mode for a single date selection
									selected={field.value} // Pass the form's value to the date picker
									onSelect={field.onChange} // Update the form value when a date is selected
								/>
							)}
						/> */}
						{/*  */}
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
						{errors.purpose && <FieldError errors={[errors.purpose]} />}
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
