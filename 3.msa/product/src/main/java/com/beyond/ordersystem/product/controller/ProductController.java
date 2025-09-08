package com.beyond.ordersystem.product.controller;

import com.beyond.ordersystem.common.dto.CommonDto;
import com.beyond.ordersystem.product.dto.*;
import com.beyond.ordersystem.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@ModelAttribute ProductCreateDto productCreateDto, @RequestHeader("X-User-Email")String email){
        Long id = productService.save(productCreateDto, email);
        return  new ResponseEntity<>(
                CommonDto.builder()
                        .result(id)
                        .status_code(HttpStatus.CREATED.value())
                        .status_message("상품등록완료")
                        .build(),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/list")
    public ResponseEntity<?> findAll(Pageable pageable, ProductSearchDto productSearchDto){
        Page<ProductResDto> productResDtoList = productService.findAll(pageable, productSearchDto);
        return  new ResponseEntity<>(
                CommonDto.builder()
                        .result(productResDtoList)
                        .status_code(HttpStatus.OK.value())
                        .status_message("상품목록조회성공")
                        .build(),
                HttpStatus.OK
        );

    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) throws InterruptedException {
        Thread.sleep(3000L);
        ProductResDto productResDto = productService.findById(id);
        return new ResponseEntity<>(
                CommonDto.builder()
                        .result(productResDto)
                        .status_code(HttpStatus.OK.value())
                        .status_message("상품상세조회성공")
                        .build(),
                HttpStatus.OK
        );
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<?> update(@ModelAttribute ProductUpdateDto productUpdateDto, @PathVariable Long productId){
        Long id = productService.update(productUpdateDto, productId);
        return  new ResponseEntity<>(
                CommonDto.builder()
                        .result(id)
                        .status_code(HttpStatus.OK.value())
                        .status_message("상품변경완료")
                        .build(),
                HttpStatus.OK
        );
    }

    @PutMapping("/updatestock")
    public ResponseEntity<?> updateStock(@RequestBody ProductUpdateStockDto updateStockDto){
        Long id = productService.updateStock(updateStockDto);
        return  new ResponseEntity<>(
                CommonDto.builder()
                        .result(id)
                        .status_code(HttpStatus.OK.value())
                        .status_message("상품재고수량변경완료")
                        .build(),
                HttpStatus.OK
        );
    }


}
