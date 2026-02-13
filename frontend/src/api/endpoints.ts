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
};
