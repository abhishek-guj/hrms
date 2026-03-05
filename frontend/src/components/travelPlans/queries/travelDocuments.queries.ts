import {
	useMutation,
	useQuery,
	useQueryClient,
	type UseQueryResult,
} from "@tanstack/react-query";
import { TravelDocumentService } from "../services/travelDocuments.service";
import type {
	DataTabelItem,
	TravelDocument,
	TravelDocumentCreateDto,
	TravelDocumentTypeRequestDto,
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

// useCreateTravelDocument

export const useCreateTravelDocument = () => {
	const queryClient = useQueryClient();
	return useMutation({
		mutationFn: async ({
			id,
			payload,
		}: {
			id: string;
			payload: TravelDocumentCreateDto;
		}) => {
			const response = await TravelDocumentService.createTravelDocument(
				id,
				payload,
			);
			return response.data;
		},
		onSuccess: () => {
			queryClient.invalidateQueries({ queryKey: ["getTravelDocuments"] });
			showSuccess("travel document created successfully");
		},
		onError: (err) => {
			showError("error creating travel document");
			console.log("error status update ", err);
		},
	});
};

export const useDeleteTravelDocument = () => {
	const queryClient = useQueryClient();
	return useMutation({
		mutationFn: async ({ id, docId }: { id: string; docId: string }) => {
			await TravelDocumentService.deleteTravelDocument(id, docId);
			return id;
		},
		onSuccess: (id) => {
			queryClient.invalidateQueries({ queryKey: ["getTravelDocuments", id] });
			queryClient.invalidateQueries({ queryKey: ["getTravelDocuments"] });
			queryClient.invalidateQueries({ queryKey: ["getTravelDocument"] });
			queryClient.invalidateQueries({ queryKey: ["getTravelDocument", id] });
			showSuccess("travel document deleted successfully");
		},
		onError: (err) => {
			console.log("error deleting travel document", err);
			showError("error deleting travel document");
		},
	});
};

export const useTravelDocumentTypes = (): UseQueryResult<DataTabelItem[]> => {
	return useQuery({
		queryKey: ["getTravelDocumentTypes"],
		queryFn: (): Promise<DataTabelItem[]> =>
			TravelDocumentService.getTravelDocumentTypes(),
	});
};

export const useUpdateTravelDocumentType = () => {
	const queryClient = useQueryClient();
	return useMutation({
		mutationFn: async ({
			id,
			payload,
		}: {
			id: string;
			payload: TravelDocumentTypeRequestDto;
		}) => {
			const response = await TravelDocumentService.updateTravelDocumentType(
				id,
				payload,
			);
			return response.data;
		},
		onSuccess: () => {
			queryClient.invalidateQueries({ queryKey: ["getTravelDocumentTypes"] });
			showSuccess("travel document type updated successfully");
		},
		onError: (err) => {
			showError("error updating document type");
			console.log("error status updating", err);
		},
	});
};

export const useDeleteTravelDocumentType = () => {
	const queryClient = useQueryClient();
	return useMutation({
		mutationFn: async ({ id }: { id: string }) => {
			await TravelDocumentService.deleteTravelDocumentType(id);
			return id;
		},
		onSuccess: (id) => {
			queryClient.invalidateQueries({
				queryKey: ["getTravelDocumentTypes", id],
			});
			queryClient.invalidateQueries({ queryKey: ["getTravelDocumentTypes"] });
			showSuccess("travel document type deleted successfully");
		},
		onError: (err) => {
			showError("error deleting document type");
			console.log("error deleting document type", err);
		},
	});
};

export const useCreateTravelDocumentType = () => {
	const queryClient = useQueryClient();
	return useMutation({
		mutationFn: async ({
			payload,
		}: {
			payload: TravelDocumentTypeRequestDto;
		}) => {
			const response =
				await TravelDocumentService.createTravelDocumentType(payload);
			return response.id;
		},
		onSuccess: (id) => {
			queryClient.invalidateQueries({
				queryKey: ["getTravelDocumentTypes", id],
			});
			queryClient.invalidateQueries({ queryKey: ["getTravelDocumentTypes"] });
			showSuccess("travel document type created successfully");
		},
		onError: (err) => {
			showError("error creating document type");
			console.log("error creating document type", err);
		},
	});
};
