
export interface AllGameSlotsDto {

    gameTypeId: string;
    gameTypeName: string;
    gameSlots: GameSlotDto[];

}

export interface GameSlotDto {
    id: string;
    gameTypeId: string;
    slotStart: Date;
    slotEnd: Date;
    isBooked: Boolean;
    isLowPriority: Boolean;
}

export interface SlotDetailsDto {
    gameSlot: GameSlotDto;
    canBook: boolean;
    // max players in group
    // queue in waiting list
}


// my bookings