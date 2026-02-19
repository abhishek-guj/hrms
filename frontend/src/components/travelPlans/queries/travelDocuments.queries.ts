import {
	useMutation,
	useQuery,
	useQueryClient,
	type UseQueryResult
} from "@tanstack/react-query";
import { TravelDocumentService } from "../services/travelDocuments.service";
import type {
	DataTabelItem,
	TravelDocument,
	TravelDocumentCreateDto
} from "../types/TravelPlan.types";
import { showError, showSuccess } from "../../ui/toast";

export const useTravelDocuments = (id: string) => {
	return useQuery({
		queryKey: ["getTravelDocuments", id],
		queryFn: async (): Promise<TravelDocument[]> =>
			TravelDocumentService.getTravelDocumentById(id),
	});
};

export const useTravelDocumentById = (id: string, docId: string) => {
	return useQuery({
		queryKey: ["getTravelDocument", docId],
		queryFn: async (): Promise<TravelDocument> =>
			TravelDocumentService.getTravelDocumentByDocId(id, docId),
	});
};


export const useTravelDocumentTypes = (): UseQueryResult<DataTabelItem[]> => {
	return useQuery({
		queryKey: ["getExpenseTypes"],
		queryFn: (): Promise<DataTabelItem[]> =>
			TravelDocumentService.getTravelDocumentTypes(),
	});
};

// useCreateTravelDocument

export const useCreateTravelDocument = () => {
	const queryClient = useQueryClient();
	return useMutation({
		mutationFn: async ({ id, payload }: { id: string, payload: TravelDocumentCreateDto }) => {
			const response = await TravelDocumentService.createTravelDocument(id, payload);
			return response.data;
		},
		onSuccess: () => {
			queryClient.invalidateQueries({ queryKey: ["getTravelDocuments"] });
			showSuccess("expense created successfull");
		},
		onError: (err) => {
			showError("error creating document", err);
			console.log("error status update ");
		},
	});
};


export const useDeleteTravelDocument = () => {
	const queryClient = useQueryClient();
	return useMutation({
		mutationFn: async ({ id, docId }: { id: string, docId: string }) => {
			const response = await TravelDocumentService.deleteTravelDocument(id, docId);
			return id;
		},
		onSuccess: (id) => {
			queryClient.invalidateQueries({ queryKey: ["getTravelDocuments", id] });
			queryClient.invalidateQueries({ queryKey: ["getTravelDocuments"] });
			queryClient.invalidateQueries({ queryKey: ["getTravelDocument"] });
			showSuccess("travel document deleted successfully");
		},
		onError: () => {
			console.log("error deleted travel document");
			showError("error deleted travel document");
		},
	});
};