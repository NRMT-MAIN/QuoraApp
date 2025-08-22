package com.example.quoraReactiveApp.repositories;

import com.example.quoraReactiveApp.models.LikeEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface LikeRepository extends ReactiveMongoRepository<LikeEntity , String> {
}
