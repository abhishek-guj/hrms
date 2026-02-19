package com.roima.hrms.services;

import com.roima.hrms.dtos.req.EmailShareReqDto;
import com.roima.hrms.dtos.req.JobReferralReqDto;
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
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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
    private final EmailService emailService;
    private final JobShareRepository jobShareRepository;
    private final JobReferralRepository jobReferralRepository;

    public JobService(JobRepository jobRepository, RoleUtil roleUtil, TravelPlanRepository travelPlanRepository, EmployeeProfileRepository employeeProfileRepository, FileService fileService, ExpenseDocumentRepository expenseDocumentRepository, JobMapper jobMapper, EmailService emailService, JobShareRepository jobShareRepository, JobReferralRepository jobReferralRepository) {
        this.jobRepository = jobRepository;
        this.roleUtil = roleUtil;
        this.travelPlanRepository = travelPlanRepository;
        this.employeeProfileRepository = employeeProfileRepository;
        this.fileService = fileService;
        this.expenseDocumentRepository = expenseDocumentRepository;
        this.jobMapper = jobMapper;
        this.emailService = emailService;
        this.jobShareRepository = jobShareRepository;
        this.jobReferralRepository = jobReferralRepository;
    }

    public List<JobDto> getAllJobs() {
        // if admin or hr then show all expenses
        List<Job> jobs = jobRepository.findAll();

        return jobMapper.toJobDtoList(jobs);
    }

    public JobDto getById(Long id) {
        Job job = jobRepository.findById(id).orElseThrow(() -> new RuntimeException("Job Not Found!"));
        // this will be returned f admin or hr
//        if (roleUtil.isAdmin() || roleUtil.isHr() || roleUtil.isManager() || roleUtil.isEmployee()) {
//            return jobMapper.toJobDto(job);
//        } else {
//            // for normal public
//            JobDto jobDto = jobMapper.toJobDto(job);
//        }
        return jobMapper.toJobDto(job);
    }

    @Transactional
    public JobDto createJob(JobRequestDto dto) {

        EmployeeProfile submittedBy = roleUtil.getCurrentEmployee();

//         check if files not null
        if (dto.getJobJdFile() == null || dto.getJobJdFile().isEmpty()) {
            throw new RuntimeException("Please upload at least 1 document");
        }

        fileService.validateFile(dto.getJobJdFile());


        // converting to entity
        Job job = jobMapper.toEntity(dto);


        String filePath = fileService.store(dto.getJobJdFile(), "job");

        JobJdFile jdFile = JobJdFile.builder().job(job).filePath(filePath).build();

        job.setJobJdFile(jdFile);

        // saving
        jobRepository.save(job);

        // returning
        return jobMapper.toJobDto(job);
    }

    //
    public void deleteJob(Long id) {
        Job job = jobRepository.findById(id).orElseThrow(() -> new RuntimeException("Job Not Found!"));
        if (roleUtil.isAdmin() || roleUtil.isHr()) {
            jobRepository.delete(job);
        } else {
            throw new UnauthorizedException("You are not allowed to delete this.");
        }
    }

    //    public void shareById(Long id, List<String> emails) {
    @Transactional
    @Async
    public void shareById(Long id, List<EmailShareReqDto> emails) {
        // TAKE EMAIL IN DTO
        for (EmailShareReqDto email : emails) {
            JobShare jobShare = JobShare.builder().job(jobRepository.findById(id).orElseThrow(() -> new RuntimeException("Job Not Found!"))).sharedOn(LocalDateTime.now()).sharedBy(roleUtil.getCurrentEmployee()).build();
            try {
                emailService.sendShareMail(email.getEmail(), id);
                jobShare.setEmailSent(true);
                jobShareRepository.save(jobShare);

            } catch (Exception ex) {
                jobShare.setEmailSent(false);
                jobShareRepository.save(jobShare);
                ex.printStackTrace();
            }
        }


    }


    @Transactional
    public boolean referById(Long jobId, JobReferralReqDto jobReferralDto) {

        Job job = jobRepository.findById(jobId).orElseThrow(() -> new RuntimeException("Job Not Found"));
        EmployeeProfile referredBy = roleUtil.getCurrentEmployee();

        // check if files not null
        if (jobReferralDto.getCvFile() == null || jobReferralDto.getCvFile().isEmpty()) {
            throw new RuntimeException("Please upload CV");
        }
        fileService.validateFile(jobReferralDto.getCvFile());

        String filePath = fileService.store(jobReferralDto.getCvFile(), "cv");

        JobReferral jobReferral = JobReferral
                .builder()
                .job(job)
                .firstName(jobReferralDto.getFirstName())
                .lastName(jobReferralDto.getLastName())
                .email(jobReferralDto.getEmail())
                .contactNumber(jobReferralDto.getContactNumber())
                .referredOn(LocalDateTime.now())
                .referredBy(referredBy)
                .cvPath(filePath)
                .note(jobReferralDto.getNote())
                .status("Review")
                .statusChangedOn(LocalDateTime.now())
                .build();

        try {
            emailService.sendReferMail(jobReferral, jobId);
            jobReferralRepository.save(jobReferral);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

}