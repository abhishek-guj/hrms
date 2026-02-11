import { createSlice } from "@reduxjs/toolkit";

interface AuthSlice {
	isAuthenticated: boolean;
}

const initialState: AuthSlice = {
	isAuthenticated: false,
};

const authSlice = createSlice({
	name: "auth",
	initialState,
	reducers: {
		authenticate(state) {
			state.isAuthenticated = true;
		},
	},
});

export const authActions = authSlice.actions;
export const authReducer = authSlice.reducer;
