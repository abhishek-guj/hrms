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
  return (
    <div className="flex flex-1 flex-col gap-4 p-4">
      {/* <div className="grid auto-rows-min gap-4 md:grid-cols-3">
        <div className="bg-red-100 aspect-video rounded-xl" />
        <div className="bg-green-100 aspect-video rounded-xl" />
        <div className="bg-blue-100 aspect-video rounded-xl" />
      </div>
      <div className="bg-neutral-200 min-h-[100vh] flex-1 rounded-xl md:min-h-min" /> */}
    </div>
  );
}

export default App;
