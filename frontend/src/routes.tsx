import { createBrowserRouter, Navigate } from "react-router-dom";
import BookGameSlot from "./components/game/BookGameSlot";
import CVView from "./components/job/CvView";
import JobReferral from "./components/job/JobReferral";
import JobShare from "./components/job/JobShare";
import JobView from "./components/job/JobView";
import ReferralsPage from "./components/job/ReferralsPage";
import ReferralStatusChange from "./components/job/ReferralStatusChange";
import PublicLayout from "./components/layout/PublicLayout";
import TravelDocumentCreate from "./components/travelPlans/TravelDocuments/TravelDocumentCreate";
import TravelDocumentDelete from "./components/travelPlans/TravelDocuments/TravelDocumentDelete";
import TravelDocumentsPage from "./components/travelPlans/TravelDocuments/TravelDocumentsPage";
import TravelDocumentView from "./components/travelPlans/TravelDocuments/TravelDocumentView";
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
import GamesPage from "./pages/game/GamesPage";
import MyBookingPage from "./pages/game/MyBookingPage";
import JobPublicPage from "./pages/job/JobPublicPage";
import JobsPage from "./pages/job/JobsPage";
import LoginPage from "./pages/LoginPage";
import OrgChartPage from "./pages/org/OrgChartPage";
import TravelPlanPage from "./pages/travel/TravelPlanPage";
// ── Achievements ──────────────────────────────────────────────────────────────
import AchievementsPage from "./pages/achievements/AchievementsPage";

import ProtectedRoute from "./auth/ProtectedRoute";
import { roles } from "./auth/role.util";
import GameConfigPage from "./pages/game/GameConfigPage";
import AdminPanelPage from "./pages/admin/AdminPanelPage";

const { admin, hr, manager, employee } = roles;

const router = createBrowserRouter([
  {
    path: "/",
    element: (
      <ProtectedRoute>
        <MainLayout />
      </ProtectedRoute>
    ),
    children: [
      { index: true, path: "/", element: <Navigate to={"/travel/plans"} /> },
      {
        path: "/travel/plans",
        element: <TravelPlanPage />,
        children: [
          {
            path: "new",
            element: (
              <ProtectedRoute allowedRoles={[admin, hr]}>
                <TravelPlanCreate />
              </ProtectedRoute>
            ),
          },
        ],
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
          // { path: "new", element: <JobView /> },
          { path: ":jobId", element: <JobView /> },
          { path: ":jobId/share", element: <JobShare /> },
          { path: ":jobId/refer", element: <JobReferral /> },
        ],
      },
      {
        path: "/referrals",
        element: <ReferralsPage />,
        children: [
          { path: ":referId", element: <ReferralStatusChange /> },
          { path: ":referId/cv", element: <CVView /> },
        ],
      },
      {
        path: "/games",
        element: <GamesPage />,
        children: [{ path: ":slotId", element: <BookGameSlot /> }],
      },
      {
        path: "/game-config",
        element: (
          <ProtectedRoute allowedRoles={[admin, hr]}>
            <GameConfigPage />
          </ProtectedRoute>
        ),
      },
      {
        path: "/my-slots",
        element: <MyBookingPage />,
      },
      {
        path: "/admin-panel",
        element: <AdminPanelPage />
      },

      // ── Achievements & Celebrations ──────────────────────────────────────
      {
        path: "/achievements",
        element: <AchievementsPage />,
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
