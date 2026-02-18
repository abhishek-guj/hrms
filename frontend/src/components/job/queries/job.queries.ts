import { useQuery, type UseQueryResult } from "@tanstack/react-query";
import type { JobDto } from "../types/job.types";
import { JobService } from "../service/job.service";


export const useJobsAll = (): UseQueryResult<JobDto[]> => {
    return useQuery({
        queryKey: ["getAllJobs"],
        queryFn: (): Promise<JobDto[]> =>
            JobService.getAllJobs(),
    });
};

export const useJobsById = (id: string): UseQueryResult<JobDto> => {
    return useQuery({
        queryKey: ["getAllJobs", id],
        queryFn: (): Promise<JobDto> => JobService.getJobById(id),
    })
}