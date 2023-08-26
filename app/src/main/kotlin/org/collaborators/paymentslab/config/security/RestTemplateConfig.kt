package org.collaborators.paymentslab.config.security

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.client.RestTemplate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Configuration
class RestTemplateConfig {
    @Bean
    fun restTemplate(): RestTemplate {
        val restTemplate = RestTemplate()
        restTemplate.messageConverters.add(0, MappingJackson2HttpMessageConverter(configureObjectMapper().build()))
        return restTemplate
    }

    // JSR310
    @Bean
    fun jtm(): JavaTimeModule {
        val jtm = JavaTimeModule()
        jtm.addDeserializer(LocalDateTime::class.java, LocalDateTimeDeserializer(DateTimeFormatter.ISO_DATE_TIME))
        return jtm
    }

    @Bean
    fun configureObjectMapper(): Jackson2ObjectMapperBuilder {
        val builder = object : Jackson2ObjectMapperBuilder() {
            override fun configure(objectMapper: ObjectMapper) {
                super.configure(objectMapper)
                objectMapper.setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE)
                objectMapper.setVisibility(PropertyAccessor.IS_GETTER, JsonAutoDetect.Visibility.NONE)
                objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
                objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
                objectMapper.registerModule(jtm())
            }
        }
        builder.serializationInclusion(JsonInclude.Include.NON_NULL)
        builder.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        builder.modulesToInstall(jtm())
        return builder
    }
}