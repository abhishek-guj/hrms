import type { DataTableStatus } from "../../shared/shared.types";


export interface DataTabelItem {
	id: string;
	name: string;
	date: string;
	status: DataTableStatus;
	amount: string;
}

export interface TravelTypeDto {
	id: string;
	name: string;
}

export interface TravelPlanDto {
	id: number;
	purpose: string;
	travelType: TravelTypeDto;
	startDate: Date;
	endDate: Date;
	destinationCity: string;
	destinationState: string;
	destinationCountry: string;
	lastDateOfExpenseSubmission: Date;
	maxAmountPerDay: number;
}

export interface TravelPlanCreateDto {
	purpose: string;
	travelTypeId: string;
	startDate: string;
	endDate: string;
	destinationCity: string;
	destinationState: string;
	destinationCountry: string;
	lastDateOfExpenseSubmission: string;
	maxAmountPerDay: number;
}

export interface TravelPlanUpdateDto {
	purpose: string;
	travelTypeId: string;
	startDate: Date;
	endDate: Date;
	destinationCity: string;
	destinationState: string;
	destinationCountry: string;
	lastDateOfExpenseSubmission: Date;
	maxAmountPerDay: number;
}

export interface TravelEmployeeDto {
	id: number;
	firstName: string;
	lastName: string;
}

// export interface EmployeeDto {}

export interface TravelExpenseDto {
	id: number;
	travelPlan: ExpenseTravelPlanDto;
	submittedBy: ExpenseEmployeeProfileDto;
	submitStatus: boolean;
	expenseUploadDate: string;
	expenseType: ExpenseExpenseTypeDto;
	expenseAmount: number;
	expenseDate: string;
	status: string;
	remark: string;
	statusChangedOn: string;
	statusChangedBy: ExpenseEmployeeProfileDto;
	expenseDocumentFilePaths: string[];
}

interface ExpenseTravelPlanDto {
	id: number;
	purpose: string;
}

interface ExpenseEmployeeProfileDto {
	id: number;
	firstName: string;
	lastName: string;
	managerId: number;
}

interface ExpenseExpenseTypeDto {
	id: number;
	name: string;
}

export interface ExpenseTypeDto {
	id: string;
	name: string;
}

export interface TravelExpenseRequestDto {
	travelPlanId: string;
	submittedById: string;
	expenseTypeId: string;
	submitStatus: string;
	expenseUploadDate: string;
	expenseAmount: number;
	expenseDate: Date;
	status: string;
	remark: string;
	files: File[];
}



export interface TravelDocumentCreateDto {
	travelPlanId: string;
	uploadedForEmployeeId: string;
	documentTypeId: string;
	file: File;
}

export interface TravelDocument {
	id: string,
	travelPlanId: string,
	travelPlanPurpose: string,
	ownerType: string,
	uploadedById: string,
	uploadedByName: string,
	uploadedForId: string,
	uploadedForName: string,
	uploadDate: Date,
	documentTypeId: string,
	documentTypeName: string,
	filePath: string
}