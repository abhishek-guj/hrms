import { Outlet } from "react-router-dom";
import JobsViewTable from "../../components/job/JobsViewTable";
import {
	Card,
	CardContent,
	CardHeader,
	CardTitle,
} from "../../components/ui/card";

const JobsPage = () => {
	return (
		<div>
			<Card className="border-none shadow-none max-h-full h-full w-full p-8">
				<CardHeader>
					<CardTitle>Jobs</CardTitle>
				</CardHeader>
				<CardContent className="flex flex-col gap-1">
					<JobsViewTable />
				</CardContent>
			</Card>
			<Outlet />
		</div>
	);
};

export default JobsPage;
