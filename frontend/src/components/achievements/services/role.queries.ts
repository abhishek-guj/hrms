import {
	useMutation,
	useQuery,
	useQueryClient,
	type UseQueryResult,
} from "@tanstack/react-query";
import type { RoleType } from "../types/role.types";
import { RoleService } from "./role.service";
import { showError, showSuccess } from "../../ui/toast";

export const useGetAllRoles = (): UseQueryResult<RoleType[]> => {
	return useQuery({
		queryKey: ["getAllRoles"],
		queryFn: (): Promise<RoleType[]> => RoleService.getAllRoles(),
	});
};

export const useUpdateRole = () => {
	const queryClient = useQueryClient();
	return useMutation({
		mutationFn: ({ id, roleId }: { id: string; roleId: string }) =>
			RoleService.updateRole(id, roleId),
		onSuccess: () => {
			queryClient.invalidateQueries({ queryKey: ["getProfile"] });
			showSuccess(`Successfully updated profile role`);
		},
		onError: (err, payload) => {
			console.log(`Error updating role`, payload);
			showError(`Error updating role `);
		},
	});
};
