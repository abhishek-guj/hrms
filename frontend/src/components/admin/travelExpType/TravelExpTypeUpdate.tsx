import { Pencil } from "lucide-react";
import { useState } from "react";
import { useForm } from "react-hook-form";
import { useUpdateTravelDocumentType } from "../../travelPlans/queries/travelDocuments.queries";
import { Button } from "../../ui/button";
import {
    Dialog,
    DialogContent,
    DialogDescription,
    DialogHeader,
    DialogTitle,
    DialogTrigger,
} from "../../ui/dialog";
import { Field, FieldGroup, FieldLabel } from "../../ui/field";
import { Input } from "../../ui/input";
import { useExpenseTypesUpdate } from "../../travelPlans/queries/travelPlans.queries";



export const TravelExpTypeUpdate = ({
    travelExpType,
}: {
    travelExpType: any;
}) => {

    const [name, setName] = useState(travelExpType.name);

    const updateTravelExpType = useExpenseTypesUpdate();

    const handleSave = async () => {
        updateTravelExpType.mutateAsync({ id: travelExpType.id, payload: { name } });
        reset()
    };



    const {
        formState: { errors },
        reset,
        handleSubmit
    } = useForm({
        mode: "all"
    })

    return (
        <Dialog>
            <DialogTrigger asChild>
                <Button
                    size="sm"
                    className="flex items-center gap-1.5 cursor-pointer"
                    variant={"outline"}
                    asChild
                >
                    <span className="w-fit">
                        <Pencil className="h-4 w-4" />
                        <span className="hidden lg:block">Edit</span>
                    </span>
                </Button>
            </DialogTrigger>
            <DialogContent className="sm:min-w-fit sm:max-w-1/2 min-w-fit, max-w-fit">
                <DialogHeader>
                    <DialogTitle>Update Travel Doc Type</DialogTitle>
                    <DialogDescription>Update travel document type...</DialogDescription>
                </DialogHeader>
                <form onSubmit={handleSubmit(handleSave)}>
                    <FieldGroup>
                        <Field>
                            <FieldLabel htmlFor="name">Name</FieldLabel>
                            <Input id="name" type="text" defaultValue={name} onChange={(e) => setName(e.target.value)} />
                        </Field>
                        <Button
                            variant={"default"}
                            disabled={updateTravelExpType.isPending}
                        >
                            Save
                        </Button>
                    </FieldGroup>
                </form>
            </DialogContent>
        </Dialog>
    );
};