package org.scaler.ecommerceuserservice.configurations;

import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedReorderedGenerator;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */
@Configuration
public class EcommerceUserServiceConfiguration {

    @Bean
    public TimeBasedReorderedGenerator generator(){
        return Generators.timeBasedReorderedGenerator();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public KafkaTemplate<String, String> kafkaTemplate() {
//        return new KafkaTemplate<>(producerFactory());
//    }
//
//    public ProducerFactory<String, String> producerFactory() {
//        Map<String, Object> config = new HashMap<>();
//        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        return new DefaultKafkaProducerFactory<>(config);
//    }
}
