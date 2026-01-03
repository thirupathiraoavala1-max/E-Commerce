package com.productservice.order_service.services;

import com.productservice.order_service.dto.InventoryResponse;
import com.productservice.order_service.dto.OrderLineItemsDto;
import com.productservice.order_service.dto.OrderRequest;
import com.productservice.order_service.model.Order;
import com.productservice.order_service.model.OrderLineItems;
import com.productservice.order_service.repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {


    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;


    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());


        if (orderRequest.getOrderLineItemsDtoList() == null ||
                orderRequest.getOrderLineItemsDtoList().isEmpty()) {
            throw new IllegalArgumentException("Order items cannot be empty");
        }
        //Mapping the items to place an order
        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream().map(this::mapToDto).toList();

        order.setOrderLineItemsList(orderLineItems);

        //List out the skuCodes present in the order.
        List<String> skuCodes = order.getOrderLineItemsList().stream()
                .map(OrderLineItems::getSkuCode)
                .toList();

        //Call inventory service, and place order if the product is in stock
        //To make synchronous communication, we have to use block().
        //For Asynchronous communication, we don't use block().
      InventoryResponse[] inventoryResponseArray =   webClientBuilder.build().get()
                .uri("http://inventory-service/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode",skuCodes).build() )
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                // Mono is used to save value in the future,
                // the A container that will give you one value (or no value) in the future.
                .block();

        if (inventoryResponseArray.length != skuCodes.size()) {
            throw new IllegalArgumentException("Some products not found in inventory");
        }

        Boolean allProducts = Arrays.stream(inventoryResponseArray)
                .allMatch(InventoryResponse::getIsInStock );

        if(allProducts){
            orderRepository.save(order);
        }else {
            throw new IllegalArgumentException("Product is not in stock, please try again later!!!!!");
        }

    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;

    }
}
