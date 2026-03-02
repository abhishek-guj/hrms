import type { GameDetailsDto } from "../game/types/game.types";
import type { EmployeeProfileDto } from "../job/types/job.types";


export interface FullEmployeeProfileDto {
    id: number;
    firstName: string;
    lastName: string;
    userEmail: string;
    contactNumber: number;
    manager: EmployeeProfileDto;
    birthDate: string;
    joiningDate: string;
    interests: GameDetailsDto[];
}

export interface EmployeeInterests {
    // game ids
    gameTypeIds: number[];
}