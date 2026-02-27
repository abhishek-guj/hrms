import { useMutation, useQuery, useQueryClient, type UseQueryResult } from "@tanstack/react-query";
import { GameService } from "../services/game.service";
import type { AllGameSlotsDto, GameDetailsDto, GameReqDto, SlotBookingDto, SlotDetailsDto } from "../types/game.types";
import { showError, showSuccess } from "../../ui/toast";


export const useTimeSlotsAll = (): UseQueryResult<AllGameSlotsDto[]> => {
    return useQuery({
        queryKey: ["getAllTimeSlots"],
        queryFn: (): Promise<AllGameSlotsDto[]> =>
            GameService.getAllTimeSlots(),
    });
};
export const useMySlotBookings = (): UseQueryResult<SlotBookingDto[]> => {
    return useQuery({
        queryKey: ["getMySlotBookings"],
        queryFn: (): Promise<SlotBookingDto[]> =>
            GameService.getMySlotBookings(),
    });
};

export const useSlotDetails = (slotId: string): UseQueryResult<SlotDetailsDto> => {
    return useQuery({
        queryKey: ["getSlotDetails", slotId],
        queryFn: (): Promise<SlotDetailsDto> =>
            GameService.getSlotDetails(slotId),
    });
};

export const useCancelBooking = () => {
    const queryClient = useQueryClient();
    return useMutation({
        mutationFn: async ({ id }: { id: string }) => {
            const response = await GameService.cancelBooking(id);
            return id;
        },
        onSuccess: (id) => {
            queryClient.invalidateQueries({ queryKey: ["getAllTimeSlots"] })
            queryClient.invalidateQueries({ queryKey: ["getSlotDetails"] })
            queryClient.invalidateQueries({ queryKey: ["getMySlotBookings"] })
            showSuccess(`Succefully deleted ${id} booking`)
        },
        onError: (err, payload) => {
            console.log(`Error deleting ${payload.id} booking`, payload)
            showError(`Error deleting booking id: ${payload?.id} `)

        }
    })
};

export const useBookSlot = () => {
    const queryClient = useQueryClient()
    return useMutation({
        mutationFn: async ({ id, playerIds }: { id: string, playerIds: number[] }) => {
            await GameService.bookSlot(id, playerIds);
            return id;
        },
        onSuccess: (id) => {
            showSuccess(`Succefully requested slot Id: ${id}`)
            queryClient.invalidateQueries({ queryKey: ["getAllTimeSlots"] })
            queryClient.invalidateQueries({ queryKey: ["getSlotDetails"] })
            queryClient.invalidateQueries({ queryKey: ["getMySlotBookings"] })
        },
        onError: (err, payload) => {
            console.log(`Error requesting  ${payload.id} slot`, payload)
            showError(`Error requesting  ${payload.id} slot`)

        }
    })
};


export const useCreateGame = () => {
    const queryClient = useQueryClient()
    return useMutation({
        mutationFn: async ({ dto }: { dto: GameReqDto }) => {
            const response = await GameService.createGame(dto);
        },
        onSuccess: (id) => {
            showSuccess(`Succefully created game`)
            queryClient.invalidateQueries({ queryKey: ["getAllTimeSlots"] })
            queryClient.invalidateQueries({ queryKey: ["getSlotDetails"] })
            queryClient.invalidateQueries({ queryKey: ["getMySlotBookings"] })
            queryClient.invalidateQueries({ queryKey: ["getAllGames"] })
        },
        onError: (err, payload) => {
            console.log(`Error created game`, payload)
            showError(`Error creating game`)
        }
    })
};
export const useUpdateGame = () => {
    const queryClient = useQueryClient()
    return useMutation({
        mutationFn: async ({ gameId, dto }: { gameId: string, dto: GameReqDto }) => {
            console.log(gameId)
            const response = await GameService.updateGame(gameId, dto);
        },
        onSuccess: (id) => {
            showSuccess(`Succefully updated game`)
            queryClient.invalidateQueries({ queryKey: ["getAllTimeSlots"] })
            queryClient.invalidateQueries({ queryKey: ["getSlotDetails"] })
            queryClient.invalidateQueries({ queryKey: ["getMySlotBookings"] })
            queryClient.invalidateQueries({ queryKey: ["getAllGames"] })
        },
        onError: (err, payload) => {
            console.log(`Error updating game`, payload)
            showError(`Error updating game`)
        }
    })
};
export const useDeleteGame = () => {
    const queryClient = useQueryClient()
    return useMutation({
        mutationFn: async ({ gameId }: { gameId: string }) => {
            const response = await GameService.deleteGame(gameId);
        },
        onSuccess: (id) => {
            showSuccess(`Succefully deleted game`)
            queryClient.invalidateQueries({ queryKey: ["getAllTimeSlots"] })
            queryClient.invalidateQueries({ queryKey: ["getSlotDetails"] })
            queryClient.invalidateQueries({ queryKey: ["getMySlotBookings"] })
            queryClient.invalidateQueries({ queryKey: ["getAllGames"] })
        },
        onError: (err, payload) => {
            console.log(`Error deleting game`, payload)
            showError(`Error deleting game`)
        }
    })
};


export const useGetGames = (): UseQueryResult<GameDetailsDto[]> => {
    return useQuery({
        queryKey: ["getAllGames"],
        queryFn: (): Promise<GameDetailsDto[]> =>
            GameService.getGames(),
    });
};