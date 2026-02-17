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

	const initialNodes = [
		{
			id: "n1",
			position: { x: 0, y: 0 },
			data: { label: "Node 1" },
			type: "input",
		},
		{
			id: "n2",
			position: { x: 100, y: 100 },
			data: { label: "Node 2" },
		},
	];

	const initialEdges = [
		{
			id: "n1-n2",
			source: "n1",
			target: "n2",
		},
	];
	return (
		<div className="flex flex-1 flex-col gap-4 p-4">
			<ReactFlow nodes={initialNodes} edges={initialEdges}>
				<Background />
				<Controls />
			</ReactFlow>
		</div>
	);
}

export default App;
