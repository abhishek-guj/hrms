export interface OrgChainDto {
    employeeProfileId: string;
    firstName: string;
    lastName: string;
    currentEmployee: boolean;
    underEmployees: OrgChainDto[];
}