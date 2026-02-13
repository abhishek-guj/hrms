import { api } from "@/api/apiClient";
import { TRAVEL_PLAN_ENDPOINTS } from "@/api/endpoints";
import type {
	DataTabelItem,
	TravelPlanCreateDto,
	TravelPlanDto,
	TravelPlanUpdateDto,
} from "../types/TravelPlan.types";
import type { IApiResponse } from "@/api/apiResponse.types";

export const TravelPlansService = {
	async getTravelPlans(): Promise<DataTabelItem[]> {
		const res = await api.get<IApiResponse<TravelPlanDto[]>>(
			TRAVEL_PLAN_ENDPOINTS.getAll(),
		);
		return res.data.data;
	},

	async getTravelPlanById(id: string): Promise<TravelPlanDto> {
		const res = await api.get<IApiResponse<TravelPlanDto>>(
			TRAVEL_PLAN_ENDPOINTS.getById(id),
		);
		return res.data.data;
	},

	async createTravelPlan(data: TravelPlanCreateDto) {
		// console.log("dateeeeeeeeee", data);
		const res = await api.post<IApiResponse<TravelPlanDto>>(
			TRAVEL_PLAN_ENDPOINTS.create(),
			data,
		);
		console.log(res.data);
		return res.data.data;
	},

	async updateTravelPlan(id: number, data: TravelPlanUpdateDto) {
		console.log(id, data);
		const res = await api.put<IApiResponse<TravelPlanDto>>(
			TRAVEL_PLAN_ENDPOINTS.update(id),
			// { id, data },
			data,
		);
		console.log(res.data);
		return res.data.data;
	},

	async deleteTravelPlan(id: string) {
		const res = await api.delete<IApiResponse>(
			TRAVEL_PLAN_ENDPOINTS.delete(id),
			{ id },
		);
		console.log(res.data);
		return res.data.data;
	},
};
