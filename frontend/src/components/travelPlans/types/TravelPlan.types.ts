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
