import { useNavigate, useParams } from "react-router-dom";
import { Dialog, DialogContent, DialogHeader, DialogTitle } from "../ui/dialog";
import JobViewCard from "./JobViewCard";
import { useAllReferrals } from "./queries/job.queries";
import { useState } from "react";

const JobView = () => {
  const [open, setOpen] = useState(true);

  const navigate = useNavigate();
  const { jobId } = useParams<{ jobId: string }>();

  const handleClose = () => {
    navigate(`/jobs`);
  };

  return (
    <Dialog open={open} onOpenChange={handleClose}>
      <DialogContent className="max-w-1/2 w-1/2 max-h-10/12 h-screen/50 flex flex-col overflow-hidden">
        <DialogHeader>
          <DialogTitle>View Job Details</DialogTitle>
        </DialogHeader>
        <JobViewCard />
      </DialogContent>
    </Dialog>
  );
};

export default JobView;
