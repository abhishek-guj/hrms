import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { showError, showSuccess } from "../ui/toast";
import { ProfileService } from "./profile.service";
import type { EmployeeInterests, FullEmployeeProfileDto } from "./profile.types";


export const useProfile = () => useQuery({
    queryKey: ["getProfile"],
    queryFn: (): Promise<FullEmployeeProfileDto> => ProfileService.getProfile(),
})

export const useUpdateProfile = () => {
    const queryClient = useQueryClient();
    return useMutation({
        mutationFn: (employeeInterests: EmployeeInterests) => ProfileService.updateProfile(employeeInterests),
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ["getProfile"] })
            showSuccess(`Successfully updated profile`)
        },
        onError: (err, payload) => {
            console.log(`Error updating interests`, payload)
            showError(`Error updating interests `)
        }
    })
}