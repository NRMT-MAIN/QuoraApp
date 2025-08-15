package com.example.quoraReactiveApp.controllers;

import com.example.quoraReactiveApp.dtos.QuestionRequestDTO;
import com.example.quoraReactiveApp.dtos.QuestionResponseDTO;
import com.example.quoraReactiveApp.services.IQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/question")
public class QuestionController {
    private final IQuestionService questionService ;

    @PostMapping()
    public Mono<QuestionResponseDTO> createQuestion(@RequestBody QuestionRequestDTO questionRequestDTO){
        return this.questionService.createQuestion(questionRequestDTO)
                .doOnSuccess(response -> System.out.println("Question created successfully: " + response))
                .doOnError(error -> System.out.println("Error in creating question : " + error)) ;
    }

    @GetMapping("/{id}")
    public Mono<QuestionResponseDTO> getQuestionById(@PathVariable String id) {
        return this.questionService.getById(id)
                .doOnSuccess(response -> System.out.println("Fetched question succesfully : " + response))
                .doOnError(error -> System.out.println("Error in getting question by id : " + error)) ;
    }

    @GetMapping()
    public Flux<QuestionResponseDTO> getAllQuestions(
            @RequestParam(required = false) String cursor,
            @RequestParam(defaultValue = "10") int size
    ) {
        return questionService.getAll(cursor, size)
                .doOnError(error -> System.out.println("Error fetching questions: " + error))
                .doOnComplete(() -> System.out.println("Questions fetched successfully"));
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteQuestionById(@PathVariable String id) {
        return this.questionService.delete(id)
                .doOnSuccess(respone -> System.out.println("Question deleted successfully! : " + respone))
                .doOnError(error -> System.out.println("Error in deleting question : " + error));
    }

    @GetMapping("/search")
    public Flux<QuestionResponseDTO> searchQuestions(
            @RequestParam String query,
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int page
    ) {
        return questionService.searchQuestion(query, offset, page);
    }

    @GetMapping("/tag/{tag}")
    public Flux<QuestionResponseDTO> getQuestionsByTag(@PathVariable String tag,
                                                       @RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "10") int size
    ) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
