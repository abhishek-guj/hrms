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

	// query hook
	const { data, error, isLoading } = useTravelPlan(id!);

	// handlers

	if (error) {
		return <div>{error.message}</div>;
	}
	if (isLoading) {
		return <div>Loading....</div>;
	}

	//
	return (
		<div className="p-4 px-8 flex flex-col">
			<div className="pb-4">
				<div className="font-black text-xl">View Travel Plan</div>
			</div>
			<div>
				<div className="grid grid-cols-2">
					<div>
						<div className="text-black/50 text-sm my-1">Purpose</div>
						<div className="p-1">{data?.purpose}</div>
					</div>
					<div>
						<div className="text-black/50 text-sm my-1">Travel Type</div>
						<div className="p-1">{data?.travelType.name}</div>
					</div>
				</div>

				<Separator />
				{/* dates */}

				<div className="grid grid-cols-2">
					<div>
						<div className="text-black/50 text-sm my-1">Start Date</div>
						<div className="p-1">{data?.startDate}</div>
					</div>
					<div>
						<div className="text-black/50 text-sm my-1">End Date</div>
						<div className="p-1">{data?.endDate}</div>
					</div>
				</div>

				{/* place */}
				<Separator />

				<div className="grid grid-cols-3">
					<div>
						<div className="text-black/50 text-sm my-1">City</div>
						<div className="p-1">{data?.destinationCity}</div>
					</div>
					<div>
						<div className="text-black/50 text-sm my-1">State</div>
						<div className="p-1">{data?.destinationState}</div>
					</div>
					<div>
						<div className="text-black/50 text-sm my-1">Country</div>
						<div className="p-1">{data?.destinationCountry}</div>
					</div>
				</div>

				{/* place */}
				<Separator />

				<div className="grid grid-cols-2">
					<div>
						<div className="text-black/50 text-sm my-1">
							Expense Submit Last Date
						</div>
						<div className="p-1">{data?.lastDateOfExpenseSubmission}</div>
					</div>
					<div>
						<div className="text-black/50 text-sm my-1">Max Amount Per Day</div>
						<div className="p-1">{data?.maxAmountPerDay}</div>
					</div>
				</div>
				{/* </FieldGroup> */}
			</div>
		</div>
	);
};

export default TravelPlanViewForm;
