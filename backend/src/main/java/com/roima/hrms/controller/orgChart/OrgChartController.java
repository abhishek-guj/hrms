package com.roima.hrms.controller.orgChart;

import com.roima.hrms.dtos.res.OrgEmployeeDto;
import com.roima.hrms.dtos.res.TravelPlanDto;
import com.roima.hrms.enums.ApiResponseType;
import com.roima.hrms.response.ApiResponse;
import com.roima.hrms.services.OrgChartService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/org")
@CrossOrigin(origins = "*")
@Tag(name = "Org Chart")
public class OrgChartController {

    private final OrgChartService orgChartService;

    public OrgChartController(OrgChartService orgChartService) {
        this.orgChartService = orgChartService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getWholeOrg(){
        var orgList = orgChartService.getAll();
        ApiResponse< List<OrgEmployeeDto>> res = ApiResponse.createApiResponse(ApiResponseType.SUCCESS, "Fetched whole orglist successfully", orgList, null);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }
}
