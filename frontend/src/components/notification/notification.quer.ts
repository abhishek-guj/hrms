import { useMutation, useQuery, useQueryClient, type UseQueryResult } from "@tanstack/react-query";
import { NotificationService } from "./notification.service";
import { showInfo } from "../ui/toast";
import { api } from "../../api/apiClient";

export const useNotificationALL = () => {
    return useQuery({
        queryKey: ["getAllNotification"],
        queryFn: () =>
            NotificationService.getAllNotification(),
    });
};


export const useRead = () => {
    const queryClient = useQueryClient();
    return useMutation({
        mutationFn: async ({ id }: { id: string }) => {
            await api.post(`/notifications/${id}`);
        },
        onSuccess: (id) => {
            queryClient.invalidateQueries({ queryKey: ["getAllNotification"] })
            showInfo("read");
        },
        onError: (err, payload) => {
            console.log(`Error deleting ${payload.id} noti`, payload)
        }
    })
};