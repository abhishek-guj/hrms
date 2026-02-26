import { zodResolver } from "@hookform/resolvers/zod";
import { useEffect, useState } from "react";
import { Controller, useForm, type SubmitHandler } from "react-hook-form";
import { FieldInput } from "../../game/forms/BookGameSlotForm";
import {
  JobCreateSchema,
  JobUpdateSchema,
  type JobCreateSchemaType,
  type JobUpdateSchemaType,
} from "../../login/schema";
import EmployeeSelect from "../../travelPlans/TravelExpenses/forms/EmployeeSelect";
import { Button } from "../../ui/button";
import { Field, FieldError, FieldLabel, FieldSet } from "../../ui/field";
import { Input } from "../../ui/input";
import {
  useCreateJob,
  useJobsById,
  useUpdateJob,
} from "../queries/job.queries";

export const JobUpdateForm = ({ jobId, data }) => {
  console.log(data);

  const [selectedHr, setSelectedHr] = useState(data?.hrIds ?? []);
  const [selectedCV, setSelectedCV] = useState(data?.cvReviewerIds ?? []);

  const newData = { ...data };

  // react form
  const {
    register,
    handleSubmit,
    formState: { errors },
    control,
    reset,
    watch,
  } = useForm<JobUpdateSchemaType>({
    mode: "all",
    defaultValues: { ...newData },
    resolver: zodResolver(JobUpdateSchema),
  });
  // react form

  // putting data after fetching
  useEffect(() => {
    reset({ ...newData });
  }, []);

  //
  const updateJob = useUpdateJob();

  const onSubmit: SubmitHandler<JobUpdateSchemaType> = async (data) => {
    console.log("submit", data);
    await updateJob.mutateAsync({ id: jobId, dto: data });
  };

  // watch
  useEffect(() => {
    reset({ hrIds: selectedHr, cvReviewerIds: selectedCV });
  }, [selectedHr, selectedCV]);

  return (
    <form
      onSubmit={handleSubmit(onSubmit)}
      className="flex flex-col gap-4 w-full"
    >
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
      <FieldSet className="flex flex-row">
        <Controller
          name="hrIds"
          control={control}
          render={({ field, fieldState }) => (
            <EmployeeSelect
              name={"hrIds"}
              value={field.value || []}
              onValueChange={field.onChange}
              multiSelectValues={selectedHr}
              multiSelectValuesChange={setSelectedHr}
              errors={errors.hrIds}
              displayName={"HRs"}
              type={null}
            />
          )}
        />
        <Controller
          name="cvReviewerIds"
          control={control}
          render={({ field, fieldState }) => (
            <EmployeeSelect
              name={"cvReviewerIds"}
              value={field.value || []}
              onValueChange={field.onChange}
              multiSelectValues={selectedCV}
              multiSelectValuesChange={setSelectedCV}
              errors={errors.cvReviewerIds}
              displayName={"CV Reviewers"}
              type={null}
            />
          )}
        />
        {console.log(errors)}
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

export default JobUpdateForm;
