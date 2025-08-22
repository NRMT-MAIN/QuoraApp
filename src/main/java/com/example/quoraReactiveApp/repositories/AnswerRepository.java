package com.example.quoraReactiveApp.repositories;

import com.example.quoraReactiveApp.models.AnswersEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface AnswerRepository extends ReactiveMongoRepository<AnswersEntity , String> {
}
