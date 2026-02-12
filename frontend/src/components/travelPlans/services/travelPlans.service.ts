import { api } from "@/api/apiClient";
import { TRAVEL_PLAN_ENDPOINTS } from "@/api/endpoints";
import type { IApiResponse } from "../../../api/apiResponse.types";
import type { DataTabelItem } from "../types/TravelPlan.types";

export const TravelPlansService = {
	async getTravelPlans(): Promise<DataTabelItem[]> {
		const res: IApiResponse = await api.get(TRAVEL_PLAN_ENDPOINTS.getAll());
		return res.data.data;
	},
};
