import { format } from "date-fns";
import { useParams } from "react-router-dom";
import ProofView from "../travelPlans/TravelExpenses/ProofView";
import { Badge } from "../ui/badge";
import { Separator } from "../ui/separator";
import { useJobsById } from "./queries/job.queries";

const JobViewCard = () => {
	const { jobId } = useParams<{ jobId: string }>();

	const { data, isLoading, error } = useJobsById(jobId!);

	return (
		<div className="p-4 px-4 flex flex-col w-full gap-2 overflow-y-auto">
			<div className="grid grid-cols-2 space-x-10">
				<div>
					<div className="text-black/50 text-sm my-1">Job Id</div>
					<div className="p-1">{data?.id}</div>
				</div>
				<div>
					<div className="text-black/50 text-sm my-1">Job Title</div>
					<div className="p-1">{data?.jobTitle}</div>
				</div>
			</div>
			<Separator />
			<div className="grid grid-cols-1">
				<div>
					<div className="text-black/50 text-sm my-1">Description</div>
					<div className="p-1">{data?.jobDetails}</div>
				</div>
			</div>
			<Separator />
			<div className="grid grid-cols-2 gap-5">
				<div>
					<div className="text-black/50 text-sm my-1">Exp. Req.</div>
					<div className="p-1">{data?.experienceYears}</div>
				</div>
				<div>
					<div className="text-black/50 text-sm my-1">No. Of Vaccancies</div>
					<div className="p-1">{data?.numberOfVaccancy}</div>
				</div>
			</div>
			<Separator />
			<div className="grid grid-cols-2 gap-5">
				<div>
					<div className="text-black/50 text-sm my-1">Status Changed On</div>
					<div className="grid grid-cols-1 max-h-60">
						{data &&
							format(data?.statusChangedOn?.toString(), "dd/MM/yyyy HH:mm:ss")}
					</div>
				</div>
				<div>
					<div className="text-black/50 text-sm my-1">Status</div>
					<Badge className="text-base bg-emerald-500/15 text-emerald-700 dark:bg-emerald-500/10 dark:text-emerald-400">
						{data?.status.toUpperCase()}
					</Badge>
				</div>
			</div>
			<Separator />
			<div className="text-black/50 text-sm my-1">Job Description Pdf</div>
			{/* <div className="grid grid-cols-1"> */}
			{!data?.jobJdFile && <div>No Proofs there....</div>}
			{data?.jobJdFile && (
				<div className="h-96 p-0">
					<ProofView filePath={data?.jobJdFile?.filePath} docType={"job"} />
				</div>
			)}
		</div>
	);
};

export default JobViewCard;
