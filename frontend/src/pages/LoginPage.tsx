import { Navigate, useLocation } from "react-router-dom";
import { LoginForm } from "../components/login/LoginForm";
import { LoginFormCard } from "../components/login/LoginFormCard";
import { getAuthState } from "../auth/ProtectedRoute";

const LoginPage = () => {
  const { token } = getAuthState();
  const location = useLocation();

  if (token) {
    return <Navigate to={"/"} state={{ from: location }} />;
  }
  return (
    <div className="flex min-h-svh w-full items-center justify-center p-6 md:p-10">
      <div className="w-full max-w-sm">
        <LoginFormCard>
          <LoginForm />
        </LoginFormCard>
      </div>
    </div>
  );
};

export default LoginPage;
