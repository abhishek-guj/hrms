import { api } from "../../../api/apiClient";
import type { IApiResponse } from "../../../api/apiResponse.types";
import { GAME_ENDPOINTS } from "../../../api/endpoints";
import type { AllGameSlotsDto, GameDetailsDto, GameReqDto, SlotBookingDto, SlotDetailsDto } from "../types/game.types";


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
    async bookSlot(slotId: string, playerIds: number[]) {
        const res = await api.post<IApiResponse<boolean>>(GAME_ENDPOINTS.bookSlot(slotId),
            {
                playerIds: playerIds
            });
        return res.data.data;
    },

    async cancelBooking(slotBookingId: string) {
        await api.delete<IApiResponse<SlotDetailsDto>>(GAME_ENDPOINTS.cancelBooking(slotBookingId));
    },

    async createGame(dto: GameReqDto) {
        const res = await api.post<IApiResponse<boolean>>(GAME_ENDPOINTS.createGame(), dto);
        return res.data.data;
    },
    async updateGame(gameId: string, dto: GameReqDto) {
        const res = await api.put<IApiResponse<boolean>>(GAME_ENDPOINTS.updateGame(gameId), dto);
        return res.data.data;
    },

    async deleteGame(gameId: string) {
        const res = await api.delete<IApiResponse<boolean>>(GAME_ENDPOINTS.deleteGame(gameId));
        return res.data.data;
    },

    async getGames() {
        const res = await api.get<IApiResponse<GameDetailsDto[]>>(GAME_ENDPOINTS.getGames());
        return res.data.data;
    },

}