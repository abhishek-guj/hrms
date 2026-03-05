import { Plus } from "lucide-react";
import { useForm } from "react-hook-form";
import { useCreateTravelDocumentType } from "../../travelPlans/queries/travelDocuments.queries";
import { Button } from "../../ui/button";
import { Dialog, DialogContent, DialogDescription, DialogHeader, DialogTitle, DialogTrigger } from "../../ui/dialog";
import { Field, FieldError, FieldGroup, FieldLabel } from "../../ui/field";
import { Input } from "../../ui/input";

export const TravelDocTypeCreate = () => {

    const createTravelDocType = useCreateTravelDocumentType();

    const handleSave = async (data: any) => {
        createTravelDocType.mutateAsync({ payload: data });
        reset()
    };

    const {
        register,
        handleSubmit,
        formState: { errors },
        reset
    } = useForm({
        mode: "all",
    })

    return (
        <Dialog>
            <DialogTrigger asChild>
                <Button
                    size="sm"
                    className="flex items-center gap-1.5 cursor-pointer"
                    variant={"default"}
                    asChild
                >
                    <span className="w-fit">
                        <Plus className="h-4 w-4" />
                        <span className="hidden lg:block">New</span>
                    </span>
                </Button>
            </DialogTrigger>
            <DialogContent className="sm:min-w-fit sm:max-w-1/2 min-w-fit, max-w-fit">
                <DialogHeader>
                    <DialogTitle>Create Travel Doc Type</DialogTitle>
                    <DialogDescription>Create travel document type...</DialogDescription>
                </DialogHeader>
                <form onSubmit={handleSubmit(handleSave)} >
                    <FieldGroup>
                        <Field>
                            <FieldLabel htmlFor="name">Name</FieldLabel>
                            <Input id="name" type="text"
                                //  defaultValue={name}
                                {...register("name", { required: { value: true, message: "name is required" } })} />
                            {errors && <FieldError errors={[errors?.name]} />}
                        </Field>
                        <Button variant={"default"}
                            // onClick={handleSave}
                            disabled={createTravelDocType.isPending}
                        >
                            Save
                        </Button>
                    </FieldGroup>
                </form>
            </DialogContent>
        </Dialog >
    );
};