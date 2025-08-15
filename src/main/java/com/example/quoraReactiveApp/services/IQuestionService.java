package com.example.quoraReactiveApp.services;

import com.example.quoraReactiveApp.dtos.QuestionRequestDTO;
import com.example.quoraReactiveApp.dtos.QuestionResponseDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IQuestionService {
    Mono<QuestionResponseDTO> createQuestion(QuestionRequestDTO questionRequestDTO)  ;
    Mono<QuestionResponseDTO> getById(String id) ;
    Flux<QuestionResponseDTO> getAll(String cursor , int size) ;
    Mono<Void> delete(String id) ;
    Flux<QuestionResponseDTO> searchQuestion(String searchTerm , int offset , int page) ;
}
