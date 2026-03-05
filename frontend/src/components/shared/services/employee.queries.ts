import { useQuery, type UseQueryResult } from "@tanstack/react-query";
import type { EmployeeProfileDto } from "../../job/types/job.types";
import { EmployeeService } from "./employee.services";
import type { FullEmployeeProfileDto } from "../../profile/profile.types";

export const useEmployeesAll = (): UseQueryResult<EmployeeProfileDto[]> => {
	return useQuery({
		queryKey: ["getAllEmployees"],
		queryFn: async (): Promise<EmployeeProfileDto[]> => {
			const res = await EmployeeService.getAll();
			return res;
		},
	});
};

export const useFullEmployeesAll = (): UseQueryResult<
	FullEmployeeProfileDto[]
> => {
	return useQuery({
		queryKey: ["getAllEmployees"],
		queryFn: async (): Promise<FullEmployeeProfileDto[]> => {
			const res = await EmployeeService.getFullAll();
			return res;
		},
	});
};

export const useEmployeesTravelPlan = (
	id: string,
): UseQueryResult<EmployeeProfileDto[]> => {
	return useQuery({
		queryKey: ["useEmployeesTravelPlan"],
		queryFn: async (): Promise<EmployeeProfileDto[]> => {
			const res = await EmployeeService.getTravelPlanEmployees(id);
			return res;
		},
	});
};
