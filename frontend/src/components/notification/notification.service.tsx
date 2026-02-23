import { api } from "../../api/apiClient";
import type { IApiResponse } from "../../api/apiResponse.types";
import { NOTIFICATION_ENDPOINTS } from "../../api/endpoints";

export const NotificationService = {
    async getAllNotification(){
        const res = await api.get(NOTIFICATION_ENDPOINTS.getAll());
        return res.data.data;
    },}