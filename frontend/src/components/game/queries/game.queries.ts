import { useQuery, type UseQueryResult } from "@tanstack/react-query";
import { GameService } from "../services/game.service";
import type { AllGameSlotsDto, SlotDetailsDto } from "../types/game.types";


export const useTimeSlotsAll = (): UseQueryResult<AllGameSlotsDto[]> => {
    return useQuery({
        queryKey: ["getAllTimeSlots"],
        queryFn: (): Promise<AllGameSlotsDto[]> =>
            GameService.getAllTimeSlots(),
    });
};

export const useSlotDetails = (slotId: string): UseQueryResult<SlotDetailsDto> => {
    return useQuery({
        queryKey: ["getSlotDetails"],
        queryFn: (): Promise<SlotDetailsDto> =>
            GameService.getSlotDetails(slotId),
    });
};
