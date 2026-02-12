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
