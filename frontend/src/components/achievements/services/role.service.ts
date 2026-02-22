import { api } from '../../../api/apiClient';
import type { RoleType } from '../types/role.types';

export const RoleService = {
    async getAllRoles(): Promise<RoleType[]> {
        const res = await api.get('/roles');
        return res.data;
    },
};