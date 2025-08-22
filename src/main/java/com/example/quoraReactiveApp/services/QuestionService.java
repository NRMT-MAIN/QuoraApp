package com.example.quoraReactiveApp.services;

import com.example.quoraReactiveApp.adpaters.QuestionAdapter;
import com.example.quoraReactiveApp.dtos.QuestionRequestDTO;
import com.example.quoraReactiveApp.dtos.QuestionResponseDTO;
import com.example.quoraReactiveApp.events.ViewCountEvent;
import com.example.quoraReactiveApp.models.QuestionEntity;
import com.example.quoraReactiveApp.producers.KafkaEventProducer;
import com.example.quoraReactiveApp.repositories.QuestionRepository;
import com.example.quoraReactiveApp.utils.CursorUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class QuestionService implements IQuestionService {

    private final QuestionRepository questionRepository ;
    private final KafkaEventProducer kafkaEventProducer ;

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

    @Override
    public Mono<QuestionResponseDTO> getById(String id) {
        return this.questionRepository.findById(id)
                .doOnSuccess(response -> {
                    System.out.println("Question fetched successfully: " + response) ;
                    ViewCountEvent viewCountEvent = new ViewCountEvent(id , "question" , LocalDateTime.now()) ;
                    kafkaEventProducer.publishViewCount(viewCountEvent);
                })
                .doOnError(error -> System.out.println("Error in fetching by id " + error))
                .map(QuestionAdapter::toQuestionResponseDTO); // Convert inside the reactive chain
    }

    @Override
    public Flux<QuestionResponseDTO> getAll(String cursor, int size) {
        Pageable pageable = PageRequest.of(0  , size) ;

        if(!CursorUtils.isValidCursor(cursor)){
            return questionRepository.findTop10ByOrderByCreatedAtAsc()
                    .take(size)
                    .map(QuestionAdapter::toQuestionResponseDTO)
                    .doOnError(error -> System.out.println("Error fetching questions: " + error))
                    .doOnComplete(() -> System.out.println("Questions fetched successfully"));
        } else {
            LocalDateTime cursorTimeStamp = CursorUtils.parseCursor(cursor);
            return questionRepository.findByCreatedAtGreaterThanOrderByCreatedAtAsc(cursorTimeStamp, pageable)
                    .map(QuestionAdapter::toQuestionResponseDTO)
                    .doOnError(error -> System.out.println("Error fetching questions: " + error))
                    .doOnComplete(() -> System.out.println("Questions fetched successfully"));
        }
    }

    @Override
    public Flux<QuestionResponseDTO> searchQuestion(String searchTerm, int offset, int page) {
        return this.questionRepository.findByTitleOrContentContainingIgnoreCase(searchTerm , PageRequest.of(offset , page))
                .map(QuestionAdapter::toQuestionResponseDTO)
                .doOnError(error -> System.out.println("Error in searching the question : " + error))
                .doOnComplete(() -> System.out.println("Questions searched successfully")) ;
    }

    @Override
    public Mono<Void> delete(String id) {
        questionRepository.deleteById(id)
                .doOnSuccess(response -> System.out.println("Question deleted successfully! : " + response))
                .doOnError(error -> System.out.println("Error in deleting question : " + error));
        return Mono.empty();
    }
}
