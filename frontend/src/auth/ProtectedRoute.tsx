import { Navigate, Outlet, useLocation } from "react-router-dom";
import { useLocalStorage } from "../hooks/useLocalStorage";

export function getAuthState() {
  const { getItem: getToken } = useLocalStorage("token");
  const { getItem: getRole } = useLocalStorage("role");
  const { getItem: getEmployeeId } = useLocalStorage("employeeId");

  const token = getToken()
  const role = getRole()
  const employeeId = getEmployeeId()

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

  if (allowedRoles && !allowedRoles.includes(role)) {
    return <Navigate to="/unauthorized" replace />;
  }

  return <>{children}</>;
}
