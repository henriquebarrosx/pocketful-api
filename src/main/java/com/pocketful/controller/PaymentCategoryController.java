package com.pocketful.controller;

import com.pocketful.entity.PaymentCategory;
import com.pocketful.service.PaymentCategoryService;
import com.pocketful.web.dto.payment_category.NewPaymentCategoryDTO;
import com.pocketful.web.dto.payment_category.PaymentCategoryDTO;
import com.pocketful.web.mapper.PaymentCategoryDTOMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RequestMapping("v1/payments/categories")
@RestController
public class PaymentCategoryController {
    private final PaymentCategoryService paymentCategoriesService;
    private final PaymentCategoryDTOMapper paymentCategoryDTOMapper;

    @GetMapping
    public ResponseEntity<List<PaymentCategoryDTO>> getAll() {
        log.info("Getting all payments categories");

        List<PaymentCategoryDTO> paymentCategories = paymentCategoriesService.findAll().stream()
                .map(paymentCategoryDTOMapper)
                .toList();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(paymentCategories);
    }

    @GetMapping("{id}")
    public ResponseEntity<PaymentCategoryDTO> getById(@PathVariable Long id) {
        log.info("Getting payment category by id - {}", id);
        PaymentCategory category = paymentCategoriesService.findById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(paymentCategoryDTOMapper.apply(category));
    }

    @PostMapping
    public ResponseEntity<PaymentCategoryDTO> create(@RequestBody NewPaymentCategoryDTO request) {
        log.info("Creating payment category: name - {}", request.getName());

        PaymentCategory paymentCategory = paymentCategoriesService
                .create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(paymentCategoryDTOMapper.apply(paymentCategory));
    }

    @PutMapping("{id}")
    public ResponseEntity<PaymentCategoryDTO> update(
            @PathVariable Long id,
            @RequestBody NewPaymentCategoryDTO request
    ) {
        log.info("Updating payment category: id - {} | name - {}", id, request.getName());

        PaymentCategory paymentCategory = paymentCategoriesService
                .update(id, request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(paymentCategoryDTOMapper.apply(paymentCategory));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Deleting payment category: id - {}", id);
        paymentCategoriesService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
