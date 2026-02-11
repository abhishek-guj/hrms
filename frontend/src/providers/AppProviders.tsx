import type React from "react";
import queryClient from "@/api/queryClient";
import { store } from "@/store";
import { QueryClientProvider } from "@tanstack/react-query";
import { Provider } from "react-redux";
import ToastProvider from "./ToastProvider";

const AppProviders = ({ children }: { children: React.ReactNode }) => {
	return (
		<>
			<Provider store={store}>
				<QueryClientProvider client={queryClient}>
					{children}
				</QueryClientProvider>
			</Provider>
			<ToastProvider />
		</>
	);
};

export default AppProviders;
