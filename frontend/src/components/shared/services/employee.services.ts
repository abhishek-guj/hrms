import { api } from "../../../api/apiClient";
import { EMPLOYEES } from "../../../api/endpoints";
import type { EmployeeProfileDto } from "../../job/types/job.types";

export const EmployeeService = {
    async getAll(): Promise<EmployeeProfileDto[]> {
        const res = await api.get(EMPLOYEES.getAll());
        return res.data;
    },

}