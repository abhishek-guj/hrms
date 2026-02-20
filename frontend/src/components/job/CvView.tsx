import { useState } from "react";
import { useNavigate, useOutletContext, useParams } from "react-router-dom";
import { useGetDocument } from "../shared/services/documents.queries";
import { Dialog, DialogContent, DialogHeader, DialogTitle } from "../ui/dialog";
import ProofView from "../travelPlans/TravelExpenses/ProofView";

const CVView = () => {
	const [open, setOpen] = useState(true);

	const { referId } = useParams<{ referId: string }>();

	const navigate = useNavigate();

	// context
	const { data: rawData,isLoading, error } = useOutletContext();
	const referralData = rawData?.filter((d) => `${d.id}` === referId)[0];
	const cvFile = referralData?.cvPath;
	console.log(referralData);

	const handleClose = () => {
		navigate(`/referrals`);
	};

    
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

	return (
		<Dialog open={open} onOpenChange={handleClose}>
			<DialogContent className="sm:max-w-1/2 sm:w-1/2 sm:max-h-10/12 sm:h-screen/50  flex flex-col overflow-hidden">
				<DialogHeader>
					<DialogTitle>
						CV of {referralData?.firstName} {referralData?.lastName}
					</DialogTitle>
				</DialogHeader>
				<ProofView filePath={cvFile} docType={"cv"} />
			</DialogContent>
		</Dialog>
	);
};

export default CVView;
