import { useQuery } from "@tanstack/react-query";
import { TravelPlansService } from "../services/travelPlans.service";

export const useTravelPlans = () => {
	return useQuery({
		queryKey: ["getTravelPlans"],
		queryFn: () => TravelPlansService.getTravelPlans(),
	});
};
