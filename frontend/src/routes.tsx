import { createBrowserRouter } from "react-router-dom";
import TravelDocumentsPage from "./components/travelPlans/TravelDocuments/TravelDocumentsPage";
import App from "./App";
import JobShare from "./components/job/JobShare";
import JobView from "./components/job/JobView";
import PublicLayout from "./components/layout/PublicLayout";
import TravelExpenseCreate from "./components/travelPlans/TravelExpenses/TravelExpenseCreate";
import TravelExpenseDelete from "./components/travelPlans/TravelExpenses/TravelExpenseDelete";
import TravelExpenseView from "./components/travelPlans/TravelExpenses/TravelExpenseView";
import TravelExpenses from "./components/travelPlans/TravelplanDashboard/TravelExpenses";
import TravelPlanCreate from "./components/travelPlans/TravelplanDashboard/TravelPlanCreate";
import TravelPlanDelete from "./components/travelPlans/TravelplanDashboard/TravelPlanDelete";
import TravelPlanEdit from "./components/travelPlans/TravelplanDashboard/TravelPlanEdit";
import TravelPlanEmployees from "./components/travelPlans/TravelplanDashboard/TravelPlanEmployees";
import TravelPlanView from "./components/travelPlans/TravelplanDashboard/TravelPlanView";
import MainLayout from "./layouts/MainLayout";
import ErrorPage from "./pages/ErrorPage";
import JobPublicPage from "./pages/job/JobPublicPage";
import JobsPage from "./pages/job/JobsPage";
import LoginPage from "./pages/LoginPage";
import OrgChartPage from "./pages/org/OrgChartPage";
import TravelPlanPage from "./pages/travel/TravelPlanPage";
import JobReferral from "./components/job/JobReferral";
import TravelDocumentCreate from "./components/travelPlans/TravelDocuments/TravelDocumentCreate";
import TravelDocumentView from "./components/travelPlans/TravelDocuments/TravelDocumentView";
import TravelDocumentDelete from "./components/travelPlans/TravelDocuments/TravelDocumentDelete";

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
					{
						path: "documents",
						element: <TravelDocumentsPage />,
						children: [
							{ path: "new", element: <TravelDocumentCreate /> },
							{ path: ":documentId", element: <TravelDocumentView /> },
							{ path: ":documentId/delete", element: <TravelDocumentDelete /> },
						],
					},
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
			{ path: "/org-chart", element: <OrgChartPage /> },
			{
				path: "/jobs",
				element: <JobsPage />,
				children: [
					{ path: "new", element: <JobView /> },
					{ path: ":jobId", element: <JobView /> },
					{ path: ":jobId/share", element: <JobShare /> },
					{ path: ":jobId/refer", element: <JobReferral /> },
				],
			},
		],
	},
	{
		path: "/",
		element: <PublicLayout />,
		children: [
			{ path: "/login", element: <LoginPage /> },
			{
				path: "/jobs/public/:jobId",
				element: <JobPublicPage />,
			},
		],
	},
	{ path: "*", element: <ErrorPage /> },
]);

export default router;
