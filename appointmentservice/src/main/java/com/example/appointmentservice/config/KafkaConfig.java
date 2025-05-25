package com.example.appointmentservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.Map;

@Configuration
public class KafkaConfig {

    @Bean
    public ProducerFactory<String,Object> producerFactory() {
        return new DefaultKafkaProducerFactory<>(Map.of(
                "bootstrap.servers", "localhost:9092",
                "key.serializer", JsonSerializer.class,
                "value.serializer", JsonSerializer.class
                ));
    }
    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate(){
        return new KafkaTemplate<>(producerFactory());

    }
    @Bean
    public ConsumerFactory<String,Object> consumerFactory(){
        JsonDeserializer<Object> deser = new JsonDeserializer<>()
                .trustedPackages("com.example.appointmentservice.events");
        return new DefaultKafkaConsumerFactory<>(Map.of(
                "bootstrap.servers", "localhost:9092",
                "group.id","appointment"
        ), new StringDeserializer(), deser);

    }
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object>
    kafkaListenerContainerFactory() {
        var factory = new ConcurrentKafkaListenerContainerFactory<String,Object>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    @Bean
    public NewTopic appointmentCreatedTopic() {
        return new NewTopic("appointment.created", 1, (short)1);
    }



}
