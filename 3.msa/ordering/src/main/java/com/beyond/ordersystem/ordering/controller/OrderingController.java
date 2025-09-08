package com.beyond.ordersystem.ordering.controller;

import com.beyond.ordersystem.common.dto.CommonDto;
import com.beyond.ordersystem.ordering.domain.Ordering;
import com.beyond.ordersystem.ordering.dto.OrderCreateDto;
import com.beyond.ordersystem.ordering.dto.OrderListResDto;
import com.beyond.ordersystem.ordering.service.OrderingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ordering")
public class OrderingController {
    private final OrderingService orderingService;
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody List<OrderCreateDto> orderCreateDtos, @RequestHeader("X-User-Email") String email) {
        Long id = orderingService.createFeignKafka(orderCreateDtos, email);
        return new ResponseEntity<>(
                CommonDto.builder()
                        .result(id)
                        .status_code(HttpStatus.CREATED.value())
                        .status_message("주문완료")
                        .build(),
                HttpStatus.CREATED
        );
    }
    @GetMapping("/list")
    public ResponseEntity<?> findAll(){
        List<OrderListResDto> orderListResDtos = orderingService.findAll();
        return  new ResponseEntity<>(
                CommonDto.builder()
                        .result(orderListResDtos)
                        .status_code(HttpStatus.OK.value())
                        .status_message("주문목록조회성공")
                        .build(),
                HttpStatus.OK
        );
    }

    @GetMapping("/myorders")
    public ResponseEntity<?> myOrders(@RequestHeader("X-User-Email") String email){
        List<OrderListResDto> orderListResDtos = orderingService.myorders(email);
        return  new ResponseEntity<>(
                CommonDto.builder()
                        .result(orderListResDtos)
                        .status_code(HttpStatus.OK.value())
                        .status_message("내주문목록조회성공")
                        .build(),
                HttpStatus.OK
        );
    }

}
