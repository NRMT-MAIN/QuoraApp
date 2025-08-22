package com.example.quoraReactiveApp.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection="likes")
public class LikeEntity {
    @Id
    private String id ;

    private String targetId ;

    private String targetType ;

    private Boolean isLike ;

    @CreatedDate
    private LocalDateTime createdAt ;
}
