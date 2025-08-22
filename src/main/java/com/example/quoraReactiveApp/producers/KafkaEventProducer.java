package com.example.quoraReactiveApp.producers;

import com.example.quoraReactiveApp.configurations.KafkaConfig;
import com.example.quoraReactiveApp.events.ViewCountEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaEventProducer {
    private final KafkaTemplate<String , Object> kafkaTemplate ;

    public void publishViewCount(ViewCountEvent viewCountEvent) {
        kafkaTemplate.send(KafkaConfig.TOPIC_NAME , viewCountEvent.getTargetId() , viewCountEvent)
                .whenComplete((result , err) -> {
                    if(err != null) {
                        System.out.println("Error in producing view count event : " + err.getMessage());
                    }
                }) ;
    }
}
