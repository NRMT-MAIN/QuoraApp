package com.example.quoraReactiveApp.repositories;

import com.example.quoraReactiveApp.models.QuestionEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

@Repository
public interface QuestionRepository extends ReactiveMongoRepository<QuestionEntity , String> {
    @Query("{'$or' : [{'title' : {$regex :?0,$options:'i'}} ,{'content' : : {$regex :?0,$options:'i'}} ]}")
    Flux<QuestionEntity> findByTitleOrContentContainingIgnoreCase(String searchTerm , Pageable pageable) ;

    Flux<QuestionEntity> findByCreatedAtGreaterThanOrderByCreatedAtAsc(LocalDateTime cursor, Pageable pageable);

    Flux<QuestionEntity> findTop10ByOrderByCreatedAtAsc();
}
