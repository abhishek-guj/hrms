import { createSelector } from "@reduxjs/toolkit";
import type { RootState } from "..";

export const selectAuthState = (state: RootState) => state.auth;

export const selectIsAuthenticated = createSelector(
	[selectAuthState],
	(auth) => auth.isAuthenticated,
);


