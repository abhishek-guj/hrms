package com.roima.hrms.services;

import com.roima.hrms.dtos.req.TravelExpenseRequestDto;
import com.roima.hrms.dtos.res.TravelExpenseDto;
import com.roima.hrms.entities.*;
import com.roima.hrms.exceptions.UnauthorizedException;
import com.roima.hrms.exceptions.travel.ExpenseTypeNotFoundException;
import com.roima.hrms.exceptions.travel.TravelPlanNotFoundException;
import com.roima.hrms.mapper.travel.TravelExpenseMapper;
import com.roima.hrms.mapper.travel.ExpenseTypeMapper;
import com.roima.hrms.repository.*;
import com.roima.hrms.utils.RoleUtil;
import jakarta.transaction.Transactional;
import org.springdoc.core.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TravelExpenseService {
    private final TravelExpenseRepository travelExpenseRepository;
    private final TravelExpenseMapper travelExpenseMapper;
    private final ExpenseTypeRepository expenseTypeRepository;
    private final ExpenseTypeMapper expenseTypeMapper;
    private final RoleUtil roleUtil;
    private final TravelPlanRepository travelPlanRepository;
    private final EmployeeProfileRepository employeeProfileRepository;
    private final FileService fileService;
    private final ExpenseDocumentRepository expenseDocumentRepository;

    @Autowired
    public TravelExpenseService(TravelExpenseRepository travelExpenseRepository, TravelExpenseMapper travelExpenseMapper, ExpenseTypeRepository expenseTypeRepository, ExpenseTypeMapper expenseTypeMapper, SecurityService securityService, RoleUtil roleUtil, TravelPlanRepository travelPlanRepository, EmployeeProfileRepository employeeProfileRepository, FileService fileService, ExpenseDocumentRepository expenseDocumentRepository) {
        this.travelExpenseRepository = travelExpenseRepository;
        this.travelExpenseMapper = travelExpenseMapper;
        this.expenseTypeRepository = expenseTypeRepository;
        this.expenseTypeMapper = expenseTypeMapper;
        this.roleUtil = roleUtil;
        this.travelPlanRepository = travelPlanRepository;
        this.employeeProfileRepository = employeeProfileRepository;
        this.fileService = fileService;
        this.expenseDocumentRepository = expenseDocumentRepository;
    }

    public List<TravelExpenseDto> getAllTravelExpenses() {
        // if admin or hr then show all expenses
        List<TravelExpense> travelExpenses;
        if (roleUtil.isAdmin() || roleUtil.isHr()) {
            travelExpenses = travelExpenseRepository.findAll();
        } else if (roleUtil.isManager()) {
            Long managerId = roleUtil.getCurrentEmployeeId();
            travelExpenses = travelExpenseRepository.findAllByManagerId(managerId);
        } else {
            Long employeeId = roleUtil.getCurrentEmployeeId();
            travelExpenses = travelExpenseRepository.findAllByEmployeeId(employeeId);
        }
        return travelExpenseMapper.toTravelExpenseDtoList(travelExpenses);
    }

    public TravelExpenseDto getById(Long id) {
        TravelExpense travelExpense = travelExpenseRepository.findById(id).orElseThrow(() -> new RuntimeException("Travel Expense Not Found!"));

        Long curentEmployeeId = roleUtil.getCurrentEmployeeId();
        // check if requester is allowed to see or not

        if (roleUtil.isEmployee()) {
            if (curentEmployeeId.equals(travelExpense.getSubmittedBy().getId())) {
                return travelExpenseMapper.toTravelExpenseDto(travelExpense);
            } else {
                throw new UnauthorizedException("You are not allowed to view this expense");
            }
        }
        if (roleUtil.isManager()) {
            // check if expense done by subordinate emplyoee
            if (roleUtil.checkIsManager(curentEmployeeId, travelExpense.getSubmittedBy().getId())) {
                return travelExpenseMapper.toTravelExpenseDto(travelExpense);
            } else {
                throw new UnauthorizedException("You are not allowed to view this expense");
            }
        }
        // this will be returned f admin or hr
        return travelExpenseMapper.toTravelExpenseDto(travelExpense);
    }

    @Transactional
    public TravelExpenseDto createTravelExpense(TravelExpenseRequestDto dto) {

        TravelPlan travelPlan = travelPlanRepository.findById(dto.getTravelPlanId()).orElseThrow(TravelPlanNotFoundException::new);

        EmployeeProfile submittedBy = roleUtil.getCurrentEmployee();

        // check if files not null
        if (dto.getFiles() == null || dto.getFiles().isEmpty()) {
            throw new RuntimeException("Please upload at least 1 proof document");
        }

        List<String> proofPaths = new ArrayList<String>();
        for (MultipartFile file : dto.getFiles()) {
            if (file.isEmpty()) {
                continue;
            }
            fileService.validateFile(file);

            String filePath = fileService.store(file, "expense");
            proofPaths.add(filePath);
        }

        // ??? todo: check if employee submitting is allowed to submit or not

        ExpenseType expenseType = expenseTypeRepository.findById(dto.getExpenseTypeId()).orElseThrow(ExpenseTypeNotFoundException::new);

        // converting to entity
        TravelExpense travelExpense = null;
        try {

//        TravelExpense travelExpense = TravelExpense.builder()
            travelExpense = TravelExpense.builder()
                    .travelPlan(travelPlan)
                    .submittedBy(submittedBy)
                    .submitStatus(true)
                    .expenseUploadDate(LocalDate.now())
                    .expenseType(expenseType)
                    .expenseAmount(dto.getExpenseAmount())
                    .expenseDate(LocalDate.parse(dto.getExpenseDate().substring(0, 10)))
                    .status(dto.getStatus() == null ? "Pending" : dto.getStatus())
                    .remark(dto.getRemark())
                    .statusChangedOn(LocalDateTime.now())
                    .statusChangedBy(roleUtil.getCurrentEmployee())
                    .build();
        } catch (Exception ex) {
            ex.printStackTrace();
            ;
        }

        for (String path : proofPaths) {
            ExpenseDocument expenseDocument = ExpenseDocument.builder()
                    .travelExpense(travelExpense)
                    .filePath(path)
                    .uploadedAt(LocalDateTime.now())
                    .build();
            expenseDocumentRepository.save(expenseDocument);
        }

        // saving
        travelExpenseRepository.save(travelExpense);

        // returning
        return travelExpenseMapper.toTravelExpenseDto(travelExpense);
    }

    public TravelExpenseDto updateTravelExpense(Long id, TravelExpenseRequestDto dto) {

        TravelExpense travelExpense = travelExpenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Travel Expense Not Found!"));

        travelExpense = travelExpenseMapper.toUpdateEntity(travelExpense, dto);

        // reset other fields in case: it was updated
        TravelPlan travelPlan = travelPlanRepository.findById(dto.getTravelPlanId()).orElseThrow(TravelPlanNotFoundException::new);
        travelExpense.setTravelPlan(travelPlan);

        ExpenseType expenseType = expenseTypeRepository.findById(dto.getExpenseTypeId()).orElseThrow(ExpenseTypeNotFoundException::new);
        travelExpense.setExpenseType(expenseType);


        // todo: check if any new file is uploaded

        // no need to updated submitted by let it be as it is.
        // checks
        Long submittedById = travelExpense.getSubmittedBy().getId();
        if ((roleUtil.isEmployee() && !roleUtil.getCurrentEmployeeId().equals(submittedById)) || roleUtil.isManager()) {
            throw new UnauthorizedException("Can't update this details");
        } else {
            travelExpenseRepository.save(travelExpense);
            return travelExpenseMapper.toTravelExpenseDto(travelExpense);
        }
    }

    public void deleteTravelExpense(Long id) {
        TravelExpense travelExpense = travelExpenseRepository.findById(id).orElseThrow(() -> new RuntimeException("Travel Expense Not Found!"));
        Long submittedById = travelExpense.getSubmittedBy().getId();
        if (roleUtil.isEmployee() && roleUtil.getCurrentEmployeeId().equals(submittedById)) {
            travelExpenseRepository.delete(travelExpense);
        } else if (roleUtil.isAdmin() || roleUtil.isHr()) {
            travelExpenseRepository.delete(travelExpense);
        } else {
            throw new UnauthorizedException("You are not allowed to delete this.");
        }
    }

    public List<TravelExpenseDto> getAllTravelExpensesByTravelPlanId(Long travelPlanId) {
        // if admin or hr then show all expenses
        List<TravelExpense> travelExpenses;
        if (roleUtil.isAdmin() || roleUtil.isHr()) {
            travelExpenses = travelExpenseRepository.findAllByTravelId(travelPlanId);
        } else if (roleUtil.isManager()) {
            Long managerId = roleUtil.getCurrentEmployeeId();
            travelExpenses = travelExpenseRepository.findAllByManagerIdAndTravelId(managerId, travelPlanId);
        } else {
            Long employeeId = roleUtil.getCurrentEmployeeId();
            travelExpenses = travelExpenseRepository.findAllByEmployeeIdAndTravelId(employeeId, travelPlanId);
        }
        return travelExpenseMapper.toTravelExpenseDtoList(travelExpenses);
    }


//    public List<TravelExpenseDto> getByIdTravelExpensesByTravelPlanId(Long travelPlanId,Long ExpenseId) {
//        // if admin or hr then show all expenses
//        List<TravelExpense> travelExpenses;
//        if(roleUtil.isAdmin() || roleUtil.isHr()){
//            travelExpenses = travelExpenseRepository.findAllByTravelId(travelPlanId);
//        } else if (roleUtil.isManager()) {
//            Long managerId = roleUtil.getCurrentEmployeeId();
//            travelExpenses = travelExpenseRepository.findAllByManagerIdAndTravelId(managerId, travelPlanId);
//        }else {
//            Long employeeId = roleUtil.getCurrentEmployeeId();
//            travelExpenses = travelExpenseRepository.findAllByEmployeeIdAndTravelId(employeeId, travelPlanId);
//        }
//        return travelExpenseMapper.toTravelExpenseDtoList(travelExpenses);
//    }
}
