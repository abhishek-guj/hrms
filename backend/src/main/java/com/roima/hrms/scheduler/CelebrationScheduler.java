package com.roima.hrms.scheduler;

import com.roima.hrms.entities.EmployeeProfile;
import com.roima.hrms.repository.EmployeeProfileRepository;
import com.roima.hrms.services.AchievementPostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

/**
 * Daily scheduler that auto-creates system posts for:
 *  1. Employee birthdays
 *  2. Employee work anniversaries (1+ years)
 *
 * Runs every day at 08:00 AM server time.
 */
@Component
public class CelebrationScheduler {

    private static final Logger log = LoggerFactory.getLogger(CelebrationScheduler.class);

    private final EmployeeProfileRepository employeeProfileRepository;
    private final AchievementPostService achievementPostService;

    public CelebrationScheduler(EmployeeProfileRepository employeeProfileRepository,
                                 AchievementPostService achievementPostService) {
        this.employeeProfileRepository = employeeProfileRepository;
        this.achievementPostService = achievementPostService;
    }

    /**
     * Runs daily at 08:00 AM.
     * Checks all active employees for birthday or work anniversary today.
     */
    @Scheduled(cron = "0 0 8 * * *")
    public void generateDailyCelebrationPosts() {
        LocalDate today = LocalDate.now();
        int todayMonth = today.getMonthValue();
        int todayDay = today.getDayOfMonth();
        int todayYear = today.getYear();

        log.info("CelebrationScheduler running for date: {}", today);

        List<EmployeeProfile> allEmployees = employeeProfileRepository.findAll();

        for (EmployeeProfile employee : allEmployees) {
            // Skip deleted employees
            if (Boolean.TRUE.equals(employee.getIsDeleted())) continue;

            // ── Birthday check ──
            if (employee.getBirthDate() != null) {
                LocalDate bd = employee.getBirthDate();
                if (bd.getMonthValue() == todayMonth && bd.getDayOfMonth() == todayDay) {
                    try {
                        achievementPostService.createBirthdayPost(employee);
                        log.info("Birthday post created for employee: {} {}", employee.getFirstName(), employee.getLastName());
                    } catch (Exception e) {
                        log.error("Failed to create birthday post for employee id={}: {}", employee.getId(), e.getMessage());
                    }
                }
            }

            // ── Work Anniversary check ──
            if (employee.getJoiningDate() != null) {
                LocalDate jd = employee.getJoiningDate();
                if (jd.getMonthValue() == todayMonth && jd.getDayOfMonth() == todayDay) {
                    int yearsCompleted = todayYear - jd.getYear();
                    if (yearsCompleted >= 1) {
                        try {
                            achievementPostService.createWorkAnniversaryPost(employee, yearsCompleted);
                            log.info("Work anniversary post created for employee: {} {} - {} year(s)",
                                    employee.getFirstName(), employee.getLastName(), yearsCompleted);
                        } catch (Exception e) {
                            log.error("Failed to create anniversary post for employee id={}: {}", employee.getId(), e.getMessage());
                        }
                    }
                }
            }
        }
    }
}
