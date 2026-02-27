import { useMyOrgChart } from "../../components/orgchart/org.queries";
import { showError } from "../../components/ui/toast";

import { Background, Controls, MarkerType, ReactFlow } from "@xyflow/react";
import "@xyflow/react/dist/style.css";

const OrgChartPage = () => {
	const { data, isLoading, error } = useMyOrgChart();

	const edges = [];

	const nodes = createNodes(data, edges);

	createLastLevelNodes(data, edges, nodes);

	if (error) {
		showError(error.message);
		return <div>Can't Load Data at the moment...</div>;
	}
	if (isLoading) {
		return <div>Loading....</div>;
	}

	return (
		<div className="flex flex-1 flex-col gap-4 p-4">
			<ReactFlow
				nodes={nodes}
				edges={edges}
				fitView
			>
				<Background />
				<Controls />
			</ReactFlow>
		</div>
	);
};

export default OrgChartPage;

// helper functions in creating nodes
const createNodes = (data, edges) => {
	if (data) {
		// decide where to start x position
		let countx =
			(data[0]?.underEmployees?.length / 2) * 100 +
			(data[0]?.underEmployees?.length % 2 == 0
				? data[0]?.underEmployees?.length == 2
					? 0
					: 100
				: 50);

		// deciding where to start from below, as the list is always reversed and not able to .reverse()
		let county = 100 * data?.length + 100;
		return data?.map((d, idx) => {
			countx += 0;
			county -= 100;

			if (data[idx + 1]) {
				// adding edges of all nodes in hirarchy
				edges.push({
					id: `${d.employeeProfileId}-${data[idx + 1]?.employeeProfileId}`,
					// source: `${d.employeeProfileId}`,
					// target: `${data[idx + 1]?.employeeProfileId}`,
					source: `${data[idx + 1]?.employeeProfileId}`,
					target: `${d.employeeProfileId}`,
					markerEnd: {
						type: MarkerType.Arrow,
						width: 15,
						height: 15,
						color: "#3E9E34",
					},
					style: {
						strokeWidth: 2,
						stroke: "#3E9E34",
					},
				});
			}

			// returning nodes as array
			// todo: later create make it add in list like edges
			return {
				id: `${d.employeeProfileId}`,
				data: {
					label: `${d.firstName} ${d.lastName}`,
				},
				position: { x: countx, y: county },
				style: {
					background: d.currentEmployee === true ? "#3E9E34" : "",
				},
			};
		});
	}
};

const createLastLevelNodes = (data, edges, nodes) => {
	// this for creating direct reporting employee nodes to the current logged in user

	if (data) {

		let countx = 0;
		let county = data.length * 100 + 100;
		data[0].underEmployees?.forEach((d, idx) => {
			// adding edges
			edges.push({
				id: `${data[0].employeeProfileId}-${d?.employeeProfileId}`,
				source: `${data[0].employeeProfileId}`,
				target: `${d.employeeProfileId}`,
				markerEnd: {
					type: MarkerType.Arrow,
					width: 15,
					height: 15,
					color: "#3E9E34",
				},
				style: {
					strokeWidth: 2,
					stroke: "#3E9E34",
				},
			});

			// adding nodes
			nodes?.push({
				id: `${d.employeeProfileId}`,
				data: {
					label: `${d.firstName} ${d.lastName}`,
				},
				position: { x: countx, y: county },
			});
			countx += 200;
		});
	}
};
