import { useFullEmployeesAll } from "../shared/services/employee.queries";
import { UserRoleUpdate } from "./userRole/UserRoleUpdate";

const UserRoleSection = () => {
    const { data, isLoading, error } = useFullEmployeesAll();

    if (isLoading) {
        return <div>Loading...</div>;
    }
    if (error) {
        return <div>Some Error Occurred</div>;
    }
    return (
        <div className="p-2 sm:p-6 sm:px-14  max-h-[70vh] h-full w-full">
            <div className="h-full overflow-hidden flex flex-col">
                <div className="flex justify-between">
                    <div className="text-2xl font-bold mb-4">User Role Editor</div>
                </div>
                <div className="flex flex-col gap-0.5 mt-2 overflow-auto h-full">
                    {data?.map((employee) => (
                        <div
                            key={employee.id}
                            className="p-1.5 px-3.5 border grid grid-cols-5 items-center justify-between"
                        >
                            <div>{employee.id}</div>
                            <div className="flex gap-2">
                                <div>{employee.firstName} {employee.lastName}</div>
                            </div>
                            <div>
                                {employee.userEmail}
                            </div>
                            <div>
                                {employee?.userRoleRole}
                            </div>
                            <div className="flex gap-4 w-fit justify-end">
                                <UserRoleUpdate employee={employee} />
                            </div>
                        </div>
                    ))}
                </div>
            </div>
        </div>
    );
};



export default UserRoleSection;