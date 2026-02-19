package com.roima.hrms.services;

import com.roima.hrms.dtos.res.NotificationDto;
import com.roima.hrms.entities.*;
import com.roima.hrms.repository.*;
import com.roima.hrms.utils.RoleUtil;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final JobHrRepository jobHrRepository;
    private final JobCvReviewerRepository jobCvReviewerRepository;
    private final JobRepository jobRepository;
    private final RoleUtil roleUtil;
    private final ModelMapper modelMapper;
    private final TravelEmployeeRepository travelEmployeeRepository;
    private final EmployeeProfileRepository employeeProfileRepository;

    public NotificationService(NotificationRepository notificationRepository, JobHrRepository jobHrRepository, JobCvReviewerRepository jobCvReviewerRepository, JobRepository jobRepository, RoleUtil roleUtil, ModelMapper modelMapper, TravelEmployeeRepository travelEmployeeRepository, EmployeeProfileRepository employeeProfileRepository) {
        this.notificationRepository = notificationRepository;
        this.jobHrRepository = jobHrRepository;
        this.jobCvReviewerRepository = jobCvReviewerRepository;
        this.jobRepository = jobRepository;
        this.roleUtil = roleUtil;
        this.modelMapper = modelMapper;
        this.travelEmployeeRepository = travelEmployeeRepository;
        this.employeeProfileRepository = employeeProfileRepository;
    }

    public List<NotificationDto> getAllNotifications() {
        List<Notification> notifications = notificationRepository.findAllByEmployeeProfile(roleUtil.getCurrentEmployee());

        return notifications.stream().map(noti -> modelMapper.map(noti, NotificationDto.class)).toList();
    }

    @Transactional
    public void readNotification(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId).orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setRead(true);
        notification.setReadAt(LocalDateTime.now());

        notificationRepository.save(notification);
    }

    @Transactional
    public void sendJobReferNotification(JobReferral jobReferral) {
        // getting job hrs
        Set<JobHr> jobHrs = jobHrRepository.findAllByJob(jobReferral.getJob());
        // getting job reviewers
        Set<JobCvReviewer> jobCvReviewers = jobCvReviewerRepository.findAllByJob(jobReferral.getJob());

        String content = getReferContent(jobReferral);

        jobHrs.forEach(jobHr -> createNotification(jobHr.getHr(), content));
        jobCvReviewers.forEach(jobCvReviewer -> createNotification(jobCvReviewer.getReviewer(), content));

    }

    @Transactional
    public void sendTravelPlanNotification(TravelPlan travelPlan) {
        // getting employees
        Set<EmployeeProfile> employeeProfiles = travelEmployeeRepository.getAllEmployeeProfilesByTravelPlan_Id(travelPlan.getId());

        String content = getTravelPlanContent(travelPlan);

        employeeProfiles.forEach(employeeProfile -> createNotification(employeeProfile, content));

    }

    protected void createNotification(EmployeeProfile employeeProfile, String content) {
        Notification notification = Notification.builder()
                .employeeProfile(employeeProfile)
                .isRead(false)
                .createdAt(LocalDateTime.now())
                .content(content)
                .build();

        notificationRepository.save(notification);
    }

    private String getReferContent(JobReferral jobReferral) {
        EmployeeProfile referer = jobReferral.getReferredBy();
        String referredName = jobReferral.getFirstName() + " " + jobReferral.getLastName();
        String content = """
                %s referred %s for %s at %s.
                """.formatted(
                referer.getFirstName() + " " + referer.getLastName(),
                referredName,
                jobReferral.getJob().getJobTitle(),
                jobReferral.getReferredOn()
        );


        return content;
    }

    private String getTravelPlanContent(TravelPlan travelPlan) {
        String content = """
                You are assigned to %s Travel: %s.
                """.formatted(
                travelPlan.getTravelType().getName(),
                travelPlan.getPurpose()
        );
        return content;
    }

}
