import { useExpenseTypes } from "../travelPlans/queries/travelPlans.queries";
import { TravelExpTypeCreate } from "./travelExpType/TravelExpTypeCreate";
import { TravelExpTypeDelete } from "./travelExpType/TravelExpTypeDelete";
import { TravelExpTypeUpdate } from "./travelExpType/TravelExpTypeUpdate";

const TravelExpenseTypeSection = () => {
    const { data, isLoading, error } = useExpenseTypes();

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
                    <div className="text-2xl font-bold mb-4">Travel Expense Types</div>
                    <TravelExpTypeCreate />
                </div>
                <div className="flex flex-col gap-0.5 mt-2 overflow-auto h-full">
                    {data?.map((expType) => (
                        <div
                            key={expType.id}
                            className="p-1.5 px-3.5 border flex items-center justify-between"
                        >
                            <div>{expType.id}</div>
                            <div>{expType.name}</div>
                            <div className="flex gap-4 w-fit">
                                <TravelExpTypeUpdate travelExpType={expType} />
                                <TravelExpTypeDelete travelExpType={expType} />
                            </div>
                        </div>
                    ))}
                </div>
            </div>
        </div>
    );
};



export default TravelExpenseTypeSection;