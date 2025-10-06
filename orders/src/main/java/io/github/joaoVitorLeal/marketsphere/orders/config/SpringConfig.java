package io.github.joaoVitorLeal.marketsphere.orders.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();

        // Adicionando plugins e configurações de serialização
        mapper.registerModule(new Jdk8Module()); // suporte para tipos do JDK 8
        mapper.registerModule(new JavaTimeModule()); //  suporte para serializar/desserializar os tipos do Java Time API (LocalDate, Instant, etc.).
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // Garante a serialização de datas como String (ISO 8601) em vez de timestamps numéricos (Epoch time).
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);  // Configura para não incluir campos com valor nulo no JSON gerado.
        mapper.configure(DeserializationFeature.FAIL_ON_UNEXPECTED_VIEW_PROPERTIES, false); // Impede que a aplicação lançe erros se o JSON venha com campos não mapeados pelos dtos/representations

        return mapper;
    }
}
