package com.roima.hrms.mapper.job;

import com.roima.hrms.dtos.req.JobRequestDto;
import com.roima.hrms.dtos.res.JobDto;
import com.roima.hrms.entities.*;
import com.roima.hrms.repository.EmployeeProfileRepository;
import com.roima.hrms.repository.ExpenseDocumentRepository;
import com.roima.hrms.utils.RoleUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Component
public class JobMapper {

    private final ModelMapper modelMapper;
    private final ExpenseDocumentRepository expenseDocumentRepository;
    private final RoleUtil roleUtil;
    private final EmployeeProfileRepository employeeProfileRepository;

    public JobMapper(ModelMapper modelMapper, ExpenseDocumentRepository expenseDocumentRepository, RoleUtil roleUtil, EmployeeProfileRepository employeeProfileRepository) {
        this.modelMapper = modelMapper;
        this.expenseDocumentRepository = expenseDocumentRepository;
        this.roleUtil = roleUtil;
        this.employeeProfileRepository = employeeProfileRepository;
    }

    public Job toEntity(JobDto dto) {
        return modelMapper.map(dto, Job.class);
    }

    public Job toEntity(JobRequestDto dto) {

        EmployeeProfile currentEmployee = roleUtil.getCurrentEmployee();

        List<EmployeeProfile> hrs = employeeProfileRepository.findAllById(dto.getHrIds());
        List<EmployeeProfile> reviewers = employeeProfileRepository.findAllById(dto.getCvReviewerIds());

        Job job = Job
                .builder()
                .jobTitle(dto.getJobTitle())
                .jobDetails(dto.getJobDetails())
                .experienceYears(dto.getExperienceYears())
                .numberOfVaccancy(dto.getNumberOfVaccancy())
                .createdBy(currentEmployee)
                .createdOn(LocalDateTime.now())
                .updatedBy(currentEmployee)
                .updatedOn(LocalDateTime.now())
                .status("active")
                .statusChangedOn(LocalDateTime.now())
                .build();

        Set<JobHr> jobHrs = hrs.stream().map(hr -> JobHr.builder().job(job).hr(hr).build()).collect(Collectors.toSet());
        Set<JobCvReviewer> jobCvReviewers = reviewers.stream().map(reviewer->JobCvReviewer.builder().job(job).reviewer(reviewer).build()).collect(Collectors.toSet());

        job.setJobHrs(jobHrs);
        job.setJobCvReviewers(jobCvReviewers);

        return job;
    }

    public JobDto toJobDto(Job job) {
//        List<ExpenseDocument> docs = expenseDocumentRepository.findExpenseDocumentByJob(job);
        var a = modelMapper.map(job, JobDto.class);
//        a.setExpenseDocumentFilePaths(docs.stream()
//        .map(ExpenseDocument::getFilePath).toList());
        return a;
    }

//    public Job updateEntity(Job job, JobRequestDto){
//        job.get
//    }

    public List<JobDto> toJobDtoList(List<Job> jobList) {
        return jobList.stream().map(this::toJobDto).collect(Collectors.toList());
    }

    public Job toUpdateEntity(Job job, JobRequestDto jobDto) {
//        job.setSubmitStatus(jobDto.getSubmitStatus());
//        job.setExpenseUploadDate(LocalDate.parse(jobDto.getExpenseUploadDate()));
//        job.setExpenseAmount(jobDto.getExpenseAmount());
//        job.setExpenseDate(LocalDate.parse(jobDto.getExpenseDate()));
//        job.setStatus(jobDto.getStatus());
//        job.setRemark(jobDto.getRemark());
        return job;
    }
}
