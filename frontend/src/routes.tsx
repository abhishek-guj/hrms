import { createBrowserRouter } from "react-router-dom";
import App from "./App";
import MainLayout from "./layouts/MainLayout";
import LoginPage from "./pages/LoginPage";
import TravelPlanPage from "./pages/travel/TravelPlanPage";
import TravelPlanCreate from "./components/travelPlans/TravelplanDashboard/TravelPlanCreate";
import TravelPlanView from "./components/travelPlans/TravelplanDashboard/TravelPlanView";
import TravelPlanDelete from "./components/travelPlans/TravelplanDashboard/TravelPlanDelete";
import TravelPlanEdit from "./components/travelPlans/TravelplanDashboard/TravelPlanEdit";

const router = createBrowserRouter([
	{
		path: "/",
		element: <MainLayout />,
		children: [
			{ index: true, path: "/", element: <App /> },
			{
				path: "/travel/plans",
				element: <TravelPlanPage />,
				children: [
					{ path: "new", element: <TravelPlanCreate /> },
					{ path: ":id", element: <TravelPlanView /> },
					{ path: ":id/edit", element: <TravelPlanEdit /> },
					{ path: ":id/delete", element: <TravelPlanDelete /> },
				],
			},
		],
	},
	{ path: "/login", element: <LoginPage /> },
]);

export default router;
