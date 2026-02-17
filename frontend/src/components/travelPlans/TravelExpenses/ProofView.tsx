import React from "react";
import { useExpenseDocument } from "../../shared/services/documents.queries";
import PdfViewer from "../../PdfViewer";

const ProofView = ({ filePath }) => {
	const { data, isLoading, error } = useExpenseDocument(filePath);

	const getType = (blob: Blob) => {
		return blob?.type;
	};

	const fileType = getType(data);
	console.log(fileType);
	console.log(fileType?.startsWith("image"));
	if (fileType === "application/pdf") {
		return (
			// <div>
			<PdfViewer pdf={data} />
			// </div>
		);
	} else if (fileType?.startsWith("image")) {
		const image = URL.createObjectURL(data);
		return <img alt="pass image url" src={image}></img>;
	}
};

export default ProofView;
