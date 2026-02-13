import React from "react";
import { Controller } from "react-hook-form";
import {
	Field,
	FieldContent,
	FieldDescription,
	FieldError,
	FieldLabel,
} from "../../../ui/field";
import {
	Select,
	SelectContent,
	SelectItem,
	SelectTrigger,
	SelectValue,
} from "../../../ui/select";

const TravelTypeSelect = ({ value, onValueChange }) => {
	return (
		<Field orientation="responsive">
			<Select name={"travelTypeID"} value={value} onValueChange={onValueChange}>
				<SelectTrigger id="form-rhf-select-language" className="min-w-[120px]">
					<SelectValue placeholder="Select" />
				</SelectTrigger>
				<SelectContent position="item-aligned">
					<SelectItem value="1">Business</SelectItem>
					<SelectItem value="2">Offsite</SelectItem>
				</SelectContent>
			</Select>
		</Field>
	);
};

export default TravelTypeSelect;
