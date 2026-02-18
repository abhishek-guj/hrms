package com.roima.hrms.seeder.job;


import com.roima.hrms.entities.EmployeeProfile;
import com.roima.hrms.entities.Job;
import com.roima.hrms.exceptions.EmployeeNotFoundException;
import com.roima.hrms.repository.EmployeeProfileRepository;
import com.roima.hrms.repository.JobRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@Order(7)
public class JobSeeder implements ApplicationListener<ContextRefreshedEvent> {
    private final JobRepository jobRepository;
    private final EmployeeProfileRepository employeeProfileRepository;

    public JobSeeder(JobRepository jobRepository, EmployeeProfileRepository employeeProfileRepository) {
        this.jobRepository = jobRepository;
        this.employeeProfileRepository = employeeProfileRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.loadJobs();
    }


    public void loadJobs() {
        EmployeeProfile hr = employeeProfileRepository.findById(2L).orElseThrow(EmployeeNotFoundException::new);


        if (!jobRepository.existsJobByJobTitle("New Job 1")) {
            Job job1 = Job.builder()
                    .jobTitle("New Job 1")
                    .jobDetails("Lorem ipsum, dolor sit amet consectetur adipisicing elit. Id sunt cum nesciunt ullam vitae quo soluta beatae itaque omnis. Ex quo voluptatem quos repellat. Quia omnis cumque vero? Aliquam, nesciunt.")
                    .experienceYears(4)
                    .numberOfVaccancy(2)
                    .createdBy(hr)
                    .createdOn(LocalDateTime.now())
                    .updatedBy(hr)
                    .updatedOn(LocalDateTime.now())
                    .status("active")
                    .statusChangedOn(LocalDateTime.now())

                    .build();
            jobRepository.save(job1);
        }


        if (!jobRepository.existsJobByJobTitle("New Job 2")) {
            Job job2 = Job.builder()
                    .jobTitle("New Job 2")
                    .jobDetails("Lorem ipsum, dolor sit amet consectetur adipisicing elit. Id sunt cum nesciunt ullam vitae quo soluta beatae itaque omnis. Ex quo voluptatem quos repellat. Quia omnis cumque vero? Aliquam, nesciunt.")
                    .experienceYears(1)
                    .numberOfVaccancy(1)
                    .createdBy(hr)
                    .createdOn(LocalDateTime.now())
                    .updatedBy(hr)
                    .updatedOn(LocalDateTime.now())
                    .status("active")
                    .statusChangedOn(LocalDateTime.now())
                    .build();
            jobRepository.save(job2);
        }



    }

}
