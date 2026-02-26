import { useState } from "react";

import { useNavigate, useParams } from "react-router-dom";
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
} from "../../ui/dialog";
import ProofView from "../TravelExpenses/ProofView";
import { useTravelDocumentById } from "../queries/travelDocuments.queries";

const TravelDocumentView = () => {
  const [open, setOpen] = useState(true);

  const navigate = useNavigate();
  const { id, documentId } = useParams<{ id: string; documentId: string }>();

  const { data, isLoading } = useTravelDocumentById(id, documentId);

  console.log(data);

  const handleClose = () => {
    navigate(`/travel/plans/${id}/documents`);
  };

  if (isLoading) {
    return <div> Loading... </div>;
  }
  return (
    <Dialog open={open} onOpenChange={handleClose}>
      <DialogContent className="sm:max-w-1/2 sm:w-1/2 sm:max-h-10/12 sm:h-screen/50  flex flex-col overflow-hidden">
        <DialogHeader>
          <DialogTitle>Travel Document</DialogTitle>
        </DialogHeader>
        <ProofView filePath={data?.filePath} docType={"doc"} />
      </DialogContent>
    </Dialog>
  );
};

export default TravelDocumentView;
