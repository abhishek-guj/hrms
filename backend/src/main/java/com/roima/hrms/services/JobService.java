package com.roima.hrms.services;

import com.roima.hrms.dtos.req.JobRequestDto;
import com.roima.hrms.dtos.res.JobDto;
import com.roima.hrms.entities.*;
import com.roima.hrms.exceptions.UnauthorizedException;
import com.roima.hrms.exceptions.travel.ExpenseTypeNotFoundException;
import com.roima.hrms.exceptions.travel.TravelPlanNotFoundException;
import com.roima.hrms.mapper.job.JobMapper;
import com.roima.hrms.repository.*;
import com.roima.hrms.utils.RoleUtil;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class JobService {
    private final JobRepository jobRepository;
    private final RoleUtil roleUtil;
    private final TravelPlanRepository travelPlanRepository;
    private final EmployeeProfileRepository employeeProfileRepository;
    private final FileService fileService;
    private final ExpenseDocumentRepository expenseDocumentRepository;
    private final JobMapper jobMapper;

    public JobService(JobRepository jobRepository, RoleUtil roleUtil, TravelPlanRepository travelPlanRepository, EmployeeProfileRepository employeeProfileRepository, FileService fileService, ExpenseDocumentRepository expenseDocumentRepository, JobMapper jobMapper) {
        this.jobRepository = jobRepository;
        this.roleUtil = roleUtil;
        this.travelPlanRepository = travelPlanRepository;
        this.employeeProfileRepository = employeeProfileRepository;
        this.fileService = fileService;
        this.expenseDocumentRepository = expenseDocumentRepository;
        this.jobMapper = jobMapper;
    }

    public List<JobDto> getAllJobs() {
        // if admin or hr then show all expenses
        List<Job> jobs = jobRepository.findAll();

        return jobMapper.toJobDtoList(jobs);
    }

    public JobDto getById(Long id) {
        Job job = jobRepository.findById(id).orElseThrow(() -> new RuntimeException("Job Not Found!"));
        // this will be returned f admin or hr
        return jobMapper.toJobDto(job);
    }

//    @Transactional
//    public JobDto createJob(JobRequestDto dto) {
//
//        EmployeeProfile submittedBy = roleUtil.getCurrentEmployee();
//
//        // check if files not null
////        if (dto.getFiles() == null || dto.getFiles().isEmpty()) {
////            throw new RuntimeException("Please upload at least 1 proof document");
////        }
//
//        List<String> proofPaths = new ArrayList<String>();
//        for (MultipartFile file : dto.getFiles()) {
//            if (file.isEmpty()) {
//                continue;
//            }
//            fileService.validateFile(file);
//
//            String filePath = fileService.store(file, "expense");
//            proofPaths.add(filePath);
//        }
//
//        // ??? todo: check if employee submitting is allowed to submit or not
//
//        ExpenseType expenseType = expenseTypeRepository.findById(dto.getExpenseTypeId()).orElseThrow(ExpenseTypeNotFoundException::new);
//
//        // converting to entity
//        Job job = null;
//        try {
//
////        Job job = Job.builder()
//            job = Job.builder()
//                    .travelPlan(travelPlan)
//                    .submittedBy(submittedBy)
//                    .submitStatus(true)
//                    .expenseUploadDate(LocalDate.now())
//                    .expenseType(expenseType)
//                    .expenseAmount(dto.getExpenseAmount())
//                    .expenseDate(LocalDate.parse(dto.getExpenseDate().substring(0, 10)))
//                    .status(dto.getStatus() == null ? "Pending" : dto.getStatus())
//                    .remark(dto.getRemark())
//                    .statusChangedOn(LocalDateTime.now())
//                    .statusChangedBy(roleUtil.getCurrentEmployee())
//                    .build();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            ;
//        }
//
//        for (String path : proofPaths) {
//            ExpenseDocument expenseDocument = ExpenseDocument.builder()
//                    .job(job)
//                    .filePath(path)
//                    .uploadedAt(LocalDateTime.now())
//                    .build();
//            expenseDocumentRepository.save(expenseDocument);
//        }
//
//        // saving
//        jobRepository.save(job);
//
//        // returning
//        return jobMapper.toJobDto(job);
//    }

//    public JobDto updateJob(Long id, JobRequestDto dto) {
//
//        Job job = jobRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Job Not Found!"));
//
//        job = jobMapper.toUpdateEntity(job, dto);
//
//        // reset other fields in case: it was updated
//        TravelPlan travelPlan = travelPlanRepository.findById(dto.getTravelPlanId()).orElseThrow(TravelPlanNotFoundException::new);
//        job.setTravelPlan(travelPlan);
//
//        ExpenseType expenseType = expenseTypeRepository.findById(dto.getExpenseTypeId()).orElseThrow(ExpenseTypeNotFoundException::new);
//        job.setExpenseType(expenseType);
//
//
//        // todo: check if any new file is uploaded
//
//        // no need to updated submitted by let it be as it is.
//        // checks
//        Long submittedById = job.getSubmittedBy().getId();
//        if ((roleUtil.isEmployee() && !roleUtil.getCurrentEmployeeId().equals(submittedById)) || roleUtil.isManager()) {
//            throw new UnauthorizedException("Can't update this details");
//        } else {
//            jobRepository.save(job);
//            return jobMapper.toJobDto(job);
//        }
//    }
//
    public void deleteJob(Long id) {
        Job job = jobRepository.findById(id).orElseThrow(() -> new RuntimeException("Job Not Found!"));
        if (roleUtil.isAdmin() || roleUtil.isHr()) {
            jobRepository.delete(job);
        } else {
            throw new UnauthorizedException("You are not allowed to delete this.");
        }
    }
//
//    public List<JobDto> getAllJobsByTravelPlanId(Long travelPlanId) {
//        // if admin or hr then show all expenses
//        List<Job> jobs;
//        if (roleUtil.isAdmin() || roleUtil.isHr()) {
//            jobs = jobRepository.findAllByTravelId(travelPlanId);
//        } else if (roleUtil.isManager()) {
//            Long managerId = roleUtil.getCurrentEmployeeId();
//            jobs = jobRepository.findAllByManagerIdAndTravelId(managerId, travelPlanId);
//        } else {
//            Long employeeId = roleUtil.getCurrentEmployeeId();
//            jobs = jobRepository.findAllByEmployeeIdAndTravelId(employeeId, travelPlanId);
//        }
//        return jobMapper.toJobDtoList(jobs);
//    }
}