import { format } from "date-fns";
import { useParams } from "react-router-dom";
import { Separator } from "../ui/separator";
import { useJobsById } from "./queries/job.queries";
import { Badge } from "../ui/badge";

const JobViewCard = () => {
	const { jobId } = useParams<{ jobId: string }>();

	const { data, isLoading, error } = useJobsById(jobId!);

	// id: string;
	// jobTitle: string;
	// jobDetails: string;
	// experienceYears: number;
	// numberOfVaccancy: number;
	// createdBy: EmployeeProfileDto;
	// createdOn: string;
	// updatedBy: EmployeeProfileDto;
	// updatedOn: string;
	// status: string; // active or close
	// statusChangedOn: string;

	console.log(data);
	return (
		<div className="p-4 px-4 flex flex-col w-fit gap-2">
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
			<div className="">
				<div className="text-black/50 text-sm my-1">Job Description Pdf</div>
				<div className="grid grid-cols-1 max-h-60">
					{/* {data?.expenseDocumentFilePaths?.length === 0 && (
						<div>No Proofs there....</div>
					)}
					{data?.expenseDocumentFilePaths?.map((filePath, idx) => (
						<>
							<div>Proof {idx + 1}</div>
							<div key={filePath} className="border border-red-800 mb-10">
								<ProofView filePath={filePath} />
							</div>
						</>
					))} */}
					JD FILE
				</div>
			</div>
		</div>
	);
};

export default JobViewCard;
