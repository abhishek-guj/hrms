import {
	useMutation,
	useQuery,
	useQueryClient,
	type UseQueryResult,
} from "@tanstack/react-query";
import { TravelPlansService } from "../services/travelPlans.service";
import type {
	DataTabelItem,
	TravelPlanCreateDto,
	TravelPlanDto,
	TravelPlanUpdateDto,
} from "../types/TravelPlan.types";

export const useTravelPlans = () => {
	return useQuery({
		queryKey: ["getTravelPlans"],
		queryFn: (): Promise<DataTabelItem[]> =>
			TravelPlansService.getTravelPlans(),
	});
};

export const useTravelPlan = (id: string): UseQueryResult<TravelPlanDto> => {
	return useQuery({
		queryKey: ["getTravelPlan", id],
		queryFn: async (): Promise<TravelPlanDto> =>
			await TravelPlansService.getTravelPlanById(id),
	});
};

export const useCreateTravelPlan = () => {
	const queryClient = useQueryClient();
	return useMutation({
		mutationFn: async ({ payload }: { payload: TravelPlanCreateDto }) => {
			const response = await TravelPlansService.createTravelPlan(payload);
			return response.data;
		},
		onSuccess: () => {
			queryClient.invalidateQueries({ queryKey: ["getTravelPlans"] });
			alert("travel plan created successfull");
		},
		onError: () => {
			console.log("error creating travel plan ");
		},
	});
};

export const useUpdateTravelPlan = () => {
	const queryClient = useQueryClient();
	return useMutation({
		mutationFn: ({
			id,
			payload,
		}: {
			id: string;
			payload: TravelPlanUpdateDto;
		}) => {
			const response = TravelPlansService.updateTravelPlan(id, payload);
			return response;
		},
		onSuccess: (_, { id }) => {
			queryClient.invalidateQueries({ queryKey: ["getTravelPlan", id] });
			queryClient.invalidateQueries({ queryKey: ["getTravelPlans"] });
			alert("travel plan updated successfull");
		},
		onError: () => {
			console.log("error updaeting travel plan");
		},
	});
};

export const useDeleteTravelPlan = () => {
	const queryClient = useQueryClient();
	return useMutation({
		mutationFn: async ({ id }: { id: string }) => {
			const response = await TravelPlansService.deleteTravelPlan(id);
			return id;
		},
		onSuccess: (id) => {
			queryClient.invalidateQueries({ queryKey: ["getTravelPlan", id] });
			queryClient.invalidateQueries({ queryKey: ["getTravelPlans"] });
			alert("travel plan deleted successfull");
		},
		onError: () => {
			console.log("error deleted travel plan");
		},
	});
};
