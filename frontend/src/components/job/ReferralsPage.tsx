import React from "react";
import ReferralsViewTable from "./ReferralsViewTable";
import { Outlet } from "react-router-dom";
import { useAllReferrals } from "./queries/job.queries";

const ReferralsPage = () => {
	const { data, isLoading, error } = useAllReferrals();

	return (
		<div className="p-8 flex flex-col gap-8">
			<h2 className="text-2xl font-bold">Referrals</h2>
			<ReferralsViewTable data={data} />
			<Outlet context={{ data: data, isLoading: isLoading, error: error }} />
		</div>
	);
};

export default ReferralsPage;
