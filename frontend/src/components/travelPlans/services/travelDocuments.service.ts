import { api } from "@/api/apiClient";
import { TRAVEL_PLAN_ENDPOINTS } from "@/api/endpoints";
import type {
	DataTabelItem,
	ExpenseTypeDto,
	TravelDocument,
	TravelDocumentCreateDto,
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
	TRAVEL_DOCUMENTS_ENDPOINTS,
} from "../../../api/endpoints";

export const TravelDocumentService = {
	async getTravelDocumentById(id: string): Promise<TravelDocument[]> {
		const res = await api.get<IApiResponse<TravelDocument[]>>(
			TRAVEL_DOCUMENTS_ENDPOINTS.getByTravelId(id),
		);
		return res.data.data;
	},
	async getTravelDocumentByDocId(id: string, docId: string): Promise<TravelDocument> {
		const res = await api.get<IApiResponse<TravelDocument>>(
			TRAVEL_DOCUMENTS_ENDPOINTS.getByDocId(id, docId),
		);
		return res.data.data;
	},
	async getTravelDocumentTypes(): Promise<DataTabelItem[]> {
		const res = await api.get<IApiResponse<DataTabelItem[]>>(
			TRAVEL_DOCUMENTS_ENDPOINTS.getDocumentTypes(),
		);
		return res.data.data;
	},

	async createTravelDocument(id: string, data: TravelDocumentCreateDto) {
		const res = await api.post<IApiResponse<TravelDocumentCreateDto>>(
			TRAVEL_DOCUMENTS_ENDPOINTS.create(id),
			data,
			{
				headers: {
					"content-type": "multipart/form-data",
				},
			},
		);
		return res.data.data;
	},


	async deleteTravelDocument(id: string, docId: string) {
		const res = await api.delete<IApiResponse>(
			TRAVEL_DOCUMENTS_ENDPOINTS.delete(id, docId));
		return res.data.data;
	},
};
