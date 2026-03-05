import { useMutation, useQuery } from "@tanstack/react-query";
import { AuthService } from "../services/auth.service";
import { showError, showSuccess } from "../../ui/toast";
import { useLocalStorage } from "../../../hooks/useLocalStorage";

// https://github.com/hassan-kamel/axios-react-query-example/blob/070e3ba929b38009f07d89f3dcecd754863e2c75/src/pages/orders/components/OrderDetail.tsx#L18
export const useLogin = () => {
	const { setItem: setToken } = useLocalStorage("token");
	const { setItem: setRole } = useLocalStorage("role");
	const { setItem: setEmployeeId } = useLocalStorage("employeeId");

	const saveAuthDetails = (data: any) => {
		setToken(data.data.token);
		setRole(data.data.role);
		setEmployeeId(data.data.employeeId);
	};

	return useMutation({
		mutationFn: async ({
			email,
			password,
		}: {
			email: string;
			password: string;
		}) => {
			const response = await AuthService.login(email, password);
			return response.data;
		},
		onSuccess: (data) => {
			saveAuthDetails(data);
			showSuccess("Login successful");
		},
		onError: () => {
			showError("Error logging in");
		},
	});
};
