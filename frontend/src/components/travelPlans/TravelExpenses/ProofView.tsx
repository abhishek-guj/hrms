import React from "react";
import {
	useExpenseDocument,
	useGetDocument,
} from "../../shared/services/documents.queries";
import PdfViewer from "../../PdfViewer";

const ProofView = ({ filePath, docType }) => {
	const { data, isLoading, error } = useGetDocument(filePath, docType);

	const getType = (blob: Blob) => {
		return blob?.type;
	};

	const fileType = getType(data);
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
