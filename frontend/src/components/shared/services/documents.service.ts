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
	DOCUMENTS_GET_ENDPOINTS,
	EXPENSE_ENDPOINTS,
	EXPENSE_TYPES_ENDPOINTS,
} from "../../../api/endpoints";

export const ExpenseDocumentSerivce = {
	async getExpenseDocument(id: string): Promise<Blob> {
		const res = await api.get(DOCUMENTS_GET_ENDPOINTS.getExpense(), {
			responseType: "blob",
			params: { id },
		});
		console.log(res);
		return res.data;
	},

	async getJobJdDocument(id: string): Promise<Blob> {
		const res = await api.get(DOCUMENTS_GET_ENDPOINTS.getJobJd(), {
			responseType: "blob",
			params: { id },
		});
		console.log(res);
		return res.data;
	},

	async getDocument(id: string, type: string): Promise<Blob> {
		const res = await api.get(DOCUMENTS_GET_ENDPOINTS.getDocument(type), {
			responseType: "blob",
			params: { id },
		});
		console.log(res);
		return res.data;
	},

};
