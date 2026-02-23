import { Navigate, NavLink, Outlet, useParams } from "react-router-dom";
import { Separator } from "../../ui/separator";
import TravelPlanViewForm from "./forms/TravelPlanViewForm";
import { useTravelPlan } from "../queries/travelPlans.queries";
import { roles, RoleUtil } from "../../../auth/role.util";

const TravelPlanView = () => {
  // getting id of open travel plan
  const { id } = useParams<{ id: string }>();

  // query hook
  const { data, error, isLoading } = useTravelPlan(id!);

  const access = RoleUtil.isEmplpoyee && !data?.assigned;

  if (isLoading) {
    return (
      <div className="p-4 px-8 flex flex-col min-w-96 min-h-96 justify-center items-center">
        Loading
      </div>
    );
  }
  if (error) {
    return (
      <div className="p-4 px-8 flex flex-col min-w-96 min-h-96 justify-center items-center">
        No Data Found...
      </div>
    );
  }

  if (access ?? false) {
    return <Navigate to={"/unauthorized"} />;
  }
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
