import { useEffect, useState } from "react";
import reactLogo from "./assets/react.svg";
import viteLogo from "/vite.svg";
import "./App.css";
import { useTravelPlans } from "./components/travelPlans/queries/travelPlans.queries";

function App() {
	const { data, isLoading, error } = useTravelPlans();

	if (error) {
		return <div>{error.message}</div>;
	}
	if (isLoading) {
		return <div>Loading....</div>;
	}
	return <div className="flex flex-col gap-2">Done</div>;
}

export default App;
