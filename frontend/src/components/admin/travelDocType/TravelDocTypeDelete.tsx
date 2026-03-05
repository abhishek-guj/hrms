import { Trash } from "lucide-react";
import { useDeleteTravelDocumentType } from "../../travelPlans/queries/travelDocuments.queries";
import { Button } from "../../ui/button";
import {
    Dialog,
    DialogContent,
    DialogDescription,
    DialogHeader,
    DialogTitle,
    DialogTrigger,
} from "../../ui/dialog";




export const TravelDocTypeDelete = ({
    travelDocType,
}: {
    travelDocType: any;
}) => {
    const deleteTravelDocType = useDeleteTravelDocumentType();

    const handleDelete = async () => {
        deleteTravelDocType.mutateAsync({ id: travelDocType.id });
    };

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
                        <Trash className="h-4 w-4" />
                        <span className="hidden lg:block">Delete</span>
                    </span>
                </Button>
            </DialogTrigger>
            <DialogContent className="sm:min-w-fit sm:max-w-1/2 min-w-fit, max-w-fit">
                <DialogHeader>
                    <DialogTitle>Delete Travel Doc Type</DialogTitle>
                    <DialogDescription>Delete travel document type...</DialogDescription>
                </DialogHeader>
                Are you sure you want to delete: {travelDocType.name}?
                <Button variant={"default"} onClick={handleDelete} disabled={deleteTravelDocType.isPending}>
                    Delete
                </Button>
            </DialogContent>
        </Dialog>
    );
};
