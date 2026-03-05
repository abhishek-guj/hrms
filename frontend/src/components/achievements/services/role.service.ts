import { api } from "../../../api/apiClient";
import type { RoleType } from "../types/role.types";

export const RoleService = {
	async getAllRoles(): Promise<RoleType[]> {
		const res = await api.get("/roles");
		return res.data;
	},

	async updateRole(id: string, roleId: string) {
		const res = await api.put(`/employees/role/${id}`, roleId);
		return res.data;
	},
};
