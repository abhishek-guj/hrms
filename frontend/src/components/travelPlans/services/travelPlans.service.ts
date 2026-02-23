import { api } from "@/api/apiClient";
import { TRAVEL_PLAN_ENDPOINTS } from "@/api/endpoints";
import type {
	DataTabelItem,
	ExpenseTypeDto,
	TravelEmployeeDto,
	TravelExpenseDto,
	TravelExpenseRequestDto,
	TravelPlanCreateDto,
	TravelPlanDto,
	TravelPlanUpdateDto,
} from "../types/TravelPlan.types";
import type { IApiResponse } from "@/api/apiResponse.types";
import {
	EXPENSE_ENDPOINTS,
	EXPENSE_TYPES_ENDPOINTS,
} from "../../../api/endpoints";

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

	async getTravelEmployeesByTravelPlanId(
		id: string,
	): Promise<TravelEmployeeDto[]> {
		const res = await api.get<IApiResponse<TravelEmployeeDto[]>>(
			TRAVEL_PLAN_ENDPOINTS.getTravelEmployeesByTravelPlanId(id),
		);
		return res.data.data;
	},

	// async getAllEmployees(): Promise<EmployeeDto[]> {
	// 	const res = await api.get<IApiResponse<EmployeeDto[]>>(
	// 		TRAVEL_PLAN_ENDPOINTS.getAllEmployees(),
	// 	);
	// 	return res.data.data;
	// },

	async updateTravelEmployees(id: string, data: number[]) {
		console.log("updateTravelEmployees");
		const res = await api.put<IApiResponse>(
			TRAVEL_PLAN_ENDPOINTS.updateTravelEmployees(id),
			data,
		);
		return res.data.data;
	},

	async getTravelExpensesByTravelPlanId(
		id: string,
	): Promise<TravelExpenseDto[]> {
		const res = await api.get<IApiResponse<TravelExpenseDto[]>>(
			TRAVEL_PLAN_ENDPOINTS.getTravelExpensesByTravelPlanId(id),
		);
		return res.data.data;
	},

	// expense types

	async getExpenseTypes(): Promise<ExpenseTypeDto[]> {
		const res = await api.get<IApiResponse<ExpenseTypeDto[]>>(
			EXPENSE_TYPES_ENDPOINTS.getAll(),
		);
		return res.data.data;
	},

	// expense
	async getTravelExpenseById(id: string): Promise<TravelExpenseDto> {
		const res = await api.get<IApiResponse<TravelExpenseDto>>(
			EXPENSE_ENDPOINTS.getTravelExpenseById(id),
		);
		return res.data.data;
	},

	async createTravelExpense(data: TravelExpenseRequestDto) {
		const res = await api.post<IApiResponse<TravelExpenseDto>>(
			EXPENSE_ENDPOINTS.create(),
			data,
			{
				headers: {
					"content-type": "multipart/form-data",
				},
			},
		)
		return res.data;
	},

	async deleteTravelExpense(id: string) {
		const res = await api.delete<IApiResponse>(EXPENSE_ENDPOINTS.delete(id), {
			id,
		});
		return res.data.data;
	},
};
