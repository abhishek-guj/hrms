import { useNavigate, useParams } from "react-router-dom";
import {
	useCreateTravelExpense,
	useExpenseTypes,
} from "../../travelPlans/queries/travelPlans.queries";
import type { JobReferralSchemaType } from "../../login/schema";
import { Controller, useForm, type SubmitHandler } from "react-hook-form";
import {
	Field,
	FieldError,
	FieldGroup,
	FieldLabel,
	FieldSet,
} from "../../ui/field";
import FormSelect from "../../travelPlans/TravelExpenses/forms/FormSelect";
import { Input } from "../../ui/input";
import { DatePickerInput } from "../../ui/date-picker";
import { Separator } from "../../ui/separator";
import { Button } from "../../ui/button";
import { useJobsAll, useReferJob } from "../queries/job.queries";
import { useState } from "react";

const JobReferralForm = () => {
	const [loading, setLoading] = useState(false);
	// navigate
	const navigate = useNavigate();
	// navigate

	const { jobId } = useParams<{ jobId: string }>();

	// query hooks
	const referJob = useReferJob();
	// query hooks

	// react form
	const {
		register,
		handleSubmit,
		formState: { errors },
		reset,
		control,
	} = useForm<JobReferralSchemaType>({
		mode: "onBlur",
	});
	// react form

	// handlers
	const onSubmit: SubmitHandler<JobReferralSchemaType> = async (data) => {
		setLoading(true);
		const resData = await referJob.mutateAsync({ jobId: jobId, payload: data });
		setLoading(false);
		navigate(`/jobs`);
	};
	// handlers

	return (
		<form
			onSubmit={handleSubmit(onSubmit)}
			className="flex flex-col gap-2 py-2"
		>
			<FieldGroup>
				<FieldSet className="grid grid-cols-2 hidden">
					<Field>
						<FieldLabel htmlFor="expenseTypeId">Job</FieldLabel>
						<Input
							id="jobId"
							type="text"
							{...register("jobId")}
							disabled
							value={jobId}
							className="hidden"
						/>
					</Field>
				</FieldSet>

				<FieldSet className="grid grid-cols-2">
					<Field>
						<FieldLabel htmlFor="firstName">First Name</FieldLabel>
						<Input
							id="firstName"
							type="text"
							placeholder="Abc"
							{...register("firstName")}
						/>
						{errors.firstName && <FieldError errors={[errors.firstName]} />}
					</Field>
					<Field>
						<FieldLabel htmlFor="lastName">Last Name</FieldLabel>
						<Input
							id="lastName"
							type="text"
							placeholder="Xyz"
							{...register("lastName")}
						/>
						{errors.lastName && <FieldError errors={[errors.lastName]} />}
					</Field>
				</FieldSet>

				{/* contact */}
				<Separator />
				<FieldSet className="grid grid-cols-2">
					<Field>
						<FieldLabel htmlFor="contactNumber">Contact Number</FieldLabel>
						<Input
							id="contactNumber"
							type="tel"
							placeholder="1234567890"
							{...register("contactNumber")}
						/>
						{errors.contactNumber && (
							<FieldError errors={[errors.contactNumber]} />
						)}
					</Field>
					<Field>
						<FieldLabel htmlFor="email">Email</FieldLabel>
						<Input
							id="email"
							type="email"
							placeholder="abhi@exp.com"
							{...register("email")}
						/>
						{errors.email && <FieldError errors={[errors.email]} />}
					</Field>
				</FieldSet>
				<Separator />

				{/* files */}
				<FieldSet>
					<Field>
						<FieldLabel htmlFor="note">Note</FieldLabel>
						<Input
							id="note"
							type="text"
							placeholder="Remarks...."
							{...register("note")}
						/>
						{errors.note && <FieldError errors={[errors.note]} />}
					</Field>
					<Field>
						<FieldLabel htmlFor="cvFile">CV File</FieldLabel>
						<Input id="cvFile" type="file" multiple {...register("cvFile")} />
						{errors.cvFile && <FieldError errors={[errors.cvFile]} />}
					</Field>
				</FieldSet>

				<FieldSet className="grid grid-cols-1">
					<Field>
						<Button type="submit" disabled={loading}>
							Save
						</Button>
					</Field>
				</FieldSet>
			</FieldGroup>
		</form>
	);
};

export default JobReferralForm;
