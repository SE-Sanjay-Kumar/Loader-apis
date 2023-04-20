package com.tms.loader.services.order;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tms.loader.entities.Client;
import com.tms.loader.entities.driver.Driver;
import com.tms.loader.entities.order.Order;
import com.tms.loader.entities.order.OrderLocation;
import com.tms.loader.entities.order.OrderSchedule;
import com.tms.loader.entities.order.OrderStatus;
import com.tms.loader.entities.payment.Payment;
import com.tms.loader.exceptions.AllExceptionHandler;
import com.tms.loader.exceptions.ResourceNotFoundException;
import com.tms.loader.payloads.order.OrderDto;
import com.tms.loader.payloads.order.UpdateOrderDto;
import com.tms.loader.repositories.ClientRepo;
import com.tms.loader.repositories.driver.DriverRepo;
import com.tms.loader.repositories.order.OrderRepo;
import com.tms.loader.repositories.order.OrderStatusRepo;
import com.tms.loader.repositories.payment.PaymentRepo;



@Service
public class OrderService {
	@Autowired
	private OrderRepo orderRepo;
	@Autowired
	private ClientRepo clientRepo;
	@Autowired
	private DriverRepo driverRepo;
	@Autowired
	private PaymentRepo paymentRepo;
	@Autowired
	private OrderStatusRepo orderStatusRepo;
	@Autowired
	private ModelMapper mapper;
	
	// add order
	public OrderDto addOrder(OrderDto orderDto) {
	    Order order = mapper.map(orderDto, Order.class);
	    // Set the order status based on the provided status ID
	    if (orderDto.getStatusId()!=null) {
	    	OrderStatus status = orderStatusRepo.findById(orderDto.getStatusId())
                    .orElseThrow(() -> new ResourceNotFoundException("Order Status", "id", orderDto.getStatusId()));
order.setStatus(status);
	    }
	    
	    
	    // Set the client for the order
	    Client client = clientRepo.findById(orderDto.getClientId())
	                               .orElseThrow(() -> new ResourceNotFoundException("Client", "id", orderDto.getClientId()));
	    order.setClient(client);
	    
	    // Set the driver for the order (if one is provided)
	    if (orderDto.getDriverId() != null) {
	        Driver driver = driverRepo.findById(orderDto.getDriverId())
	                                   .orElseThrow(() -> new ResourceNotFoundException("Driver", "id", orderDto.getDriverId()));
	        order.setDriver(driver);
	    }
	    
	    // Set the payment for the order (if one is provided)
	    if (orderDto.getPaymentId() != null) {
	        Payment payment = paymentRepo.findById(orderDto.getPaymentId())
	                                      .orElseThrow(() -> new ResourceNotFoundException("Payment", "id", orderDto.getPaymentId()));
	        order.setPayment(payment);
	    }
	    
	    // Set the order location and schedule
	    OrderLocation location = new OrderLocation(orderDto.getPickUp(), orderDto.getDropOff());
	    OrderSchedule schedule = new OrderSchedule(orderDto.getSchedule());
	    order.setOrderLocation(location);
	    order.setOrderSchedule(schedule);
	    
	    // Save the order to the database
	    Order savedOrder = orderRepo.save(order);
	    System.out.println(savedOrder.getOrderLocation().getPickUp());
	    // Map the saved order back to an OrderDto and return it
	    OrderDto backSavedOrder = mapper.map(savedOrder, OrderDto.class);
	    backSavedOrder.setPickUp(savedOrder.getOrderLocation().getPickUp());
	    backSavedOrder.setDropOff(savedOrder.getOrderLocation().getDropOff());
	    return backSavedOrder;
	}

	// update order
	public OrderDto updateOrder(UpdateOrderDto orderDto, Long id) {
		Optional<Order> optionalOrder = orderRepo.findById(id);
	    if (optionalOrder.isPresent()) {
	        Order order = optionalOrder.get();
	        mapper.map(orderDto, order);
	        try {
//	        	OrderStatus status = orderStatusRepo.findById(orderDto.getStatusId())
//	        	        .orElseThrow(() -> new ResourceNotFoundException("order status", "id", orderDto.getStatusId()));

	        	
	        	OrderStatus status = orderStatusRepo.findById(orderDto.getStatusId())
	        		    .orElseThrow(() -> new ResourceNotFoundException("order status", "id", orderDto.getStatusId()));
	        	Driver driver = driverRepo.findById(orderDto.getDriverId())
	        			.orElseThrow(()-> new ResourceNotFoundException("driver", "id", orderDto.getDriverId()));
	        		orderRepo.updateOrderById(orderDto.getPrice(), status, driver, order.getOrderId());
Order updatedOrder = orderRepo.findById(id).orElseThrow(()->  new ResourceNotFoundException("Order","id", id));
	            OrderDto backSavedOrder = mapper.map(updatedOrder, OrderDto.class);
	    	    backSavedOrder.setPickUp(updatedOrder.getOrderLocation().getPickUp());
	    	    backSavedOrder.setDropOff(updatedOrder.getOrderLocation().getDropOff());
	    	    return backSavedOrder;
	        } catch (Exception e) {
	        	System.out.println(e.getLocalizedMessage()+e.getStackTrace());
	            throw new AllExceptionHandler();
	        }
	    } else {
	        throw new ResourceNotFoundException("Order", "id", id);
	    }
	}
	// delete order
	public boolean deleteOrder(Long id) {
	    Optional<Order> order = orderRepo.findById(id);
	    if (order.isPresent()) {
	        orderRepo.delete(order.get());
	        return true;
	    }
	    return false;
	}

	// get order
	public OrderDto getOrderByOrderId(Long id) {
		// Find the order by its ID
	    Order order = orderRepo.findById(id)
	                            .orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));
	    
	    // Map the order to an OrderDto and return it
	    OrderDto backSavedOrder = mapper.map(order, OrderDto.class);
	    backSavedOrder.setPickUp(order.getOrderLocation().getPickUp());
	    backSavedOrder.setDropOff(order.getOrderLocation().getDropOff());
	    return backSavedOrder;
//	    return mapper.map(order, OrderDto.class);
	    
	    
	}
	public List<OrderDto> getOrderByClientId(Long id) {
		// Find the client by its ID
	    Client client = clientRepo.findById(id)
	                               .orElseThrow(() -> new ResourceNotFoundException("Client", "id", id));
	    
	    // Get all orders associated with the client
	    List<Order> orders = orderRepo.findByClient(client);
	    
	    // Map the orders to a list of OrderDtos and return it
	    return orders.stream()
	                 .map(order -> {
	                	 OrderDto backSavedOrder = mapper.map(order, OrderDto.class);
	             	    backSavedOrder.setPickUp(order.getOrderLocation().getPickUp());
	             	    backSavedOrder.setDropOff(order.getOrderLocation().getDropOff());
	             	    return backSavedOrder;
	                 })
	                 .collect(Collectors.toList());
	}
	
	public List<OrderDto> getOrdersWithStatusId(Integer id) {
		OrderStatus status = orderStatusRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order Status", "id", id));

		// Get all orders with the given status
		List<Order> orders = orderRepo.findByStatus(status);

		// Map the orders to a list of OrderDtos and return it	
		return orders.stream()
				.map(order -> {
               	 OrderDto backSavedOrder = mapper.map(order, OrderDto.class);
            	    backSavedOrder.setPickUp(order.getOrderLocation().getPickUp());
            	    backSavedOrder.setDropOff(order.getOrderLocation().getDropOff());
            	    return backSavedOrder;
                })
			.collect(Collectors.toList());
	}
}
