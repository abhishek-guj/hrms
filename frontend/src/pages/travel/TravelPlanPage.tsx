import { Outlet } from "react-router-dom";
import { TravelPlanSectionCards } from "../../components/travelPlans/TravelplanDashboard/TravelPlanSectionCards";
import TravelPlansTable from "../../components/travelPlans/TravelplanDashboard/TravelPlansTable";

const TravelPlanPage = () => {
  return (
    <div className="flex flex-1 flex-col gap-8 p-12">
      <div className="flex justify-start items-center text-2xl font-semibold w-full">
        Travel Plans
      </div>
      <div className="min-h-[100vh] flex-1 rounded-xl md:min-h-min rounded overflow-hidden">
        <TravelPlansTable />
      </div>
      <Outlet />
    </div>
  );
};

export default TravelPlanPage;
