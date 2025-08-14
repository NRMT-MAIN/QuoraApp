package com.example.quoraReactiveApp.services;

import com.example.quoraReactiveApp.adpaters.QuestionAdapter;
import com.example.quoraReactiveApp.dtos.QuestionRequestDTO;
import com.example.quoraReactiveApp.dtos.QuestionResponseDTO;
import com.example.quoraReactiveApp.models.QuestionEntity;
import com.example.quoraReactiveApp.repositories.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class QuestionService implements IQuestionService {

    private final QuestionRepository questionRepository ;

    @Override
    public Mono<QuestionResponseDTO> createQuestion(QuestionRequestDTO questionRequestDTO) {
        QuestionEntity question = QuestionEntity.builder()
                .title(questionRequestDTO.getTitle())
                .content(questionRequestDTO.getContent())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return questionRepository.save(question)
                .map(QuestionAdapter :: toQuestionResponseDTO)
                .doOnSuccess(response -> System.out.println("Question created successfully: " + response))
                .doOnError(error -> System.out.println("Error creating question: " + error)) ;
    }
}
