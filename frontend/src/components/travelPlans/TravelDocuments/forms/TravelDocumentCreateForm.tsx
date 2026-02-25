import { Button } from "@/components/ui/button";

import { Field, FieldGroup, FieldLabel } from "@/components/ui/field";
import { Input } from "@/components/ui/input";

import { zodResolver } from "@hookform/resolvers/zod";
import { Controller, useForm, type SubmitHandler } from "react-hook-form";
import { useNavigate, useParams } from "react-router-dom";
import {
  TravelDocumentSchema,
  type TravelDocumentSchemaType,
} from "../../../login/schema";
import { FieldError, FieldSet } from "../../../ui/field";
import {
  useCreateTravelDocument,
  useTravelDocumentTypes,
} from "../../queries/travelDocuments.queries";
import FormSelect from "../../TravelExpenses/forms/FormSelect";
import AssignEmloyeeSelect from "../../TravelplanDashboard/forms/AssignEmloyeeSelect";
import type { TravelDocumentCreateDto } from "../../types/TravelPlan.types";

const TravelDocumentCreateForm = () => {
  // navigate
  const navigate = useNavigate();
  // navigate

  const { id } = useParams<{ id: string }>();

  // query hooks
  const create = useCreateTravelDocument();
  const { data: docTypes } = useTravelDocumentTypes();
  // query hooks

  // react form
  const {
    register,
    handleSubmit,
    formState: { errors },
    control,
  } = useForm<TravelDocumentSchemaType>({
    mode: "onBlur",
    defaultValues: {
      travelPlanId: id,
      uploadedForEmployeeId: "",
      documentTypeId: "",
    },
    resolver: zodResolver(TravelDocumentSchema),
  });
  // react form

  // handlers
  const onSubmit: SubmitHandler<TravelDocumentCreateDto> = async (data) => {
    console.log("asasa", data);
    const resData = await create.mutateAsync({ id: id, payload: data });
    navigate(`/travel/plans/${id}/documents`);
  };
  // handlers

  //
  return (
    <form onSubmit={handleSubmit(onSubmit)}>
      <FieldGroup>
        <FieldSet className="grid grid-cols-2">
          <Field>
            <FieldLabel htmlFor="uploadedForEmployeeId">Upload For</FieldLabel>
            <Controller
              name="uploadedForEmployeeId"
              control={control}
              render={({ field, fieldState }) => (
                <MultiSelect
                  options={ROLES}
                  value={value ? value : []}
                  onChange={onChange}
                  labelledBy="Select"
                  disableSearch
                  hasSelectAll={false}
                />
              )}
            />
            {errors.uploadedForEmployeeId && (
              <FieldError errors={[errors.uploadedForEmployeeId]} />
            )}
          </Field>
        </FieldSet>
        <FieldSet>
          <Field>
            <FieldLabel htmlFor="documentTypeId">Document Type</FieldLabel>
            <Controller
              name="documentTypeId"
              control={control}
              render={({ field, fieldState }) => (
                <FormSelect
                  type={"travelDocument"}
                  data={docTypes}
                  name={"documentTypeId"}
                  value={field.value}
                  onValueChange={field.onChange}
                />
              )}
            />
            {errors.documentTypeId && (
              <FieldError errors={[errors.documentTypeId]} />
            )}
          </Field>
        </FieldSet>

        <FieldSet>
          <Field>
            <FieldLabel htmlFor="file">Travel Document File</FieldLabel>
            <Input id="file" type="file" single {...register("file")} />
            {console.log(errors)}
            {errors.file && <FieldError errors={[errors.file]} />}
          </Field>
        </FieldSet>

        <Field>
          <Button type="submit">Save</Button>
        </Field>
      </FieldGroup>
    </form>
  );
};

export default TravelDocumentCreateForm;
