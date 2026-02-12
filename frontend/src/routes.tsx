import { createBrowserRouter } from "react-router-dom";
import App from "./App";
import MainLayout from "./layouts/MainLayout";
import LoginPage from "./pages/LoginPage";
import TravelPlanPage from "./pages/travel/TravelPlanPage";
import TravelPlanCreate from "./components/travelPlans/TravelplanDashboard/TravelPlanCreate";

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
					{ path: ":id", element: <TravelPlanCreate /> },
					{ path: ":id/edit", element: <TravelPlanCreate /> },
					{ path: ":id/delete", element: <TravelPlanCreate /> },
				],
			},
		],
	},
	{ path: "/login", element: <LoginPage /> },
]);

export default router;
