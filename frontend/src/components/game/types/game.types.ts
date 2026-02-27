import type { EmployeeProfileDto } from "../../job/types/job.types";

export interface AllGameSlotsDto {

    gameTypeId: string;
    gameTypeName: string;
    gameSlots: GameSlotDto[];

}

export interface GameSlotDto {
    id: string;
    gameTypeId: string;
    gameTypeName: string;
    slotStart: Date;
    slotEnd: Date;
    booked: boolean;
    lowPriority: boolean;
}

export interface SlotDetailsDto {
    gameSlot: GameSlotDto;
    canBook: boolean;
    inGroup: boolean;
    slotSizes: number[];
    inQueue: boolean
}


export interface SlotBookingDto {
    id: string;
    playerGroup: PlayerGroupDto;
    gameSlot: GameSlotDto;
    status: string;
    groupOwner: EmployeeProfileDto;
    slotSizes: number[];
}
export interface PlayerGroupDto {
    id: string;
    players: EmployeeProfileDto[];
}




// my bookings

// {
//     "gameTypeId": 1,
//         "gameTypeName": "",
//             "maxSlotDurationMinutes": 1,
//                 "slotSizes": [
//                     1
//                 ],
//                     "startTime": "",
//                         "endTime": ""
// }

export interface GameReqDto {
    gameTypeId: string;
    gameTypeName: string;
    maxSlotDurationMinutes: number;
    slotSizes: number[];
    startTime: string;
    endTime: string;
}
export interface GameDetailsDto {
    gameTypeId: string;
    gameTypeName: string;
    maxSlotDurationMinutes: number;
    slotSizes: number[];
    startTime: string;
    endTime: string;

}