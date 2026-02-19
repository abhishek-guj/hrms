package com.roima.hrms.services;

import com.roima.hrms.entities.*;
import com.roima.hrms.repository.EmployeeProfileRepository;
import com.roima.hrms.repository.JobCvReviewerRepository;
import com.roima.hrms.repository.JobHrRepository;
import com.roima.hrms.repository.JobRepository;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmailService {
    private final EmployeeProfileRepository employeeProfileRepository;
    private final JobRepository jobRepository;
    private final JobHrRepository jobHrRepository;
    private final JobCvReviewerRepository jobCvReviewerRepository;
    @Value("${frontend.url}")
    String baseUrl;

    public EmailService(EmployeeProfileRepository employeeProfileRepository, JobRepository jobRepository, JobHrRepository jobHrRepository, JobCvReviewerRepository jobCvReviewerRepository) {
        this.employeeProfileRepository = employeeProfileRepository;
        this.jobRepository = jobRepository;
        this.jobHrRepository = jobHrRepository;
        this.jobCvReviewerRepository = jobCvReviewerRepository;
    }

    @Async
    public void sendShareMail(String email, Long jobId) throws MessagingException, IOException, MessagingException {

        try {

            Session session = createSession();

            Message msg = createShareMail(session, email, jobId);

            Transport.send(msg);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Async
    public void sendReferMail(JobReferral jobReferral, Long jobId) throws MessagingException, IOException, MessagingException {
        Job job = jobRepository.findById(jobId).orElseThrow(() -> new RuntimeException("Job not found."));
        // getting job hrs
        Set<JobHr> jobHrs = jobHrRepository.findAllByJob(job);
        // getting job reviewers
        Set<JobCvReviewer> jobCvReviewers = jobCvReviewerRepository.findAllByJob(job);

        List<String> emails = new ArrayList<>();

        jobHrs.forEach(jobHr -> emails.add(jobHr.getHr().getUser().getEmail()));
        jobCvReviewers.forEach(jobHr -> emails.add(jobHr.getReviewer().getUser().getEmail()));

        try {
            Session session = createSession();

            Message msg = createReferMail(session, emails, jobReferral, jobId);

            Transport.send(msg);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private Session createSession() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        return Session.getInstance(props, new jakarta.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("skelireverbs@gmail.com", "srte szzv bfsa uitk");
            }
        });
    }

    private Message createShareMail(Session session, String email, Long jobId) throws IOException, MessagingException {

        Job job = jobRepository.findById(jobId).orElseThrow(() -> new RuntimeException("Job not found."));
        String filePath = getFilePath(job.getJobJdFile().getFilePath(), "job");

        String subject = "Great opportunity - %s at Roima Intelligence India".formatted(job.getJobTitle());
        String body = shareMailBody(job);

        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("skelireverbs@gmail.com", false));

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
        msg.setSubject(subject);
//        msg.setContent(body, "text/html");
        msg.setSentDate(new Date());

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(body, "text/html");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        MimeBodyPart attachPart = new MimeBodyPart();

        attachPart.attachFile(filePath);
        multipart.addBodyPart(attachPart);
        msg.setContent(multipart);

        return msg;
    }

    private Message createReferMail(Session session, List<String> emails, JobReferral jobReferral, Long jobId) throws IOException, MessagingException {

        // getting job details
        Job job = jobRepository.findById(jobId).orElseThrow(() -> new RuntimeException("Job not found."));

        // getting its file path
        String filePath = getFilePath(jobReferral.getCvPath(), "cv");

        // setting mail subject
        String subject = "Job Referral - %s by %s".formatted(job.getJobTitle(), jobReferral.getReferredBy().getFirstName() + " " + jobReferral.getReferredBy().getFirstName());

        // getting mail body
        String body = referMailBody(job, jobReferral, jobReferral.getReferredBy());

        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("skelireverbs@gmail.com", false));

        String emailsParse = emails.stream()
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(email -> !email.isEmpty())
                .collect(Collectors.joining(","));


//        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailsParse));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("skelireverbs@gmail.com"));
        msg.setSubject(subject);
//        msg.setContent(body, "text/html");
        msg.setSentDate(new Date());

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(body, "text/html");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        MimeBodyPart attachPart = new MimeBodyPart();

        attachPart.attachFile(filePath);
        multipart.addBodyPart(attachPart);
        msg.setContent(multipart);

        return msg;
    }

    private String shareMailBody(Job job) {

        String jobLink = baseUrl + "jobs/public/" + job.getId();

        EmployeeProfile hr = employeeProfileRepository.getEmployeeProfileByUser_Email("hr1@exp.com");

        String body = """
                 Hello,
                 <br> 
                 <br>An employee recommended you for the job position of %s.
                 <br>
                 <br><b>Position:</b> %s
                 <br><b>Job Description:</b> %s
                 <br><b>Job Link:</b> <a href="%s">%s</a>
                 <br>Job Description pdf is attached below.
                 <br>
                 <br><b>Best Regards,</b>
                 <br>%s
                 <br>%s
                 <br><a href="tel:+%s">%s</a> | <a href="mailto:%s">%s</a>
                 <br><a href="https://www.roimaint.com/">Roima Intelligence India</a>
                """.formatted(job.getJobTitle(),
                job.getJobTitle(),
                job.getJobDetails(),
                jobLink, job.getJobTitle(),
                hr.getFirstName() + " " + hr.getLastName(),
                "Hr",
                hr.getContactNumber(),
                hr.getContactNumber(),
                hr.getUser().getEmail(),
                hr.getUser().getEmail()
        );


        return body;
    }

    private String referMailBody(Job job, JobReferral jobReferral, EmployeeProfile referEmp) {

        String jobLink = baseUrl + "jobs/" + job.getId();

        EmployeeProfile hr = employeeProfileRepository.getEmployeeProfileByUser_Email("hr1@exp.com");

        String body = """
                 Hello All,
                 <br> 
                 <br>%s recommended  for the job position of %s.
                 <br>
                 <br><b>Job Title:</b> %s
                 <br><b>Job Description:</b> %s
                 <br><b>Job Link:</b> <a href="%s">%s</a>
                 <br>
                 <br>Referred By.
                 <br><b>Name:</b> %s
                 <br><b>Employee Id:</b> %s
                 <br>
                 <br>Referred person details.
                 <br><b>Name:</b> %s
                 <br><b>Email:</b> <a href="mailto:%s">%s</a>
                 <br>Cv Attached below.
                 <br>
                 <br><b>Best Regards,</b>
                 <br>%s
                 <br>%s
                 <br><a href="tel:+%s">%s</a> | <a href="mailto:%s">%s</a>
                 <br><a href="https://www.roimaint.com/">Roima Intelligence India</a>
                """.formatted(
                referEmp.getFirstName() + " " + referEmp.getLastName(), job.getJobTitle(),
                job.getJobTitle(),
                job.getJobDetails(),
                jobLink, job.getJobTitle(),
                referEmp.getFirstName() + " " + referEmp.getLastName(),
                referEmp.getId(),
                jobReferral.getFirstName() + " " + jobReferral.getLastName(),
                jobReferral.getEmail(),
                jobReferral.getEmail(),
                hr.getFirstName() + " " + hr.getLastName(),
                "Hr",
                hr.getContactNumber(),
                hr.getContactNumber(),
                hr.getUser().getEmail(),
                hr.getUser().getEmail()
        );


        return body;
    }


    private String getFilePath(String fileName,String docType) {
        String BASE_PATH = "src/main/resources/"+docType;

        return BASE_PATH + "/" + fileName;
    }

}
