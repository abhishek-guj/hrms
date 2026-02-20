import { NavLink, Outlet, useParams } from "react-router-dom";
import { Separator } from "../../ui/separator";
import TravelPlanViewForm from "./forms/TravelPlanViewForm";
import { useTravelPlan } from "../queries/travelPlans.queries";

const TravelPlanView = () => {
	// getting id of open travel plan
	const { id } = useParams<{ id: string }>();

	// query hook
	const { data, error, isLoading } = useTravelPlan(id!);
	return (
		<main>
			<TravelPlanViewForm data={data} error={error} isLoading={isLoading} />
			<Separator />
			<div className="p-2 flex justify-center gap-4">
				<NavLink
					to={"employee"}
					className="p-2 border bg-secondary rounded-xl text-sm sm:text-base"
				>
					Employees
				</NavLink>
				<NavLink
					to={"documents"}
					className="p-2 border bg-secondary rounded-xl text-sm sm:text-base"
				>
					Documents
				</NavLink>
				<NavLink
					to={"expenses"}
					className="p-2 border bg-secondary rounded-xl text-sm sm:text-base"
				>
					Expenses
				</NavLink>
			</div>
			<Separator />
			<Outlet context={{ data: data }} />
		</main>
	);
};

export default TravelPlanView;
