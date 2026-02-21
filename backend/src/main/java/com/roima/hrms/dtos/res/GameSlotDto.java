package com.roima.hrms.dtos.res;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class GameSlotDto{
    private Long id;
    private Long gameTypeId;
    private LocalDateTime slotStart;
    private LocalDateTime slotEnd;
}