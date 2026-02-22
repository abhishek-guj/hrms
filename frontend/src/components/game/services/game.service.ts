import { api } from "../../../api/apiClient";
import type { IApiResponse } from "../../../api/apiResponse.types";
import { GAME_ENDPOINTS } from "../../../api/endpoints";
import type { AllGameSlotsDto, SlotBookingDto, SlotDetailsDto } from "../types/game.types";


export const GameService = {

    async getAllTimeSlots() {
        const res = await api.get<IApiResponse<AllGameSlotsDto[]>>(GAME_ENDPOINTS.getAllSlots());
        return res.data.data;
    },

    async getMySlotBookings() {
        const res = await api.get<IApiResponse<SlotBookingDto[]>>(GAME_ENDPOINTS.getAllSlotBookings());
        return res.data.data;
    },

    async getSlotDetails(slotId: string) {
        const res = await api.get<IApiResponse<SlotDetailsDto>>(GAME_ENDPOINTS.getSlotDetails(slotId));
        return res.data.data;
    },
    async bookSlot(slotId: string, playerIds: string[]) {
        const res = await api.post<IApiResponse<boolean>>(GAME_ENDPOINTS.bookSlot(slotId),
            {
                playerIds: playerIds
            });
        return res.data.data;
    },

    async cancelBooking(slotBookingId: string) {
        const res = await api.delete<IApiResponse<SlotDetailsDto>>(GAME_ENDPOINTS.cancelBooking(slotBookingId));
    }

}