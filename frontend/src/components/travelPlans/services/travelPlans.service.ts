import { api } from "@/api/apiClient";
import { TRAVEL_PLAN_ENDPOINTS } from "@/api/endpoints";

export const TravelPlansService = {
	async getTravelPlans() {
		const res = await api.get(TRAVEL_PLAN_ENDPOINTS.get);
		console.log(res.data);
		return {
			travelPlans: res.data,
		};
	},
};
