package com.tms.loader.services.order;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tms.loader.configs.MYConstants;
import com.tms.loader.entities.Client;
import com.tms.loader.entities.driver.Driver;
import com.tms.loader.entities.driver.DriverStatus;
import com.tms.loader.entities.order.Order;
import com.tms.loader.entities.order.OrderLocation;
import com.tms.loader.entities.order.OrderSchedule;
import com.tms.loader.entities.order.OrderStatus;
import com.tms.loader.entities.payment.Payment;
import com.tms.loader.entities.vehicle.VehicleCost;
import com.tms.loader.entities.vehicle.VehicleStatus;
import com.tms.loader.exceptions.AllExceptionHandler;
import com.tms.loader.exceptions.ResourceNotFoundException;
import com.tms.loader.payloads.StatusDto;
import com.tms.loader.payloads.order.OrderDto;
import com.tms.loader.payloads.order.UpdateOrderDto;
import com.tms.loader.repositories.ClientRepo;
import com.tms.loader.repositories.driver.DriverRepo;
import com.tms.loader.repositories.order.OrderRepo;
import com.tms.loader.repositories.order.OrderStatusRepo;
import com.tms.loader.repositories.payment.PaymentRepo;
import com.tms.loader.repositories.vehicle.FreightRepo;
import com.tms.loader.services.driver.DriverStatusService;
import com.tms.loader.services.vehicle.VehicleStatusService;



@Service
public class OrderService {
	@Autowired
	private OrderRepo orderRepo;
	@Autowired
	private ClientRepo clientRepo;
	@Autowired
	private DriverRepo driverRepo;
	@Autowired
	private FreightRepo freightRepo;
	@Autowired
	private PaymentRepo paymentRepo;
	@Autowired
	private VehicleStatusService vehicleStatusService;
	@Autowired
	private OrderStatusRepo orderStatusRepo;
	@Autowired
	private DriverStatusService driverStatusService;
	@Autowired
	private ModelMapper mapper;
	
