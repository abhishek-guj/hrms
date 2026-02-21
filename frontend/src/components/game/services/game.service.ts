import { api } from "../../../api/apiClient";
import { GAME_ENDPOINTS } from "../../../api/endpoints";


export const GameService = {

    async getAllTimeSlots() {
        const res = await api.get(GAME_ENDPOINTS.getAllSlots());
        return res.data.data;
    }
}