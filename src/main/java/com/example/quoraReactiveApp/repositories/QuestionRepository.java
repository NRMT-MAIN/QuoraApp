package com.example.quoraReactiveApp.repositories;

import com.example.quoraReactiveApp.models.QuestionEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends ReactiveMongoRepository<QuestionEntity , String> {

}
