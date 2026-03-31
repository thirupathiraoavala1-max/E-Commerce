package com.productservice.inventory_service.service;

import com.productservice.inventory_service.dto.InventoryResponse;
import com.productservice.inventory_service.model.Inventory;
import com.productservice.inventory_service.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    @Transactional
    public List<InventoryResponse> isInStock(List<String> skuCode){
        inventoryRepository.findBySkuCodeIn( skuCode);
        return inventoryRepository.findBySkuCodeIn( skuCode).stream()
                .map(inventory ->
                    InventoryResponse.builder()
                            .skuCode(inventory.getSkuCode())
                            .isInStock(inventory.getQuantity() > 0)
                            .build()

                ).toList();
    }
}
