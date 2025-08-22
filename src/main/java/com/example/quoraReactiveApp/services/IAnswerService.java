package com.example.quoraReactiveApp.services;

import com.example.quoraReactiveApp.dtos.AnswerRequestDTO;
import com.example.quoraReactiveApp.dtos.AnswerResponseDTO;
import reactor.core.publisher.Mono;

public interface IAnswerService {
    public Mono<AnswerResponseDTO> createAnswer(AnswerRequestDTO answerRequestDTO);

    public Mono<AnswerResponseDTO> getAnswerById(String id);
}
