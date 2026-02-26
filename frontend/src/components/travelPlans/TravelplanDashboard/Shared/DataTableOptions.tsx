import { Input } from "@/components/ui/input";
import PageSizeSelector from "./PageSizeSelector";
import SearchInput from "./SearchInput";

const DataTableOptions = ({ table, globalFilter, setGlobalFilter }) => {
  return (
    <div className="flex flex-col gap-4 sm:flex-row sm:items-center sm:justify-between w-ful">
      <div className="flex items-center gap-2">
        <span className="text-sm text-muted-foreground">Show</span>
        <PageSizeSelector table={table} />
      </div>
      <div className="flex items-center gap-2">
        <span className="text-sm text-muted-foreground">Search</span>
        <SearchInput
          globalFilter={globalFilter}
          setGlobalFilter={setGlobalFilter}
        />
      </div>
    </div>
  );
};

export default DataTableOptions;
