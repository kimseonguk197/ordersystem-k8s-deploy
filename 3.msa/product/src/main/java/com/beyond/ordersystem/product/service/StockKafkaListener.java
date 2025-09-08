package com.beyond.ordersystem.product.service;

import com.beyond.ordersystem.product.dto.ProductUpdateStockDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StockKafkaListener {
    private final ProductService productService;
    @KafkaListener(topics = "stock-update-topic", containerFactory = "kafkaListener")
    public void stockConsumer(String message){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ProductUpdateStockDto dto = objectMapper.readValue(message, ProductUpdateStockDto.class);
            productService.updateStock(dto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
