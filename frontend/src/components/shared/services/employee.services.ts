import { api } from "../../../api/apiClient";
import { EMPLOYEES } from "../../../api/endpoints";
import type { EmployeeProfileDto } from "../../job/types/job.types";
import type { FullEmployeeProfileDto } from "../../profile/profile.types";

export const EmployeeService = {
	async getAll(): Promise<EmployeeProfileDto[]> {
		const res = await api.get(EMPLOYEES.getAll());
		return res.data.data;
	},

	async getFullAll(): Promise<FullEmployeeProfileDto[]> {
		const res = await api.get(EMPLOYEES.getFullAll());
		return res.data.data;
	},
	async getTravelPlanEmployees(id: string): Promise<EmployeeProfileDto[]> {
		const res = await api.get(EMPLOYEES.getTravelPlanEmployees(id));
		return res.data.data;
	},
};
