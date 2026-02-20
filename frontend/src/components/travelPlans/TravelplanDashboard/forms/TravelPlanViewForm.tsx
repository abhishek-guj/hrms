import { Input } from "@/components/ui/input";

import { useForm, type SubmitHandler } from "react-hook-form";
import { useParams } from "react-router-dom";
import { type TravelPlanSchemaType } from "../../../login/schema";
import { Card, CardContent, CardHeader, CardTitle } from "../../../ui/card";
import { DatePickerInput } from "../../../ui/date-picker";
import { Separator } from "../../../ui/separator";
import type { LoginFormType } from "../../loginForm.types";
import { useTravelPlan } from "../../queries/travelPlans.queries";
import { showError } from "../../../ui/toast";

const TravelPlanViewForm = ({ data, error, isLoading }) => {
	// handlers

	if (error) {
		showError(error.message);
		return (
			<div className="w-full h-4/5 grid items-center justify-center">
				No Data at the Moment...
			</div>
		);
	}
	if (isLoading) {
		return (
			<div className="w-full h-4/5 grid items-center justify-center">
				Loading....
			</div>
		);
	}

	//
	return (
		<div className="p-4 flex flex-col">
			<div className="pb-4 px-1 sm:px-28 ">
				<h1 className="font-bold text-2xl">View Travel Plan</h1>
			</div>
			<Separator />
			<div className="px-1 sm:px-28">
				<div className="grid grid-cols-1 sm:grid-cols-2 ">
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

				<div className="grid grid-cols-1 sm:grid-cols-2">
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

				<div className="grid grid-cols-1 sm:grid-cols-3">
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

				<div className="grid grid-cols-1 sm:grid-cols-2">
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
