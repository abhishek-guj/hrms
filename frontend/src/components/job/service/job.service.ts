import type { JobReferralDto, JobReferralReqDto } from './../types/job.types';
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
    },

    async deleteJob(id: string) {
        const res = await api.delete<IApiResponse<any>>(
            JOB_ENDPOINTS.delete(id),
        );
        return res.data;
    },
    async shareJob(id: string, email: string): Promise<string> {
        const res = await api.post<IApiResponse<string>>(JOB_ENDPOINTS.share(id),
            { email: email }
        );
        return res.data.data;
    },
    async referJob(id: string, data: JobReferralReqDto): Promise<boolean> {
        const res = await api.post<IApiResponse<boolean>>(JOB_ENDPOINTS.refer(id),
            data,
            {
                headers: {
                    "content-type": "multipart/form-data",
                },
            },
        );
        return res.data.data;
    },
    async getAllReferrals(): Promise<JobReferralDto[]> {
        const res = await api.get<IApiResponse<JobReferralDto[]>>(JOB_ENDPOINTS.getAllRefers());
        return res.data.data;
    },
    async updateStatus(id: string, status: string): Promise<string> {
        const res = await api.put<IApiResponse<string>>(JOB_ENDPOINTS.updateStatus(id),
            { status }
        );
        return res.data.data;
    },


}