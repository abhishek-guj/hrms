import React from "react";
import { Input } from "@/components/ui/input";

const SearchInput = ({ globalFilter, setGlobalFilter }) => {
  return (
    <Input
      placeholder="Search..."
      value={globalFilter}
      onChange={(e) => setGlobalFilter(e.target.value)}
      className="h-8 w-full sm:w-64"
    />
  );
};

export default SearchInput;
