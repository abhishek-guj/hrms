export const ROLE_ENDPOINTS = {
	getAll: () => "/roles",
};
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

export const TRAVEL_DOCUMENTS_ENDPOINTS = {
	create: (id: string) => `/travel-plans/${id}/documents`,
	delete: (id: string, docId: string) => `/travel-plans/${id}/documents/${docId}`,
	getByTravelId: (id: string) => `/travel-plans/${id}/documents`,
	getByDocId: (id: string, docId: string) => `/travel-plans/${id}/documents/${docId}`,
	getDocumentTypes: () => `/travel-document-types`
}

export const EXPENSE_ENDPOINTS = {
	create: () => `/travel-expense`,
	delete: (id: string) => `/travel-expense/${id}`,
	// getTravelExpenseById: (id: string) => `/travel-expense/${id}`,
};

export const EXPENSE_TYPES_ENDPOINTS = {
	getAll: () => "/expense-types",
};

export const DOCUMENTS_GET_ENDPOINTS = {
	getDocument: (type: string) => `/documents/${type}`,
	getExpense: () => `/documents/expense`,
	getJobJd: () => `/documents/job`
};

export const ORGCHART_ENDPOINTS = {
	getMyOrg: () => "/org",
};


export const JOB_ENDPOINTS = {
	getAll: () => "/jobs",
	create: () => `/jobs`,
	update: (id: string) => `/jobs/${id}`,
	getById: (id: string) => `/jobs/${id}`,
	delete: (id: string) => `/jobs/${id}`,
	share: (id: string) => `jobs/${id}/share`,
	refer: (id: string) => `jobs/${id}/refer`,
	getAllRefers: () => `/jobs/referrals`,
	updateStatus: (id: string) => `/jobs/referrals/${id}`

}


export const GAME_ENDPOINTS = {
	getAllSlots: () => `/games/slots`,
	getAllSlotBookings: () => `/games/slots/bookings`,
	getSlotDetails: (slotId: string) => `/games/slots/${slotId}`,
	bookSlot: (slotId: string) => `games/slots/${slotId}`,
	cancelBooking: (slotBookingId: string) => `/games/slots/bookings/${slotBookingId}`,
	createGame: () => `/games`,
	updateGame: (gameId: string) => `/games/${gameId}`,
	deleteGame: (gameId: string) => `/games/${gameId}`,
	getGames: () => `/games`,
}

export const ACHIEVEMENT_ENDPOINTS = {
	getFeed: (params?: string) => `/achievements${params ? `?${params}` : ""}`,
	getById: (id: number) => `/achievements/${id}`,
	create: () => `/achievements`,
	update: (id: number) => `/achievements/${id}`,
	delete: (id: number, reason?: string) =>
		`/achievements/${id}${reason ? `?reason=${encodeURIComponent(reason)}` : ""}`,
	toggleLike: (id: number) => `/achievements/${id}/like`,
	addComment: (postId: number) => `/achievements/${postId}/comments`,
	editComment: (commentId: number) => `/achievements/comments/${commentId}`,
	deleteComment: (commentId: number, reason?: string) =>
		`/achievements/comments/${commentId}${reason ? `?reason=${encodeURIComponent(reason)}` : ""}`,
};


export const NOTIFICATION_ENDPOINTS = {
	getAll: () => '/notifications'
}


export const EMPLOYEES = {
	getAll: () => `/employees`
}