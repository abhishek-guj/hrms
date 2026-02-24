import axios from "axios";

const PUBLIC_ROUTES = [
	"/login",
	"/jobs/public",
]


export const api = axios.create({
	baseURL: import.meta.env.VITE_API_URL,
	headers: {
		"Content-Type": "application/json",
	},
});

api.interceptors.request.use((config) => {
	const isPublic = PUBLIC_ROUTES.some(url => config.url?.includes(url))

	const token = localStorage.getItem("token");
	if (token && !isPublic) {
		config.headers.Authorization = `Bearer ${token}`;
	}
	return config;
});

api.interceptors.response.use(
	(response) => {
		if (response.data.status == "ERROR") {
			if (response.data.message?.includes("not found")) {
				window.location.href = "/error"
			}
		}
		return response;


	},
	(error) => {
		let message = "An error occurred."

		if (error.response) {
			const status = error.response.status;
			const data = error.response.data;
			message = data?.message || `Request failed with status ${status}`;
		}
		else if (error.request) {
			message = 'No response from server. Please check your network.';
		}
		else {
			message = error.message || 'Request setup error';
		}
		return Promise.reject({
			message: message,
		});
	},
);
