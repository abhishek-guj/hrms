package com.roima.hrms.services;

import com.roima.hrms.dtos.res.OrgEmployeeDto;
import com.roima.hrms.entities.EmployeeProfile;
import com.roima.hrms.repository.EmployeeProfileRepository;
import com.roima.hrms.repository.OrgChartRepository;
import com.roima.hrms.utils.RoleUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrgChartService {

    private final OrgChartRepository orgChartRepository;
    private final EmployeeProfileRepository employeeProfileRepository;
    private final RoleUtil roleUtil;

    public OrgChartService(OrgChartRepository orgChartRepository, EmployeeProfileRepository employeeProfileRepository, RoleUtil roleUtil) {
        this.orgChartRepository = orgChartRepository;
        this.employeeProfileRepository = employeeProfileRepository;
        this.roleUtil = roleUtil;
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

    public OrgEmployeeDto getOrgChart() {
        List<EmployeeProfile> employeeProfiles = employeeProfileRepository.findAll();

        Map<Long, OrgEmployeeDto> orgEmployeesMap = employeeProfiles.stream().map(employeeProfile -> OrgEmployeeDto.builder()
                .employeeProfileId(employeeProfile.getId())
                .firstName(employeeProfile.getFirstName())
                .underEmployees(new ArrayList<>())
                .build()
        ).collect(Collectors.toMap(orgEmployeeDto -> orgEmployeeDto.getEmployeeProfileId(), orgEmployeeDto -> orgEmployeeDto));


        List<OrgEmployeeDto> roots = new ArrayList<>();

        for (EmployeeProfile employeeProfile : employeeProfiles) {
            if (employeeProfile.getManager() == null) {
                roots.add(orgEmployeesMap.get(employeeProfile.getId()));
            } else {
                var orgManagerDto = orgEmployeesMap.get(employeeProfile.getManager().getId());
                orgManagerDto.getUnderEmployees().add(orgEmployeesMap.get(employeeProfile.getId()));
            }
        }

        var b = orgEmployeesMap;
        return orgEmployeesMap.get(roots.get(0).getEmployeeProfileId());
    }

    public OrgEmployeeDto getMyOrgChart() {

        List<EmployeeProfile> employeeProfiles = employeeProfileRepository.findAll();

        Map<Long, OrgEmployeeDto> orgEmployeesMap = employeeProfiles.stream().map(employeeProfile -> OrgEmployeeDto.builder()
                .employeeProfileId(employeeProfile.getId())
                .firstName(employeeProfile.getFirstName())
                .underEmployees(new ArrayList<>())
                .build()
        ).collect(Collectors.toMap(orgEmployeeDto -> orgEmployeeDto.getEmployeeProfileId(), orgEmployeeDto -> orgEmployeeDto));

        var currentEmployee = roleUtil.getCurrentEmployee();

        var currentEmployeeOrgChart = orgEmployeesMap.get(currentEmployee.getId());

        // getting root employee
        OrgEmployeeDto root = null;
        for (EmployeeProfile employeeProfile : employeeProfiles) {
            if (employeeProfile.getManager() == null) {
                root = orgEmployeesMap.get(employeeProfile.getId());
            }
        }

        // getting all employees under current employee
        var tmp = currentEmployee;
        while (true) {

            // get manager of current employee
            var manager = tmp.getManager();

            if (manager == null) {

                break;
            }

            // getting his orgmap
            OrgEmployeeDto managerMap = orgEmployeesMap.get(manager.getId());
            // adding current employee to manager
            managerMap.getUnderEmployees().add(orgEmployeesMap.get(tmp.getId()));

            // upading tmp to its manager
            tmp = manager;

        }


        return root;
    }

    public List<OrgEmployeeDto> getMyOrgChain(){
        List<OrgEmployeeDto> chain = new ArrayList<>();

        EmployeeProfile currentEmployee = roleUtil.getCurrentEmployee();


        List<OrgEmployeeDto> orgUnderCurrentEmployee = new ArrayList<>();
        // find all under current employee
        List<EmployeeProfile> underCurrentEmployee = employeeProfileRepository.getAllByManager_Id(currentEmployee.getId());
        for(EmployeeProfile employeeProfile:underCurrentEmployee){
            orgUnderCurrentEmployee.add(
                    OrgEmployeeDto.builder().employeeProfileId(employeeProfile.getId()).firstName(employeeProfile.getFirstName()).lastName(employeeProfile.getLastName()).build()
            );
        }


        EmployeeProfile tmp = currentEmployee;
        while(tmp!=null){
            OrgEmployeeDto newDto = OrgEmployeeDto.builder().employeeProfileId(tmp.getId()).firstName(tmp.getFirstName()).lastName(tmp.getLastName()).build();
            if(tmp.getId().equals(currentEmployee.getId())){
                newDto.setUnderEmployees(orgUnderCurrentEmployee);
                newDto.setCurrentEmployee(true);
            }
            chain.add(newDto);
            tmp = tmp.getManager();
        }


        return chain;
    }

}