import { format } from "date-fns";
import { useParams } from "react-router-dom";
import { Separator } from "../../ui/separator";
import { useTravelExpenseById } from "../queries/travelPlans.queries";
import PdfViewer from "../../PdfViewer";

import { ScrollArea } from "@/components/ui/scroll-area";
import ProofView from "./ProofView";

const ExpenseViewCard = () => {
	const { expenseId } = useParams<{ expenseId: string }>();

	const { data, isLoading, error } = useTravelExpenseById(expenseId!);

	if (isLoading) {
		return (
			<div className="p-4 px-8 flex flex-col min-w-96 min-h-96 justify-center items-center">
				Loading
			</div>
		);
	}
	if (error) {
		return (
			<div className="p-4 px-8 flex flex-col min-w-96 min-h-96 justify-center items-center">
				No Data Found...
			</div>
		);
	}

	console.log(data, error);
	return (
		// <Card>
		// 	<CardContent>
		<div className="p-4 px-8 flex flex-col overflow-y-scroll">
			<div>
				<div className="grid grid-cols-2 space-x-10">
					<div>
						<div className="text-black/50 text-sm my-1">Name</div>
						<div className="p-1">
							{data?.submittedBy.firstName} {data?.submittedBy.lastName}
						</div>
					</div>
					<div>
						<div className="text-black/50 text-sm my-1">Expense Type</div>
						<div className="p-1">{data?.expenseType.name}</div>
					</div>
				</div>

				<Separator />
				{/* dates */}

				<div className="grid grid-cols-3">
					<div>
						<div className="text-black/50 text-sm my-1">Amount</div>
						<div className="p-1">{data?.expenseAmount}</div>
					</div>
					<div>
						<div className="text-black/50 text-sm my-1">Expense Date</div>
						<div className="p-1 w-fit">{data?.expenseDate as string}</div>
					</div>
					<div>
						<div className="text-black/50 text-sm my-1">Upload Date</div>
						<div className="p-1">{data?.expenseUploadDate}</div>
					</div>
				</div>

				<Separator />

				<div className="grid grid-cols-3 gap-5">
					<div>
						<div className="text-black/50 text-sm my-1">Status</div>
						<div className="p-1">{data?.status}</div>
					</div>
					<div>
						<div className="text-black/50 text-sm my-1">Status Changed On</div>
						<div className="p-1">
							{format(
								data?.statusChangedOn?.toString() ?? "2026-01-01",
								"dd/MM/yyyy",
							)}
						</div>
					</div>
					<div>
						<div className="text-black/50 text-sm my-1">Status Changed By</div>
						<div className="p-1">
							{data?.statusChangedBy.firstName} {data?.statusChangedBy.lastName}
						</div>
					</div>
				</div>
				<Separator />
				<div className="">
					<div className="text-black/50 text-sm my-1">Proof Files</div>
					<div className="grid grid-cols-1 max-h-60">
						{data?.expenseDocumentFilePaths?.length === 0 && (
							<div>No Proofs there....</div>
						)}
						{data?.expenseDocumentFilePaths?.map((filePath, idx) => (
							<>
								<div>Proof {idx + 1}</div>
								<div key={filePath} className="mb-10">
									<ProofView filePath={filePath} docType={"expense"} />
								</div>
							</>
						))}
					</div>
				</div>
			</div>
		</div>

		// 	</CardContent>
		// </Card>
	);
};

export default ExpenseViewCard;
