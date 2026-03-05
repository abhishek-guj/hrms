import { Pencil } from "lucide-react";
import { useState } from "react";
import { Controller, useForm } from "react-hook-form";
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
import { Field, FieldError, FieldGroup, FieldLabel, FieldSet } from "../../ui/field";
import { Input } from "../../ui/input";
import { useExpenseTypesUpdate } from "../../travelPlans/queries/travelPlans.queries";
import { useGetAllRoles, useUpdateRole } from "../../achievements/services/role.queries";
import FormSelect from "../../travelPlans/TravelExpenses/forms/FormSelect";
import { ViewField } from "../../game/GameSlotDetails";
import type { FullEmployeeProfileDto } from "../../profile/profile.types";



export const UserRoleUpdate = ({
    employee,
}: {
    employee: FullEmployeeProfileDto;
}) => {


    const updateRole = useUpdateRole();

    const handleSave = async (data) => {
        console.log(data)

        await updateRole.mutateAsync({ id: employee.id, roleId: data?.role });
        reset()
    };

    const { data } = useGetAllRoles();
    // alert(employee?.userRoleRole)
    console.log(employee?.userRoleId)
    const {
        formState: { errors },
        reset,
        handleSubmit,
        control
    } = useForm({
        mode: "all",
        defaultValues: {
            role: `${employee?.userRoleId}`
        }
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
                    <DialogTitle>Update Role of User</DialogTitle>
                    <DialogDescription>Update Role of User...</DialogDescription>
                </DialogHeader>
                <form onSubmit={handleSubmit(handleSave)}>
                    <FieldGroup>
                        <FieldSet className="grid grid-cols-2">
                            <ViewField name="Current Role" value={employee.userRoleRole} />

                            <Field>
                                <FieldLabel htmlFor="uploadedForEmployeeId">
                                    Role Update To
                                </FieldLabel>
                                <Controller
                                    name="role"
                                    control={control}
                                    render={({ field, fieldState }) => (
                                        <FormSelect
                                            data={data}
                                            name={"role"}
                                            value={field.value}
                                            onValueChange={field.onChange}
                                            type={"role"}
                                        />
                                    )}
                                />
                                {errors.role && (
                                    <FieldError errors={[errors.role]} />
                                )}
                            </Field>
                        </FieldSet>
                        <Button
                            variant={"default"}
                        >
                            Save
                        </Button>
                    </FieldGroup>
                </form>
            </DialogContent>
        </Dialog>
    );
};