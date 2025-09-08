package com.beyond.ordersystem.ordering.dto;

import com.beyond.ordersystem.ordering.domain.OrderDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailResDto {
    private Long detailId;
    private String productName;
    private int productCount;

    public static OrderDetailResDto fromEntity(OrderDetail orderDetail) {
        return OrderDetailResDto.builder()
                .detailId(orderDetail.getId())
                .productName(orderDetail.getProductName())
                .productCount(orderDetail.getQuantity())
                .build();
    }
}