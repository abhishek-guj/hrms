import React from "react";
import { useJobsById } from "../../components/job/queries/job.queries";
import { useParams } from "react-router-dom";
import JobViewCard from "../../components/job/JobViewCard";

const JobPublicPage = () => {
	const { jobId } = useParams<{ jobId: string }>();

	const { data, isLoading, error } = useJobsById(jobId!);
	console.log(data);
	return (
		<div className="grid place-content-center p-28">
			<div className="border rounded-2xl">
				<JobViewCard />
			</div>
		</div>
	);
};

export default JobPublicPage;
