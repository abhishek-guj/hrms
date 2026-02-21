import { api } from "../../../api/apiClient";
import type { IApiResponse } from "../../../api/apiResponse.types";
import { GAME_ENDPOINTS } from "../../../api/endpoints";
import type { AllGameSlotsDto, SlotDetailsDto } from "../types/game.types";


export const GameService = {

    async getAllTimeSlots() {
        const res = await api.get<IApiResponse<AllGameSlotsDto[]>>(GAME_ENDPOINTS.getAllSlots());
        return res.data.data;
    },

    async getSlotDetails(slotId: string) {
        const res = await api.get<IApiResponse<SlotDetailsDto>>(GAME_ENDPOINTS.getSlotDetails(slotId));
        return res.data.data;
    },


}