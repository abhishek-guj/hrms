import {
	Select,
	SelectContent,
	SelectItem,
	SelectTrigger,
	SelectValue,
} from "@/components/ui/select";

const PageSizeSelector = ({ table }) => {
	return (
		<Select
			value={String(table.getState().pagination.pageSize)}
			onValueChange={(value) => table.setPageSize(Number(value))}
		>
			<SelectTrigger className="h-8 w-16">
				<SelectValue />
			</SelectTrigger>
			<SelectContent>
				{[5, 10, 20].map((size) => (
					<SelectItem key={size} value={String(size)}>
						{size}
					</SelectItem>
				))}
			</SelectContent>
		</Select>
	);
};

export default PageSizeSelector;
