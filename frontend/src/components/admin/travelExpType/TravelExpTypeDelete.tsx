import { Trash } from "lucide-react";
import { Button } from "../../ui/button";
import {
    Dialog,
    DialogContent,
    DialogDescription,
    DialogHeader,
    DialogTitle,
    DialogTrigger,
} from "../../ui/dialog";
import { useDeleteTravelExpenseType } from "../../travelPlans/queries/travelPlans.queries";




export const TravelExpTypeDelete = ({
    travelExpType,
}: {
    travelExpType: any;
}) => {
    const deleteTravelExpType = useDeleteTravelExpenseType();
    const handleDelete = async () => {
        deleteTravelExpType.mutateAsync({ id: travelExpType.id });
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
                {/* Are you sure you want to delete: {travelExpType.name}? */}
                <Button variant={"default"} onClick={handleDelete} disabled={deleteTravelExpType.isPending}>
                    Delete
                </Button>
            </DialogContent>
        </Dialog>
    );
};
