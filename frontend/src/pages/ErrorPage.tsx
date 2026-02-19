import React from "react";
import { NavLink, useNavigate } from "react-router-dom";
import { Button } from "../components/ui/button";
import { Handle } from "@xyflow/react";

const ErrorPage = () => {
	const navigate = useNavigate();
	const handleOnClick = () => {
		navigate("/");
	};
	return (
		<div className="container text-red-600 w-screen h-screen flex justify-center">
			<div className="flex flex-col items-center justify-center w-screen gap-10">
				<div className="text-6xl">404 Error: Page Not Found</div>
				<Button
					variant={"ghost"}
					className="text-2xl border-red-500 border p-4 rounded-2xl hover:bg-red-500 hover:text-white"
					asChild
					onClick={handleOnClick}
				>
					<div>{"<-"} Back</div>
				</Button>
			</div>
		</div>
	);
};

export default ErrorPage;
