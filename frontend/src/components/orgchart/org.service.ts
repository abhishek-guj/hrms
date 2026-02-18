import { api } from "../../api/apiClient";
import { ORGCHART_ENDPOINTS } from "../../api/endpoints";

export const OrgChartService = {

    async getMyOrgChart() {
        const res = await api.get(
            ORGCHART_ENDPOINTS.getMyOrg(),
        );
        return res.data.data;
    },
}