	// add order 
	// image adding
	public OrderDto addOrder(OrderDto orderDto) {
	    Order order = mapper.map(orderDto, Order.class);
	    // Set the order status based on the provided status ID
	    if (orderDto.getStatus()!=null) {
	    	OrderStatus status = orderStatusRepo.findById(orderDto.getStatus().getStatusId())
                    .orElseThrow(() -> new ResourceNotFoundException("Order Status", "id", orderDto.getStatus().getStatusId()));
order.setStatus(status);
	    }
	    
	    
	    // Set the client for the order
	    Client client = clientRepo.findById(orderDto.getClient().getId())
	                               .orElseThrow(() -> new ResourceNotFoundException("Client", "id", orderDto.getClient().getId()));
	    order.setClient(client);
	    
	    // Set the driver for the order (if one is provided)
	    if (orderDto.getDriver() != null) {
	        Driver driver = driverRepo.findById(orderDto.getDriver().getId())
	                                   .orElseThrow(() -> new ResourceNotFoundException("Driver", "id", orderDto.getDriver().getId()));
	        order.setDriver(driver);
	    }
	    
	    // Set the payment for the order (if one is provided)
	    if (orderDto.getPayment()!= null) {
	        Payment payment = paymentRepo.findById(orderDto.getPayment().getPaymentId())
	                                      .orElseThrow(() -> new ResourceNotFoundException("Payment", "id", orderDto.getPayment().getPaymentId()));
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
		System.out.println("In update order step 1");
		Optional<Order> optionalOrder = orderRepo.findById(id);
	    if (optionalOrder.isPresent()) {
	    	System.out.println("In update order step 2");
	        Order order = optionalOrder.get();
	        mapper.map(orderDto, order);
	        try {
	        	
//	        	OrderStatus status = orderStatusRepo.findById(orderDto.getStatusId())
//	        	        .orElseThrow(() -> new ResourceNotFoundException("order status", "id", orderDto.getStatusId()));
	        	
	        	
	        	OrderStatus status = orderStatusRepo.findById(orderDto.getStatus().getStatusId())
	        		    .orElseThrow(() -> new ResourceNotFoundException("order status", "id", orderDto.getStatus().getStatusId()));
	        	Driver driver = null;
	        	if (orderDto.getDriver()!=null) {
	        		 driver = driverRepo.findById(orderDto.getDriver().getId())
		        			.orElseThrow(()-> new ResourceNotFoundException("driver", "id", orderDto.getDriver().getId()));
		        	
	        	}
	        	System.out.println("here before update order  "+status.getStatus());
	        		
	        	orderRepo.updateOrderById(orderDto.getPrice(), status,orderDto.getEstimatedArrivalOfGoods(), driver,orderDto.getPaymentStatus(), order.getOrderId());
	        	Order updatedOrder = orderRepo.findById(id).orElseThrow(()->  new ResourceNotFoundException("Order","id", id));
	            OrderDto backSavedOrder = mapper.map(updatedOrder, OrderDto.class);
	            System.out.println("Here the "+order.getStatus().getStatusId());
	            // if order is assigned then mark driver and his/her vehicle as booked
//	            if (order.getStatus().getStatusId() == MYConstants.ORDER_ACTIVE) {
//	            	updationToDriverAndVehicle(driver, MYConstants.DRIVER_PROGRESS, MYConstants.VEHICLE_ASSIGNED);
//	            	System.out.println("Here");
//	            }
	            if (order.getStatus().getStatusId() == MYConstants.ORDER_ACTIVE) {
	            	updationToDriverAndVehicle(driver, MYConstants.DRIVER_BUSY, MYConstants.VEHICLE_ASSIGNED);
	            	System.out.println("Here");
	            }
	            if (order.getStatus().getStatusId() == MYConstants.ORDER_DELIVERED) {
	            	updationToDriverAndVehicle(driver, MYConstants.DRIVER_AVAILABLE, MYConstants.VEHICLE_AVAILABLE);
	            	System.out.println("Here");
	            }
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
	public void updationToDriverAndVehicle(Driver driver, Integer driverStatusCode, Integer vehicleStatusCode) {
		System.out.println("before updation of driver status");
    	
		StatusDto  progressStatusDto = driverStatusService.getStatus(driverStatusCode);
    	DriverStatus driverStatus = mapper.map(progressStatusDto, DriverStatus.class);
    	driverRepo.updateDriverById(driverStatus, driver.getUserName(),driver.getLocation(),driver.getFoodCost(), driver.getId());
    	System.out.println("before updation of vehicle status");
    	
    	Long vid = driver.getVehicle().getVehicleId();
    	// maintenance and fuel cost
    	float maintenanceCost = driver.getVehicle().getCost().getMaintenanceCost();
    	float fuelCost= driver.getVehicle().getCost().getFuelCost();
    	VehicleCost cost = new VehicleCost();
    	cost.setMaintenanceCost(maintenanceCost);
    	cost.setFuelCost(fuelCost);
    	StatusDto vstatusdto =  vehicleStatusService.getStatus(vehicleStatusCode);
    	VehicleStatus vstatus = mapper.map(vstatusdto, VehicleStatus.class);
    	freightRepo.updateFreightById(vstatus,cost, vid);
    	System.out.println("after updation of vehicle status");
    	
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
	public List<OrderDto> getAllOrders(){
		List<Order> orders = orderRepo.findAll();
		return orders.stream()
                .map(order -> {
                	OrderDto backSavedOrder = mapper.map(order, OrderDto.class);
            	    backSavedOrder.setPickUp(order.getOrderLocation().getPickUp());
            	    backSavedOrder.setDropOff(order.getOrderLocation().getDropOff());
            	    return backSavedOrder;
            	   
                })
                .collect(Collectors.toList());
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