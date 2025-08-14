package com.example.quoraReactiveApp.adpaters;

import com.example.quoraReactiveApp.dtos.QuestionResponseDTO;
import com.example.quoraReactiveApp.models.QuestionEntity;

public class QuestionAdapter {
    public static QuestionResponseDTO toQuestionResponseDTO(QuestionEntity questionEntity){
        return QuestionResponseDTO.builder()
                .id(questionEntity.getId())
                .title(questionEntity.getTitle())
                .content(questionEntity.getContent())
                .createdAt(questionEntity.getCreatedAt())
                .build();
    }
}
