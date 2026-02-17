import { useQuery, type UseQueryResult } from "@tanstack/react-query";
import type { TravelPlanDto } from "../types/TravelPlan.types";
import { ExpenseDocumentSerivce } from "./documents.service";

export const useExpenseDocument = (id: string): UseQueryResult<Blob> => {
	return useQuery({
		queryKey: ["getExpenseDocument", id],
		queryFn: async (): Promise<Blob> => {
			const res = await ExpenseDocumentSerivce.getExpenseDocument(id);
			console.log("res", res);
			return res;
		},
	});
};
