import { api } from "@/api/apiClient";
import { TRAVEL_PLAN_ENDPOINTS } from "@/api/endpoints";

export const TravelPlansService = {
	async getTravelPlans() {
		const res = await api.get("http://localhost:8081/api/v1/travel-plans");
		console.log(res.data);
		return {
			travelPlans: res.data,
		};
	},
};
