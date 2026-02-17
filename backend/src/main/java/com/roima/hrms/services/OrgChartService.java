package com.roima.hrms.services;

import com.roima.hrms.dtos.res.OrgEmployeeDto;
import com.roima.hrms.entities.EmployeeProfile;
import com.roima.hrms.repository.EmployeeProfileRepository;
import com.roima.hrms.repository.OrgChartRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class OrgChartService {

    private final OrgChartRepository orgChartRepository;

    public OrgChartService(OrgChartRepository orgChartRepository) {
        this.orgChartRepository = orgChartRepository;
    }

    public List<OrgEmployeeDto> getAll() {
//        try {
//
//            List<EmployeeProfile> employeeProfiles = orgChartRepository.getAllOrg();
//            return mapOrgEmployee(employeeProfiles);
//        } catch (Exception ex) {
//            log.error("org error", ex);
//            return new ArrayList<>();
//        }
        return new ArrayList<>();
    }

    private List<OrgEmployeeDto> mapOrgEmployee(List<EmployeeProfile> employeeProfiles) {
//        return employeeProfiles.stream().map(employeeProfile ->
//        {
//            var orgEmployee = new OrgEmployeeDto(employeeProfile.getId(), employeeProfile.getFirstName(), employeeProfile.getManager().getId());
//            return orgEmployee;
//        }).toList();
//    }
        return new ArrayList<>();
    }
}