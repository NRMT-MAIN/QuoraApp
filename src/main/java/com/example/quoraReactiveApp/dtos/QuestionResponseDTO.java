package com.example.quoraReactiveApp.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class QuestionResponseDTO {
    private String id ;
    private String title ;
    private String content ;
    private LocalDateTime createdAt ;
}
