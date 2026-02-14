import { Input } from "@/components/ui/input";

import { useForm, type SubmitHandler } from "react-hook-form";
import { useParams } from "react-router-dom";
import { type TravelPlanSchemaType } from "../../../login/schema";
import { Card, CardContent, CardHeader, CardTitle } from "../../../ui/card";
import { DatePickerInput } from "../../../ui/date-picker";
import { Separator } from "../../../ui/separator";
import type { LoginFormType } from "../../loginForm.types";
import { useTravelPlan } from "../../queries/travelPlans.queries";

const TravelPlanViewForm = () => {
	// getting id of open travel plan
	const { id } = useParams<{ id: string }>();

	console.log(id);
	// query hook
	const { data, error, isLoading } = useTravelPlan(id!);

	console.log(data);

	// handlers

	if (error) {
		return <div>{error.message}</div>;
	}
	if (isLoading) {
		return <div>Loading....</div>;
	}

	//
	return (
		<Card className="border-none">
			<CardHeader>
				<CardTitle>View Travel Plan</CardTitle>
			</CardHeader>
			<CardContent>
				{/*  */}
				<div className="grid grid-cols-2 py-3">
					<div>
						<div className="text-black/50 text-sm my-1">Purpose</div>
						<div className="p-1 pb-2">{data?.purpose}</div>
					</div>
					<div>
						<div className="text-black/50 text-sm my-1">Travel Type</div>
						<div className="p-1 pb-2">{data?.travelType.name}</div>
					</div>
				</div>

				<Separator />
				{/* dates */}

				<div className="grid grid-cols-2 py-3">
					<div>
						<div className="text-black/50 text-sm my-1">Start Date</div>
						<div className="p-1 pb-2">{data?.startDate}</div>
					</div>
					<div>
						<div className="text-black/50 text-sm my-1">End Date</div>
						<div className="p-1 pb-2">{data?.endDate}</div>
					</div>
				</div>

				{/* place */}
				<Separator />

				<div className="grid grid-cols-3 py-3">
					<div>
						<div className="text-black/50 text-sm my-1">City</div>
						<div className="p-1 pb-2">{data?.destinationCity}</div>
					</div>
					<div>
						<div className="text-black/50 text-sm my-1">State</div>
						<div className="p-1 pb-2">{data?.destinationState}</div>
					</div>
					<div>
						<div className="text-black/50 text-sm my-1">Country</div>
						<div className="p-1 pb-2">{data?.destinationCountry}</div>
					</div>
				</div>

				{/* place */}
				<Separator />

				<div className="grid grid-cols-2 py-3">
					<div>
						<div className="text-black/50 text-sm my-1">
							Expense Submit Last Date
						</div>
						<div className="p-1 pb-2">{data?.lastDateOfExpenseSubmission}</div>
					</div>
					<div>
						<div className="text-black/50 text-sm my-1">Max Amount Per Day</div>
						<div className="p-1 pb-2">{data?.maxAmountPerDay}</div>
					</div>
				</div>
				{/* </FieldGroup> */}
			</CardContent>
		</Card>
	);
};

export default TravelPlanViewForm;
