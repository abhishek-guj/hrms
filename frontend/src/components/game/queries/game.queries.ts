import { useQuery } from "@tanstack/react-query";
import { GameService } from "../services/game.service";


export const useTimeSlotsAll = () => {
    return useQuery({
        queryKey: ["getAllTimeSlots"],
        queryFn: () =>
            GameService.getAllTimeSlots(),
    });
};
