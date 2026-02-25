package com.roima.hrms.seeder.job;

import com.roima.hrms.entities.*;
import com.roima.hrms.exceptions.EmployeeNotFoundException;
import com.roima.hrms.repository.EmployeeProfileRepository;
import com.roima.hrms.repository.JobRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Set;

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
        EmployeeProfile hr1 = employeeProfileRepository.getEmployeeProfileByUser_Email("hr1@exp.com");
        EmployeeProfile hr2 = employeeProfileRepository.getEmployeeProfileByUser_Email("hr2@exp.com");

        EmployeeProfile emp1 = employeeProfileRepository.findById(4L).orElseThrow();
        EmployeeProfile emp2 = employeeProfileRepository.findById(5L).orElseThrow();
        EmployeeProfile emp3 = employeeProfileRepository.findById(6L).orElseThrow();
        EmployeeProfile emp4 = employeeProfileRepository.findById(7L).orElseThrow();

        if (!jobRepository.existsJobByJobTitle("New Job 1")) {

            Job job1 = Job.builder()
                    .jobTitle("New Job 1")
                    .jobDetails(
                            "Lorem ipsum, dolor sit amet consectetur adipisicing elit. Id sunt cum nesciunt ullam vitae quo soluta beatae itaque omnis. Ex quo voluptatem quos repellat. Quia omnis cumque vero? Aliquam, nesciunt.")
                    .experienceYears(4)
                    .numberOfVaccancy(2)
                    .createdBy(hr1)
                    .createdOn(LocalDateTime.now())
                    .updatedBy(hr2)
                    .updatedOn(LocalDateTime.now())
                    .status("active")
                    .statusChangedOn(LocalDateTime.now())
                    .isDeleted(false)
                    .build();

            JobHr jobHr1 = JobHr.builder().job(job1).hr(hr1).build();
            JobHr jobHr2 = JobHr.builder().job(job1).hr(hr2).build();

            job1.setJobHrs(Set.of(jobHr1, jobHr2));

            JobCvReviewer cvReviewer1 = JobCvReviewer.builder().job(job1).reviewer(emp1).build();
            JobCvReviewer cvReviewer2 = JobCvReviewer.builder().job(job1).reviewer(emp2).build();
            JobCvReviewer cvReviewer3 = JobCvReviewer.builder().job(job1).reviewer(emp3).build();

            job1.setJobCvReviewers(Set.of(cvReviewer1, cvReviewer2, cvReviewer3));

            JobJdFile jobJdFile = JobJdFile.builder().job(job1).filePath("22cd4966-0ced-11f1-9cba-325096b39f47.pdf")
                    .uploadedOn(LocalDateTime.now()).uploadedBy(hr1).build();

            job1.setJobJdFile(jobJdFile);

            jobRepository.save(job1);
        }

        if (!jobRepository.existsJobByJobTitle("New Job 2")) {
            Job job2 = Job.builder()
                    .jobTitle("New Job 2")
                    .jobDetails(
                            "Lorem ipsum, dolor sit amet consectetur adipisicing elit. Id sunt cum nesciunt ullam vitae quo soluta beatae itaque omnis. Ex quo voluptatem quos repellat. Quia omnis cumque vero? Aliquam, nesciunt.")
                    .experienceYears(1)
                    .numberOfVaccancy(1)
                    .createdBy(hr2)
                    .createdOn(LocalDateTime.now())
                    .updatedBy(hr1)
                    .updatedOn(LocalDateTime.now())
                    .status("active")
                    .statusChangedOn(LocalDateTime.now())
                    .build();

            JobHr jobHr1 = JobHr.builder().job(job2).hr(hr1).build();

            job2.setJobHrs(Set.of(jobHr1));

            JobCvReviewer cvReviewer2 = JobCvReviewer.builder().job(job2).reviewer(emp2).build();
            JobCvReviewer cvReviewer3 = JobCvReviewer.builder().job(job2).reviewer(emp3).build();
            JobCvReviewer cvReviewer4 = JobCvReviewer.builder().job(job2).reviewer(emp4).build();

            job2.setJobCvReviewers(Set.of(cvReviewer2, cvReviewer3, cvReviewer4));

            JobJdFile jobJdFile = JobJdFile.builder().job(job2).filePath("fdf5e36e-0cec-11f1-83f1-325096b39f47.pdf")
                    .uploadedOn(LocalDateTime.now()).uploadedBy(hr2).build();

            job2.setJobJdFile(jobJdFile);

            jobRepository.save(job2);
        }

    }

}
