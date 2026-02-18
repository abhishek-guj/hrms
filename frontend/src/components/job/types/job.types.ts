export interface JobDto {
    id: string;
    jobTitle: string;
    jobDetails: string;
    experienceYears: number;
    numberOfVaccancy: number;
    createdBy: EmployeeProfileDto;
    createdOn: string;
    updatedBy: EmployeeProfileDto;
    updatedOn: string;
    status: string; // active or close
    statusChangedOn: string;
}


export interface EmployeeProfileDto {
    id: string;
    firstName: string;
    lastName: string;
}