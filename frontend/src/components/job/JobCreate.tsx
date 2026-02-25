import { Pencil, Plus, Trash2 } from "lucide-react";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Button } from "../ui/button";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "../ui/dialog";
import { JobCreateForm } from "./forms/JobCreateForm";
import JobUpdateForm from "./forms/JobUpdateForm";
import { useDeleteJob, useJobsById } from "./queries/job.queries";

const JobCreate = () => {
  return (
    <Dialog>
      <DialogTrigger asChild>
        <Button size="sm" className="flex items-center gap-1.5">
          <Plus className="h-4 w-4" />
          New
        </Button>
      </DialogTrigger>
      <DialogContent className="sm:min-w-4/5 max-w-fit">
        <DialogHeader>
          <DialogTitle>Create Job</DialogTitle>
          <DialogDescription>Create a job......</DialogDescription>
        </DialogHeader>
        <JobCreateForm />
      </DialogContent>
    </Dialog>
  );
};

export default JobCreate;

export const JobUpdate = ({ jobId }: { jobId: { id: string } }) => {
  const { data } = useJobsById(jobId.id);
  return (
    <Dialog>
      <DialogTrigger asChild>
        <Button
          size="sm"
          className="flex items-center gap-1.5"
          variant={"outline"}
          asChild
        >
          <span className="w-fit">
            <Pencil className="h-4 w-4" />
            <span className="hidden lg:block">Edit</span>
          </span>
        </Button>
      </DialogTrigger>
      <DialogContent className="sm:min-w-4/5 max-w-fit">
        <DialogHeader>
          <DialogTitle>Update Job</DialogTitle>
          <DialogDescription>Update job......</DialogDescription>
        </DialogHeader>
        <JobUpdateForm jobId={jobId?.id} data={data} />
      </DialogContent>
    </Dialog>
  );
};

export const JobDelete = ({
  jobId,
}: {
  jobId: { id: string; title: string };
}) => {
  const [open, setOpen] = useState(false);
  const deleteTravelPlan = useDeleteJob();

  const navigate = useNavigate();

  // handlers
  const handleDelete = async () => {
    await deleteTravelPlan.mutateAsync({ id: jobId?.id });
    setOpen(false);
    navigate(`/jobs`);
  };

  if (jobId?.id == undefined || !jobId?.id) {
    return <div>Loading....</div>;
  }
  return (
    <Dialog open={open} onOpenChange={setOpen}>
      <DialogTrigger asChild>
        <Button
          size="sm"
          className="flex items-center gap-1.5"
          variant={"outline"}
          asChild
        >
          <span className="w-fit">
            <Trash2 className="h-4 w-4" />
            <span className="hidden lg:block">Delete</span>
          </span>
        </Button>
      </DialogTrigger>
      <DialogContent className="sm:max-w-fit">
        <DialogHeader>
          <DialogTitle>Delete Job</DialogTitle>
          <DialogDescription>
            Are you sure you want to delete this job?
            <br />
            {jobId?.title}
          </DialogDescription>
        </DialogHeader>
        <div className="flex w-full justify-end">
          <Button onClick={handleDelete}>Delete</Button>
        </div>
      </DialogContent>
    </Dialog>
  );
};
