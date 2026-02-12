import { useQuery } from "@tanstack/react-query";
import { TravelPlansService } from "../services/travelPlans.service";
import type { DataTabelItem } from "../types/TravelPlan.types";

export const useTravelPlans = () => {
	return useQuery({
		queryKey: ["getTravelPlans"],
		queryFn: (): Promise<DataTabelItem[]> =>
			TravelPlansService.getTravelPlans(),
	});
};
