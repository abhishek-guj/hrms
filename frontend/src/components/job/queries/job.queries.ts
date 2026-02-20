import { useMutation, useQuery, useQueryClient, type UseQueryResult } from "@tanstack/react-query";
import type { JobDto, JobReferralDto, JobReferralReqDto } from "../types/job.types";
import { JobService } from "../service/job.service";
import { showError, showSuccess } from "../../ui/toast";


export const useJobsAll = (): UseQueryResult<JobDto[]> => {
    return useQuery({
        queryKey: ["getAllJobs"],
        queryFn: (): Promise<JobDto[]> =>
            JobService.getAllJobs(),
    });
};

export const useAllReferrals = (): UseQueryResult<JobReferralDto[]> => {
    return useQuery({
        queryKey: ["getAllReferrals"],
        queryFn: (): Promise<JobReferralDto[]> =>
            JobService.getAllReferrals(),
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

export const useShareJob = () => {
    return useMutation({
        mutationFn: async ({ id, email }: { id: string, email: string }) => {
            const response = await JobService.shareJob(id, email);
            return id;
        },
        onSuccess: (id) => {
            showSuccess(`Succefully shared Job Id: ${id}`)
        },
        onError: (err, payload) => {
            console.log(`Error sharing ${payload.id} Job`, payload)
            showError(`Error sharing Job id: ${payload?.id} `)

        }
    })
};


export const useReferJob = () => {
    const queryClient = useQueryClient();
    return useMutation({
        mutationFn: async ({ jobId, payload }: { jobId: string, payload: JobReferralReqDto }) => {
            const response = await JobService.referJob(jobId, payload);
            return response;
        },
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ["getAllReferrals"] })
            showSuccess("Job Referred successfully");
        },
        onError: () => {
            console.log("error creating expense ");
            showError("Some error occurred during referring")
        },
    });
};

export const useUpdateStatus = () => {
    const queryClient = useQueryClient();
    return useMutation({
        mutationFn: async ({ id, status }: { id: string, status: string }) => {
            const response = await JobService.updateStatus(id, status);
            return id;
        },
        onSuccess: (id) => {
            queryClient.invalidateQueries({ queryKey: ["getAllReferrals"] })
            showSuccess(`Succefully updated ${id} Referral`)
        },
        onError: (err, payload) => {
            console.log(`Error deleting ${payload.id} Job`, payload)
            showError(`Error updating Referral id: ${payload?.id} `)

        }
    })
};
