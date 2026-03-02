import { api } from "../../api/apiClient"
import type { IApiResponse } from "../../api/apiResponse.types"
import { PROFILE_ENDPOINTS } from "../../api/endpoints"
import type { EmployeeInterests, FullEmployeeProfileDto } from "./profile.types"

export const ProfileService = {
    async getProfile(): Promise<FullEmployeeProfileDto> {
        const res = await api.get<IApiResponse<FullEmployeeProfileDto>>(PROFILE_ENDPOINTS.get())
        return res.data.data;
    },

    async updateProfile(employeeInterests: EmployeeInterests): Promise<FullEmployeeProfileDto> {
        const res = await api.patch<IApiResponse<FullEmployeeProfileDto>>(PROFILE_ENDPOINTS.update(),
            employeeInterests
        )
        return res.data.data;
    }
}
