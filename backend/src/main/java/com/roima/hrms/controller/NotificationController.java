package com.roima.hrms.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.roima.hrms.dtos.res.JobDto;
import com.roima.hrms.dtos.res.NotificationDto;
import com.roima.hrms.enums.ApiResponseType;
import com.roima.hrms.response.ApiResponse;
import com.roima.hrms.services.NotificationService;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/notifications")
@CrossOrigin(origins = "*")
@Tag(name = "Notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getNotifications() {
        List<NotificationDto> notificationList = notificationService.getAllNotifications();
        ApiResponse<List<NotificationDto>> res = ApiResponse.createApiResponse(ApiResponseType.SUCCESS,
                "Fetched all Notifications successfully", notificationList, null);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PostMapping("{messageId}")
    public ResponseEntity<ApiResponse> postMethodName(@PathVariable Long messageId) {
        notificationService.readNotification(messageId);
        ApiResponse<Boolean> res = ApiResponse.createApiResponse(ApiResponseType.SUCCESS,
                "Fetched all Notifications successfully", true, null);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

}
