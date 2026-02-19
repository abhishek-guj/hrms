import { NavLink, Outlet } from "react-router-dom";
import { Separator } from "../../ui/separator";
import TravelPlanViewForm from "./forms/TravelPlanViewForm";

const TravelPlanView = () => {
	return (
		<main>
			<TravelPlanViewForm />
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
			<Outlet />
		</main>
	);
};

export default TravelPlanView;
