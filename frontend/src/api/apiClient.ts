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
		console.log("error in api interceptoprs", error);
		if (error.response?.status === 401) {
			localStorage.removeItem("token");
			window.location.href = "/login";
		}
		return Promise.reject(error);
	},
);
