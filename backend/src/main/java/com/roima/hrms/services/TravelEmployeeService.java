package com.roima.hrms.services;

import com.roima.hrms.dtos.req.TravelEmployeeReqDto;
import com.roima.hrms.dtos.res.TravelEmployeeDto;
import com.roima.hrms.entities.EmployeeProfile;
import com.roima.hrms.entities.TravelEmployee;
import com.roima.hrms.entities.TravelPlan;
import com.roima.hrms.exceptions.EmployeeNotFoundException;
import com.roima.hrms.exceptions.travel.TravelPlanNotFoundException;
import com.roima.hrms.repository.EmployeeProfileRepository;
import com.roima.hrms.repository.TravelEmployeeRepository;
import com.roima.hrms.repository.TravelPlanRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TravelEmployeeService {
    private final TravelEmployeeRepository travelEmployeeRepository;
    private final TravelPlanRepository travelPlanRepository;
    private final EmployeeProfileRepository employeeProfileRepository;
    private final ModelMapper modelMapper;
    private final NotificationService notificationService;
    private final EmailService emailService;

    public TravelEmployeeService(TravelEmployeeRepository travelEmployeeRepository, TravelPlanRepository travelPlanRepository, EmployeeProfileRepository employeeProfileRepository, ModelMapper modelMapper, NotificationService notificationService, EmailService emailService) {
        this.travelEmployeeRepository = travelEmployeeRepository;
        this.travelPlanRepository = travelPlanRepository;
        this.employeeProfileRepository = employeeProfileRepository;
        this.modelMapper = modelMapper;
        this.notificationService = notificationService;
        this.emailService = emailService;
    }

    public List<TravelEmployeeDto> getTravelEmployees(Long travelPlanId) {
        List<TravelEmployee> travelEmployees = travelEmployeeRepository.findByTravelPlan_Id(travelPlanId);
        return travelEmployees.stream().map(te -> modelMapper.map(te.getEmployeeProfile(), TravelEmployeeDto.class)).collect(Collectors.toList());
    }

    @Transactional
    public void addTravelEmployee(Long travelPlanId, Long employeeId) {
        TravelPlan travelPlan = travelPlanRepository.findById(travelPlanId).orElseThrow(TravelPlanNotFoundException::new);
        EmployeeProfile employeeProfile = employeeProfileRepository.findById(employeeId).orElseThrow(EmployeeNotFoundException::new);

        TravelEmployee travelEmployee = new TravelEmployee();
        travelEmployee.setEmployeeProfile(employeeProfile);
        travelEmployee.setTravelPlan(travelPlan);

        travelEmployeeRepository.save(travelEmployee);
    }


    @Transactional
    public boolean addTravelEmployees(Long travelPlanId, List<Long> employeeIds) {
        TravelPlan travelPlan = travelPlanRepository.findById(travelPlanId).orElseThrow(TravelPlanNotFoundException::new);
        employeeIds.forEach(employeeId -> {
            EmployeeProfile employeeProfile = employeeProfileRepository.findById(employeeId).orElseThrow(EmployeeNotFoundException::new);
            if (travelEmployeeRepository.existsByTravelPlanAndEmployeeProfile(travelPlan, employeeProfile)) {
                return;
            }
            TravelEmployee travelEmployee = new TravelEmployee();
            travelEmployee.setEmployeeProfile(employeeProfile);
            travelEmployee.setTravelPlan(travelPlan);
            travelEmployeeRepository.save(travelEmployee);

            // todo: add email service here
            notificationService.sendTravelPlanNotification(travelPlan);
            try {
                emailService.sendTravelPlanMail(travelPlanId);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        return true;
    }

    @Transactional
    public void updateTravelEmployees(Long travelPlanId, List<Long> employeeIds) {
        TravelPlan travelPlan = travelPlanRepository.findById(travelPlanId).orElseThrow(TravelPlanNotFoundException::new);

        // delete all already assigned employees
        travelEmployeeRepository.deleteAllByTravelPlan_Id((travelPlanId));

        List<EmployeeProfile> newEmployeeProfiles = employeeProfileRepository.findAllById(employeeIds);

        for (EmployeeProfile emp : newEmployeeProfiles) {
            TravelEmployee newTE = TravelEmployee.builder().travelPlan(travelPlan).employeeProfile(emp).build();
            travelEmployeeRepository.save(newTE);

            // todo make this to only the ones that are added
            notificationService.sendTravelPlanNotification(travelPlan);
            try {
                emailService.sendTravelPlanMail(travelPlanId);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }

    @Transactional
    public boolean deleteTravelEmployees(Long travelPlanId, List<TravelEmployeeReqDto> dto) {
        dto.forEach(employee -> {
            try {
                travelEmployeeRepository.removeTravelEmployeesByTravelPlan_IdAndEmployeeProfile_Id(travelPlanId, employee.getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return true;
    }

}
