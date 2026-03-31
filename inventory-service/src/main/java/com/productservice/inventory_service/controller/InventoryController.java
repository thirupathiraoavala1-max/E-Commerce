package com.productservice.inventory_service.controller;

import com.productservice.inventory_service.dto.InventoryResponse;
import com.productservice.inventory_service.model.Inventory;
import com.productservice.inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inventory")
public class InventoryController {
    private final InventoryService inventoryService;


    //URL will be constructed as "http://localhost:8082/api/invetnory?skuCode=iphone-13&skuCode=iphone13-red"
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam List<String> skuCode){
        return inventoryService.isInStock(skuCode);
    }

}
