package com.cosmocats.config;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import com.cosmocats.service.mapper.ProductMapper;
import com.cosmocats.service.mapper.ProductServiceMapper;

@TestConfiguration
public class MappersTestConfiguration {

    @Bean
    public ProductMapper productMapper() {
        return Mappers.getMapper(ProductMapper.class);
    }

    @Bean
    public ProductServiceMapper paymentServiceMapper(){
        return Mappers.getMapper(ProductServiceMapper.class);
    }
}
