import { TravelPlanSectionCards } from "../../components/travelPlans/TravelplanDashboard/TravelPlanSectionCards";
import TravelPlansTable from "../../components/travelPlans/TravelplanDashboard/TravelPlansTable";

const TravelPlanPage = () => {
	return (
		<div className="flex flex-1 flex-col gap-4 p-4">
			<div className="h-fit ">
				<TravelPlanSectionCards />
			</div>
			<div className="min-h-[100vh] flex-1 rounded-xl md:min-h-min rounded overflow-hidden p-2">
				{/* USE THIS TABLE BLOCK */}
				{/* https://blocks.so/tables */}
				{/* USE THIS TABLE BLOCK */}
				<TravelPlansTable />
			</div>
		</div>
	);
};

export default TravelPlanPage;
