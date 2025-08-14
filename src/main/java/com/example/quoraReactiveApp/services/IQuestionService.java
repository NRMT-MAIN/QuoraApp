package com.example.quoraReactiveApp.services;

import com.example.quoraReactiveApp.dtos.QuestionRequestDTO;
import com.example.quoraReactiveApp.dtos.QuestionResponseDTO;
import reactor.core.publisher.Mono;

public interface IQuestionService {
    Mono<QuestionResponseDTO> createQuestion(QuestionRequestDTO questionRequestDTO)  ;
}
