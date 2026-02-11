import { createBrowserRouter } from "react-router-dom";
import App from "./App";
import MainLayout from "./layouts/MainLayout";
import LoginPage from "./pages/LoginPage";
import TravelPlanPage from "./pages/travel/TravelPlanPage";

const router = createBrowserRouter([
	{
		path: "/",
		element: <MainLayout />,
		children: [
			{ index: true, path: "/", element: <App /> },
			{ path: "/travel/plans", element: <TravelPlanPage /> },
		],
	},
	{ path: "/login", element: <LoginPage /> },
]);

export default router;
