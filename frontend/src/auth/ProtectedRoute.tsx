import { Navigate, Outlet, useLocation } from "react-router-dom";

function getAuthState() {
  const token = localStorage.getItem("token");
  const role = localStorage.getItem("role");
  const employeeId = localStorage.getItem("employeeId");
  return { token, role, employeeId };
}

export default function ProtectedRoute({
  allowedRoles,
  children,
  redirectTo = "/login",
}) {
  const { token, role } = getAuthState();
  const location = useLocation();

  if (!token) {
    return <Navigate to={redirectTo} state={{ from: location }} />;
  }

  if (allowedRoles && !allowedRoles.includes(role?.toLocaleLowerCase())) {
    // alert(allowedRoles.includes(role?.toLocaleLowerCase()));
    // return <Navigate to="/unauthorized" replace />;
  }

  return <>{children}</>;
}
