package com.tms.loader.controllers.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tms.loader.payloads.order.OrderDto;
import com.tms.loader.payloads.order.UpdateOrderDto;
import com.tms.loader.services.order.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // Add an order
    @PostMapping("/")
    public ResponseEntity<OrderDto> addOrder(@RequestBody OrderDto orderDto) {
        return new ResponseEntity<OrderDto>(orderService.addOrder(orderDto), HttpStatus.CREATED); 
    }
    @GetMapping("/")
    public ResponseEntity<List<OrderDto>> getOrders() {
        return new ResponseEntity<List<OrderDto>>(orderService.getAllOrders(), HttpStatus.OK); 
    }
    // Update an order by ID
    @PutMapping("/{id}")
    public ResponseEntity<OrderDto> updateOrder(@RequestBody UpdateOrderDto orderDto, @PathVariable Long id) {
        return new ResponseEntity<OrderDto>(orderService.updateOrder(orderDto, id), HttpStatus.OK);
    }

    // Delete an order by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id) {
    	boolean isDeleted = orderService.deleteOrder(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Get an order by ID
    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderByOrderId(@PathVariable Long id) {
        return new ResponseEntity<OrderDto>(orderService.getOrderByOrderId(id), HttpStatus.OK);
    }

    // Get all orders for a client by client ID
    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<OrderDto>> getOrderByClientId(@PathVariable Long clientId) {
        return new ResponseEntity<List<OrderDto>>(orderService.getOrderByClientId(clientId), HttpStatus.OK);
    }

    // Get all orders with a given status ID
    @GetMapping("/status/{statusId}")
    public ResponseEntity<List<OrderDto>> getOrdersWithStatusId(@PathVariable Integer statusId) {
        return new ResponseEntity<List<OrderDto>>(orderService.getOrdersWithStatusId(statusId), HttpStatus.OK);
    }
}