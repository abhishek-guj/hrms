import { createSlice, type PayloadAction } from "@reduxjs/toolkit";
import { jwtDecode } from "jwt-decode";

interface AuthSlice {
	isAuthenticated: boolean;
	token: string;
	role: string;
	employeeId: number;
}

const initialState: AuthSlice = {
	isAuthenticated: false,
	token: "",
	role: "",
	employeeId: Number.NaN,
};

const authSlice = createSlice({
	name: "auth",
	initialState,
	reducers: {
		authenticate(state, action: PayloadAction<string>) {
			try {
				if (action.payload.status !== "SUCCESS") {
					console.error("error in login");
					return;
				}

				const data = action.payload.data;
				const token = data.token;
				const decoded = jwtDecode(token);

				state.isAuthenticated = true;
				state.role = decoded.role;
				state.token = decoded.token;
				state.employeeId = data.employeeId
				// console.log("authSlice", { ...state })
			} catch (ex) {
				console.error(ex);
			}
		},
	},
});

export const authActions = authSlice.actions;
export const authReducer = authSlice.reducer;
