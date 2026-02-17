export type DataTableStatus =
	| "completed"
	| "pending"
	| "processing"
	| "cancelled";

export interface DataTabelItem {
	id: string;
	name: string;
	date: string;
	status: DataTableStatus;
	amount: string;
}

export interface TravelTypeDto {
	id: number;
	name: string;
}

export interface TravelPlanDto {
	id: number;
	purpose: string;
	travelType: TravelTypeDto;
	startDate: string;
	endDate: string;
	destinationCity: string;
	destinationState: string;
	destinationCountry: string;
	lastDateOfExpenseSubmission: string;
	maxAmountPerDay: number;
}

export interface TravelPlanCreateDto {
	purpose: string;
	travelTypeId: number;
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
	travelTypeId: number;
	startDate: string;
	endDate: string;
	destinationCity: string;
	destinationState: string;
	destinationCountry: string;
	lastDateOfExpenseSubmission: string;
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
