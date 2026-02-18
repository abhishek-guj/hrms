import { useQuery, type UseQueryResult } from "@tanstack/react-query";
import { OrgChartService } from "./org.service";
import type { OrgChainDto } from "./org.types";

export const useMyOrgChart = (): UseQueryResult<OrgChainDto[]> => {
    return useQuery({
        queryKey: ["getMyOrgChart"],
        queryFn: (): Promise<OrgChainDto[]> =>
            OrgChartService.getMyOrgChart(),
    });
};