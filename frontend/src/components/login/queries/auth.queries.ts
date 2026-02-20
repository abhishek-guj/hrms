import { useMutation, useQuery } from "@tanstack/react-query";
import { AuthService } from "../services/auth.service";
import { showError, showSuccess } from "../../ui/toast";

// export const useLogin = (email: string, password: string) => {
// 	return useQuery({
// 		queryKey: ["login"],
// 		queryFn: () => AuthService.login(email, password),
// 	});
// };

// https://github.com/hassan-kamel/axios-react-query-example/blob/070e3ba929b38009f07d89f3dcecd754863e2c75/src/pages/orders/components/OrderDetail.tsx#L18
export const useLogin = () => {
	return useMutation({
		mutationFn: async ({
			email,
			password,
		}: {
			email: string;
			password: string;
		}) => {
			const response = await AuthService.login(email, password);
			console.log(response)
			return response.data;
		},
		onSuccess: (data) => {
			localStorage.setItem("token", data.data.token)
			localStorage.setItem("role", data.data.role)
			localStorage.setItem("employeeId", data.data.employeeId)
			showSuccess("Login successfull");
		},
		onError: () => {
			showError("Error logging in");
		},
	});
};

