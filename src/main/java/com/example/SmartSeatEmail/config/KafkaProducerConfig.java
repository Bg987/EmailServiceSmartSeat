package com.example.SmartSeatEmail.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;

import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    private Map<String, Object> sslConfig() throws IOException {

        Map<String, Object> sslProps = new HashMap<>();

        String caContent = new String(new ClassPathResource("ca.pem").getInputStream().readAllBytes());
        String serviceCert = new String(new ClassPathResource("service.cert").getInputStream().readAllBytes());
        String serviceKey = new String(new ClassPathResource("service.key").getInputStream().readAllBytes());

        sslProps.put("security.protocol", "SSL");

        sslProps.put("ssl.truststore.type", "PEM");
        sslProps.put("ssl.truststore.certificates", caContent);
        sslProps.put("allow.auto.create.topics", true);
        sslProps.put("ssl.keystore.type", "PEM");
        sslProps.put("ssl.keystore.certificate.chain", serviceCert);
        sslProps.put("ssl.keystore.key", serviceKey);

        return sslProps;
    }

    // ---------------- PRODUCER ----------------

    @Bean
    public ProducerFactory<String, Object> producerFactory() throws IOException {

        Map<String, Object> props = new HashMap<>();

        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "smartseat-bhavyagodhaniya2004-2234.k.aivencloud.com:26025");

        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        props.putAll(sslConfig());

        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() throws IOException {
        return new KafkaTemplate<>(producerFactory());
    }

    // ---------------- CONSUMER ----------------

    @Bean
    public ConsumerFactory<String, Object> consumerFactory() throws IOException {

        Map<String, Object> props = new HashMap<>();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "smartseat-bhavyagodhaniya2004-2234.k.aivencloud.com:26025");

        props.put(ConsumerConfig.GROUP_ID_CONFIG, "my-group");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

        props.putAll(sslConfig());

        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory()
            throws IOException {

        ConcurrentKafkaListenerContainerFactory<String, Object> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory());

        return factory;
    }
}