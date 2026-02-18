package com.roima.hrms.mapper.job;

import com.roima.hrms.dtos.req.JobRequestDto;
import com.roima.hrms.dtos.res.JobDto;
import com.roima.hrms.entities.ExpenseDocument;
import com.roima.hrms.entities.Job;
import com.roima.hrms.repository.ExpenseDocumentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class JobMapper {

    private final ModelMapper modelMapper;
    private final ExpenseDocumentRepository expenseDocumentRepository;

    public JobMapper(ModelMapper modelMapper, ExpenseDocumentRepository expenseDocumentRepository) {
        this.modelMapper = modelMapper;
        this.expenseDocumentRepository = expenseDocumentRepository;
    }

    public Job toEntity(JobDto dto){
        return modelMapper.map(dto,Job.class);
    }

    public Job toEntity(JobRequestDto dto){

        return modelMapper.map(dto,Job.class);
    }

    public JobDto toJobDto(Job job){
//        List<ExpenseDocument> docs = expenseDocumentRepository.findExpenseDocumentByJob(job);
        var a = modelMapper.map(job, JobDto.class);
//        a.setExpenseDocumentFilePaths(docs.stream().map(ExpenseDocument::getFilePath).toList());
        return a;
    }

//    public Job updateEntity(Job job, JobRequestDto){
//        job.get
//    }

    public List<JobDto> toJobDtoList(List<Job> jobList){
        return jobList.stream().map(this::toJobDto).collect(Collectors.toList());
    }

    public Job toUpdateEntity(Job job, JobRequestDto jobDto){
//        job.setSubmitStatus(jobDto.getSubmitStatus());
//        job.setExpenseUploadDate(LocalDate.parse(jobDto.getExpenseUploadDate()));
//        job.setExpenseAmount(jobDto.getExpenseAmount());
//        job.setExpenseDate(LocalDate.parse(jobDto.getExpenseDate()));
//        job.setStatus(jobDto.getStatus());
//        job.setRemark(jobDto.getRemark());
        return  job;
    }
}
