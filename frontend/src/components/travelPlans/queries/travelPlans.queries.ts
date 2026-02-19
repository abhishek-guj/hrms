import {
	useMutation,
	useQuery,
	useQueryClient,
	type UseQueryResult,
} from "@tanstack/react-query";
import { TravelPlansService } from "../services/travelPlans.service";
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
import { showError, showSuccess } from "../../ui/toast";
import { api } from "../../../api/apiClient";

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
			showSuccess("travel plan updated successfully");
		},
		onError: () => {
			showError("error creating travel plan");
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
			showSuccess("travel plan updated successfully");

		},
		onError: () => {
			showError("error travel plan updating");
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
			showSuccess("travel plan deleted successfully");
		},
		onError: () => {
			console.log("error deleted travel plan");
			showError("error deleted travel plan");
		},
	});
};

export const useTravelEmployees = (
	id: string,
): UseQueryResult<TravelEmployeeDto[]> => {
	return useQuery({
		queryKey: ["getTravelEmployees", id],
		queryFn: (): Promise<TravelEmployeeDto[]> =>
			TravelPlansService.getTravelEmployeesByTravelPlanId(id),
	});
};

export const useUpdateTravelEmployees = () => {
	console.log("useUpdateTravelEmployees");
	const queryClient = useQueryClient();
	return useMutation({
		mutationFn: ({ id, payload }: { id: string; payload: number[] }) => {
			const response = TravelPlansService.updateTravelEmployees(id, payload);
			return response;
		},
		onSuccess: (_, { id }) => {
			queryClient.invalidateQueries({ queryKey: ["getTravelEmployees", id] });
			queryClient.invalidateQueries({ queryKey: ["getTravelEmployees"] });
			showError("employees assigned successfully");
		},
		onError: () => {
			console.log("error updaeting travel plan");
			showError("error assgining employees");
		},
	});
};

export const useTravelExpenses = (
	id: string,
): UseQueryResult<TravelExpenseDto[]> => {
	return useQuery({
		queryKey: ["getTravelExpenses"],
		queryFn: (): Promise<TravelExpenseDto[]> =>
			TravelPlansService.getTravelExpensesByTravelPlanId(id),
	});
};

export const useTravelExpenseById = (
	id: string,
): UseQueryResult<TravelExpenseDto> => {
	return useQuery({
		queryKey: ["getTravelExpenses", id],
		queryFn: (): Promise<TravelExpenseDto> =>
			TravelPlansService.getTravelExpenseById(id),
	});
};

export const useExpenseTypes = (): UseQueryResult<ExpenseTypeDto[]> => {
	return useQuery({
		queryKey: ["getExpenseTypes"],
		queryFn: (): Promise<ExpenseTypeDto[]> =>
			TravelPlansService.getExpenseTypes(),
	});
};

export const useCreateTravelExpense = () => {
	const queryClient = useQueryClient();
	return useMutation({
		mutationFn: async ({ payload }: { payload: TravelExpenseRequestDto }) => {
			const response = await TravelPlansService.createTravelExpense(payload);
			return response.data;
		},
		onSuccess: () => {
			queryClient.invalidateQueries({ queryKey: ["getTravelExpenses"] });
			// alert("expense created successfull");
			showSuccess("expense created successfull");
		},
		onError: () => {
			showError("error creating expense");
			console.log("error status update ");
		},
	});
};

export const useChangeStatus = () => {
	const queryClient = useQueryClient();
	return useMutation({
		mutationFn: async ({ id, payload }: { id: string, payload: boolean }) => {
			const response = await api.put(`/travel-expense/${id}/status`, { status: payload });
			return response.data;
		},
		onSuccess: (id) => {
			queryClient.invalidateQueries({ queryKey: ["getTravelExpenses", id] });
			showSuccess("status update successfull");
		},
		onError: () => {
			showError("error status update");
			console.log("error creating expense ");
		},
	});
}

export const useDeleteTravelExpense = () => {
	const queryClient = useQueryClient();
	return useMutation({
		mutationFn: async ({ id }: { id: string }) => {
			const response = await TravelPlansService.deleteTravelExpense(id);
			return id;
		},
		onSuccess: (id) => {
			queryClient.invalidateQueries({ queryKey: ["getTravelExpenses", id] });
			queryClient.invalidateQueries({ queryKey: ["getTravelExpenses"] });
			showSuccess("expense deleted successfull");

		},
		onError: () => {
			showError("error deleting expense");
		},
	});
};
