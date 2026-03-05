import { useTravelDocumentTypes } from "../travelPlans/queries/travelDocuments.queries";
import { TravelDocTypeCreate } from "./travelDocType/TravelDocTypeCreate";
import { TravelDocTypeDelete } from "./travelDocType/TravelDocTypeDelete";
import { TravelDocTypeUpdate } from "./travelDocType/TravelDocTypeUpdate";

const TravelDocumentTypeSection = () => {
    const { data, isLoading, error } = useTravelDocumentTypes();

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
                    <div className="text-2xl font-bold mb-4">Travel Document Types</div>
                    <TravelDocTypeCreate />
                </div>
                <div className="flex flex-col gap-0.5 mt-2 overflow-auto h-full">
                    {data?.map((docType) => (
                        <div
                            key={docType.id}
                            className="p-1.5 px-3.5 border flex items-center justify-between"
                        >
                            <div>{docType.id}</div>
                            <div>{docType.name}</div>
                            <div className="flex gap-4 w-fit">
                                <TravelDocTypeUpdate travelDocType={docType} />
                                <TravelDocTypeDelete travelDocType={docType} />
                            </div>
                        </div>
                    ))}
                </div>
            </div>
        </div>
    );
};


export default TravelDocumentTypeSection;