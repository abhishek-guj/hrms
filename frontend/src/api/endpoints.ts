export const HEALTH_ENDPOINTS = {
	checkHealth: () => "/health",
};

export const AUTH_ENDPOINTS = {
	login: () => `/auth/login`,
};

export const TRAVEL_PLAN_ENDPOINTS = {
	getAll: () => "/travel-plans",
	getById: (id: string) => `/travel-plans/${id}`,
	create: () => "/travel-plans",
	update: (id: string) => `/travel-plans/${id}`,
	delete: (id: string) => `/travel-plans/${id}`,

	getTravelEmployeesByTravelPlanId: (id: string) =>
		`/travel-plans/${id}/employees`,
	updateTravelEmployees: (id: string) => `/travel-plans/${id}/employees`,

	getTravelExpensesByTravelPlanId: (id: string) =>
		`/travel-plans/${id}/expenses`,
	//
};

export const EXPENSE_ENDPOINTS = {
	create: () => `/travel-expense`,
	getTravelExpenseById: (id: string) => `/travel-expense/${id}`,
};

export const EXPENSE_TYPES_ENDPOINTS = {
	getAll: () => "/expense-types",
};

export const DOCUMENTS_GET_ENDPOINTS = {
	getExpense: () => `/documents/expense`,
};
