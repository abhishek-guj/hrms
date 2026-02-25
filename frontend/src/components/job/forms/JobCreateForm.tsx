import { Plus } from "lucide-react";
import { useForm, type SubmitHandler } from "react-hook-form";
import { FieldInput } from "../../game/forms/BookGameSlotForm";
import { Button } from "../../ui/button";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "../../ui/dialog";
import { Field, FieldError, FieldLabel, FieldSet } from "../../ui/field";
import { zodResolver } from "@hookform/resolvers/zod";
import { JobCreateSchema, type JobCreateSchemaType } from "../../login/schema";
import { Input } from "../../ui/input";

const JobCreate = () => {
  return (
    <Dialog>
      <DialogTrigger asChild>
        <Button size="sm" className="flex items-center gap-1.5">
          <Plus className="h-4 w-4" />
          New
        </Button>
      </DialogTrigger>
      <DialogContent className="sm:min-w-1/2 max-w-fit">
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

export const JobCreateForm = () => {
  // react form
  const {
    register,
    handleSubmit,
    formState: { errors },
    control,
  } = useForm<JobCreateSchemaType>({
    mode: "all",
    resolver: zodResolver(JobCreateSchema),
  });
  // react form

  const onSubmit: SubmitHandler<JobCreateSchemaType> = async (data) => {
    // setLoading(true);
    // const resData = await referJob.mutateAsync({ jobId: jobId, payload: data });
    // setLoading(false);
    // navigate(`/jobs`);
  };

  return (
    <form onSubmit={handleSubmit(onSubmit)} className="flex flex-col gap-4">
      <FieldSet>
        <FieldInput
          displayName="Job Title"
          errors={errors.jobTitle}
          name="jobTitle"
          register={register}
        />
        <FieldInput
          displayName="Job Details"
          errors={errors.jobDetails}
          name="jobDetails"
          register={register}
        />
      </FieldSet>
      <FieldSet className="flex flex-row">
        <FieldInput
          displayName="Experience Years"
          errors={errors.experienceYears}
          name="experienceYears"
          register={register}
          type="number"
          max={60}
        />
        <FieldInput
          displayName="Number Of Vaccancy"
          errors={errors.numberOfVaccancy}
          name="numberOfVaccancy"
          register={register}
          type="number"
        />
      </FieldSet>
      <FieldSet>
        <FieldInput
          displayName="HR"
          errors={errors.hrIds}
          name="hrIds"
          register={register}
        />
        <FieldInput
          displayName="CV Reviewer"
          errors={errors.cvReviewerIds}
          name="cvReviewerIds"
          register={register}
        />
      </FieldSet>
      <FieldSet>
        <Field>
          <FieldLabel htmlFor="jobJdFile">JD File</FieldLabel>
          <Input id="jobJdFile" type="file" {...register("jobJdFile")} />
          {errors.jobJdFile && <FieldError errors={[errors.jobJdFile]} />}
        </Field>
        <Button variant={"default"}>Save</Button>
      </FieldSet>
    </form>
  );
};
