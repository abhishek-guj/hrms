import { createBrowserRouter } from "react-router-dom";
import TravelDocumentsPage from "../tmp/TravelDocumentsPage";
import TravelPlanView from "./components/travelPlans/TravelplanDashboard/TravelPlanView";
import App from "./App";
import TravelExpenses from "./components/travelPlans/TravelplanDashboard/TravelExpenses";
import TravelPlanCreate from "./components/travelPlans/TravelplanDashboard/TravelPlanCreate";
import TravelPlanDelete from "./components/travelPlans/TravelplanDashboard/TravelPlanDelete";
import TravelPlanEdit from "./components/travelPlans/TravelplanDashboard/TravelPlanEdit";
import TravelPlanEmployees from "./components/travelPlans/TravelplanDashboard/TravelPlanEmployees";
import MainLayout from "./layouts/MainLayout";
import LoginPage from "./pages/LoginPage";
import TravelPlanPage from "./pages/travel/TravelPlanPage";
import TravelExpenseView from "./components/travelPlans/TravelExpenses/TravelExpenseView";
import TravelExpenseCreate from "./components/travelPlans/TravelExpenses/TravelExpenseCreate";
import TravelExpenseDelete from "./components/travelPlans/TravelExpenses/TravelExpenseDelete";

const router = createBrowserRouter([
	{
		path: "/",
		element: <MainLayout />,
		children: [
			{ index: true, path: "/", element: <App /> },
			{
				path: "/travel/plans",
				element: <TravelPlanPage />,
				children: [{ path: "new", element: <TravelPlanCreate /> }],
			},
			{
				path: "/travel/plans/:id",
				element: <TravelPlanView />,
				children: [
					{ path: "edit", element: <TravelPlanEdit /> },
					{ path: "delete", element: <TravelPlanDelete /> },
					{ path: "employee", index: true, element: <TravelPlanEmployees /> },
					{ path: "documents", element: <TravelDocumentsPage /> },
					{
						path: "expenses",
						element: <TravelExpenses />,
						children: [
							{
								path: ":expenseId",
								index: true,
								element: <TravelExpenseView />,
							},
							{
								path: "new",
								index: true,
								element: <TravelExpenseCreate />,
							},
							{
								path: ":expenseId/delete",
								index: true,
								element: <TravelExpenseDelete />,
							},
						],
					},
				],
			},
		],
	},
	{ path: "/login", element: <LoginPage /> },
]);

export default router;
