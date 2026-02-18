import { useEffect, useState } from "react";
import reactLogo from "./assets/react.svg";
import viteLogo from "/vite.svg";
import "./App.css";
import { useTravelPlans } from "./components/travelPlans/queries/travelPlans.queries";
import { ReactFlow, Background, Controls } from "@xyflow/react";
import "@xyflow/react/dist/style.css";

function App() {
	const error = false;
	const isLoading = false;
	if (error) {
		return <div>{error.message}</div>;
	}
	if (isLoading) {
		return <div>Loading....</div>;
	}
	return <div className="flex flex-1 flex-col gap-4 p-4">Hello</div>;
}

export default App;
