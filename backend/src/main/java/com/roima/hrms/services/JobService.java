package com.roima.hrms.services;

import com.roima.hrms.dtos.req.*;
import com.roima.hrms.dtos.res.JobDto;
import com.roima.hrms.dtos.res.JobReferralDto;
import com.roima.hrms.entities.*;
import com.roima.hrms.exceptions.UnauthorizedException;
import com.roima.hrms.exceptions.travel.ExpenseTypeNotFoundException;
import com.roima.hrms.exceptions.travel.TravelPlanNotFoundException;
import com.roima.hrms.mapper.job.JobMapper;
import com.roima.hrms.repository.*;
import com.roima.hrms.utils.RoleUtil;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
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
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class JobService {

    private final JobHrRepository jobHrRepository;
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
    private final JobCvReviewerRepository jobCvReviewerRepository;
    private final NotificationService notificationService;
    private final ModelMapper modelMapper;
    private final JobJdFileRepository jobJdFileRepository;

    public JobService(JobRepository jobRepository, RoleUtil roleUtil, TravelPlanRepository travelPlanRepository,
            EmployeeProfileRepository employeeProfileRepository, FileService fileService,
            ExpenseDocumentRepository expenseDocumentRepository, JobMapper jobMapper, EmailService emailService,
            JobShareRepository jobShareRepository, JobReferralRepository jobReferralRepository,
            NotificationService notificationService, ModelMapper modelMapper, JobHrRepository jobHrRepository,
            JobCvReviewerRepository jobCvReviewerRepository,
            JobJdFileRepository jobJdFileRepository) {
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
        this.notificationService = notificationService;
        this.modelMapper = modelMapper;
        this.jobHrRepository = jobHrRepository;
        this.jobCvReviewerRepository = jobCvReviewerRepository;
        this.jobJdFileRepository = jobJdFileRepository;
    }

    public List<JobDto> getAllJobs() {
        // if admin or hr then show all expenses
        List<Job> jobs = jobRepository.findAll();

        List<JobDto> dtos = jobMapper.toJobDtoList(jobs);

        dtos.forEach(dto -> {
            Job j = jobRepository.findById(dto.getId()).orElse(null);
            List<String> hrs = jobHrRepository.findAllByJob(j).stream().map(hr -> hr.getHr().getId().toString())
                    .toList();
            List<String> reviewers = jobCvReviewerRepository.findAllByJob(j).stream()
                    .map(rev -> rev.getReviewer().getId().toString()).toList();
            dto.setHrIds(hrs);
            dto.setCvReviewerIds(reviewers);
        });

        return dtos;
    }

    public JobDto getById(Long id) {
        Job job = jobRepository.findById(id).orElseThrow(() -> new RuntimeException("Job Not Found!"));
        // this will be returned f admin or hr
        // if (roleUtil.isAdmin() || roleUtil.isHr() || roleUtil.isManager() ||
        // roleUtil.isEmployee()) {
        // return jobMapper.toJobDto(job);
        // } else {
        // // for normal public
        // JobDto jobDto = jobMapper.toJobDto(job);
        // }
        List<String> hrs = jobHrRepository.findAllByJob(
                job).stream().map(hr -> hr.getHr().getId().toString())
                .toList();
        List<String> reviewers = jobCvReviewerRepository.findAllByJob(
                job).stream()
                .map(rev -> rev.getReviewer().getId().toString()).toList();

        JobDto dto = jobMapper.toJobDto(job);

        dto.setHrIds(hrs);
        dto.setCvReviewerIds(reviewers);

        return dto;
    }

    @Transactional
    public JobDto createJob(JobRequestDto dto) {

        EmployeeProfile submittedBy = roleUtil.getCurrentEmployee();

        EmployeeProfile defaultHr = employeeProfileRepository.getEmployeeProfileByUser_Email("hr1@exp.com");

        dto.getHrIds().add(defaultHr.getId());

        // check if files not null
        if (dto.getJobJdFile() == null || dto.getJobJdFile().isEmpty()) {
            throw new RuntimeException("Please upload at least 1 document");
        }

        fileService.validateFile(dto.getJobJdFile());

        // converting to entity
        Job job = jobMapper.toEntity(dto);

        String filePath = fileService.store(dto.getJobJdFile(), "job");

        JobJdFile jdFile = JobJdFile.builder().job(job).filePath(filePath).build();

        job.setJobJdFile(jdFile);
        job.setCreatedOn(LocalDateTime.now());
        job.setCreatedBy(roleUtil.getCurrentEmployee());
        job.setIsDeleted(false);

        // saving
        jobRepository.save(job);

        // returning
        return jobMapper.toJobDto(job);
    }

    @Transactional
    public JobDto updateJob(Long jobId, JobRequestDto dto) {

        if (!roleUtil.isAdmin() && !roleUtil.isHr()) {
            throw new UnauthorizedException("You cant update this.");
        }

        Job job = jobRepository.findById(jobId).orElseThrow(() -> new RuntimeException("Job not found."));

        // check if files not null
        if (dto.getJobJdFile() == null || dto.getJobJdFile().isEmpty()) {
            throw new RuntimeException("Please upload at least 1 document");
        }

        fileService.validateFile(dto.getJobJdFile());

        String filePath = fileService.store(dto.getJobJdFile(), "job");

        // converting to entity
        job.setJobTitle(dto.getJobTitle());
        job.setJobDetails(dto.getJobDetails());
        job.setExperienceYears(dto.getExperienceYears());
        job.setNumberOfVaccancy(dto.getNumberOfVaccancy());

        job.getJobCvReviewers().clear();
        job.getJobHrs().clear();

        Set<JobHr> newHrs = dto.getHrIds().stream().map(
                hr -> {
                    return JobHr.builder().job(job)
                            .hr(employeeProfileRepository.findById(Long.valueOf(hr)).orElse(null)).build();
                }).collect(Collectors.toSet());

        Set<JobCvReviewer> newRevs = dto.getCvReviewerIds().stream().map(
                rev -> {
                    return JobCvReviewer.builder().job(job)
                            .reviewer(employeeProfileRepository.findById(Long.valueOf(rev)).orElse(null)).build();
                }).collect(Collectors.toSet());

        job.getJobCvReviewers().addAll(newRevs);
        job.getJobHrs().addAll(newHrs);

        // saving
        jobRepository.save(job);

        return jobMapper.toJobDto(job);
    }

    //
    @Transactional
    public void deleteJob(Long id) {
        Job job = jobRepository.findById(id).orElseThrow(() -> new RuntimeException("Job Not Found!"));
        if (roleUtil.isAdmin() || roleUtil.isHr()) {
            job.setIsDeleted(true);
            job.setDeletedBy(roleUtil.getCurrentEmployee());

            jobRepository.save(job);
        } else {
            throw new UnauthorizedException("You are not allowed to delete this.");
        }
    }

    // public void shareById(Long id, List<String> emails) {
    @Transactional
    @Async
    public void shareById(Long id, List<EmailShareReqDto> emails) {
        // TAKE EMAIL IN DTO
        for (EmailShareReqDto email : emails) {
            JobShare jobShare = JobShare.builder()
                    .job(jobRepository.findById(id).orElseThrow(() -> new RuntimeException("Job Not Found!")))
                    .sharedOn(LocalDateTime.now()).sharedBy(roleUtil.getCurrentEmployee()).build();
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
            notificationService.sendJobReferNotification(jobReferral);
            emailService.sendReferMail(jobReferral, jobId);
            jobReferralRepository.save(jobReferral);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public List<JobReferralDto> getReferrals() {

        List<JobReferral> referrals = null;
        if (roleUtil.isHr() || roleUtil.isAdmin()) {
            referrals = jobReferralRepository.getJobReferralsHr();
        } else {

            referrals = jobReferralRepository.getJobReferrals(roleUtil.getCurrentEmployeeId());
        }

        return referrals.stream().map(jobReferral -> modelMapper.map(jobReferral, JobReferralDto.class)).toList();
    }

    @Transactional
    public void updateReferralStatus(Long referralId, ReferralStatusDto status) {
        JobReferral jobReferral = jobReferralRepository.findById(referralId)
                .orElseThrow(() -> new RuntimeException("Referral Not Found"));
        if (roleUtil.isAdmin() || roleUtil.isHr()) {
            jobReferral.setStatus(status.getStatus());
            jobReferralRepository.save(jobReferral);
        } else {
            throw new UnauthorizedException("You Can't update status");
        }
        return;
    }

}