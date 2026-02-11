import { api } from "@/api/apiClient";
import { AUTH_ENDPOINTS } from "../../../api/endpoints";

export const AuthService = {
	async login(email: string, password: string) {
		const res = await api.post(AUTH_ENDPOINTS.login(), { email, password });
		return {
			data: res.data,
		};
	},
};
