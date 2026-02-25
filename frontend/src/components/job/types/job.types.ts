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


export interface JobReferralReqDto {
    jobId: string;
    firstName: string;
    lastName: string;
    email: string;
    contactNumber: string;
    referredById: string;
    cvFile: File;
    note: string;
}

export interface JobReferralDto {
    id: string,
    jobId: string;
    firstName: string;
    lastName: string;
    email: string;
    contactNumber: string;
    referredById: string;
    cvFile: File;
    note: string;
    referredBy: { id: string, firstName: string, lastName: string };
    referredOn: Date;
    status: string;
    statusChangedOn: string;
}


export interface JobRequestDto {
    jobTitle: string,
    jobDetails: string,
    experienceYears: number,
    numberOfVaccancy: number,
    hrIds: string[],
    cvReviewerIds: string[],
    jobJdFile: File,
}