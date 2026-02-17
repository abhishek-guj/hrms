import { useState, useEffect, useRef } from "react";
import { Document, Page } from "react-pdf";
import { api } from "../api/apiClient";

import { pdfjs } from "react-pdf";
import { useExpenseDocument } from "./shared/services/documents.queries";

pdfjs.GlobalWorkerOptions.workerSrc = new URL(
	"pdfjs-dist/build/pdf.worker.min.mjs",
	import.meta.url,
).toString();

export default function PdfViewer({ pdf }) {
	const [numPages, setNumPages] = useState(0);
	const [pageWidth, setPageWidth] = useState(0);
	const containerRef = useRef();

	const onDocumentLoadSuccess = ({ numPages }) => {
		setNumPages(numPages);
	};

	// Update page width whenever container resizes
	useEffect(() => {
		const handleResize = () => {
			if (containerRef.current) {
				setPageWidth(containerRef.current.offsetWidth);
			}
		};

		handleResize();
		window.addEventListener("resize", handleResize);

		return () => window.removeEventListener("resize", handleResize);
	}, []);

	return (
		<div
			className="w-full rounded-lg max-h-96 overflow-y-auto"
			// className="w-full rounded-lg max-h-96 mb-4 relative"
			ref={containerRef}
		>
			<Document file={pdf} onLoadSuccess={onDocumentLoadSuccess}>
				{Array.from({ length: numPages }, (_, i) => (
					<Page
						key={`page_${i + 1}`}
						pageNumber={i + 1}
						width={pageWidth}
						renderTextLayer={false}
						renderAnnotationLayer={false}
						className="mb-2 border-red-600"
						scale={1}
					/>
				))}
			</Document>
		</div>
	);
}
