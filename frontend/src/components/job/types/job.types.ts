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
    jobJdFile: JobJdFileDto;
}


export interface EmployeeProfileDto {
    id: string;
    firstName: string;
    lastName: string;
}

export interface JobJdFileDto {
    // Long 
    id: string;
    // String 
    filePath: string;
    // LocalDateTime 
    uploadedOn: string;
    // EmployeeProfileDto 
    uploadedBy: string;
}
