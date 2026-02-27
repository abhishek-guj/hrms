import React from "react";
import {
	useExpenseDocument,
	useGetDocument,
} from "../../shared/services/documents.queries";
import PdfViewer from "../../PdfViewer";
import { Button } from "../../ui/button";
import { saveAs } from "file-saver";

const ProofView = ({ filePath, docType }) => {
	const { data, isLoading, error } = useGetDocument(filePath, docType);

	const getType = (blob: Blob) => {
		return blob?.type;
	};

	// handlers
	const handleDownload = () => {
		if (data) {
			const type = getType(data);
			saveAs(new Blob([data], { type: type }), "example.pdf");
		}
	};

	if (isLoading) {
		return (
			<div className="p-4 px-8 flex flex-col min-w-96 min-h-96 justify-center items-center">
				Loading
			</div>
		);
	}
	if (error) {
		return (
			<div className="p-4 px-8 flex flex-col min-w-96 min-h-96 justify-center items-center">
				No Data Found...
			</div>
		);
	}

	const fileType = getType(data!);
	if (fileType === "application/pdf") {
		return (
			<div>
				<div className="flex flex-row justify-end py-1">
					<Button onClick={handleDownload}>Download</Button>
				</div>
				<PdfViewer pdf={data} />
			</div>
		);
	} else if (fileType?.startsWith("image")) {
		const image = URL.createObjectURL(data!);
		return <img alt="pass image url" src={image}></img>;
	}
};

export default ProofView;
