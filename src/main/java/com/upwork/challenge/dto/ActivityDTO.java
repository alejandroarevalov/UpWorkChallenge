package com.upwork.challenge.dto;

import java.time.LocalDateTime;

public record ActivityDTO (String name, LocalDateTime startDate, LocalDateTime endDate, String code){
}
