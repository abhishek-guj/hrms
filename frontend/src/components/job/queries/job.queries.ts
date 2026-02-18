import { useMutation, useQuery, useQueryClient, type UseQueryResult } from "@tanstack/react-query";
import type { JobDto } from "../types/job.types";
import { JobService } from "../service/job.service";
import { showError, showSuccess } from "../../ui/toast";


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

export const useDeleteJob = () => {
    const queryClient = useQueryClient();
    return useMutation({
        mutationFn: async ({ id }: { id: string }) => {
            const response = await JobService.deleteJob(id);
            return id;
        },
        onSuccess: (id) => {
            queryClient.invalidateQueries({ queryKey: ["getAllJobs"] })
            queryClient.invalidateQueries({ queryKey: ["getAllJobs", id] })
            showSuccess(`Succefully deleted ${id} Job`)
        },
        onError: (err, payload) => {
            console.log(`Error deleting ${payload.id} Job`, payload)
            showError(`Error deleting Job id: ${payload?.id} `)

        }
    })
};
