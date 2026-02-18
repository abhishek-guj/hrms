import { api } from "../../../api/apiClient";
import type { IApiResponse } from "../../../api/apiResponse.types";
import { JOB_ENDPOINTS } from "../../../api/endpoints";
import type { JobDto } from "../types/job.types";


export const JobService = {
    async getAllJobs(): Promise<JobDto[]> {
        const res = await api.get<IApiResponse<JobDto[]>>(JOB_ENDPOINTS.getAll());
        return res.data.data;
    },
    async getJobById(id: string): Promise<JobDto> {
        const res = await api.get<IApiResponse<JobDto>>(JOB_ENDPOINTS.getById(id))
        return res.data.data;
    }
}