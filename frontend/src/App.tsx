import "@xyflow/react/dist/style.css";
import "./App.css";

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
