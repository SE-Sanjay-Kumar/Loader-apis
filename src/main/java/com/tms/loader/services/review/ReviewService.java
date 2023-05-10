package com.tms.loader.services.review;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.exception.ConstraintViolationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.tms.loader.entities.order.Order;
import com.tms.loader.entities.review.ClientReview;
import com.tms.loader.entities.review.DriverReview;
import com.tms.loader.exceptions.AllExceptionHandler;
import com.tms.loader.exceptions.ConstraintViolationExceptionHandler;
import com.tms.loader.exceptions.DataIntegrityExceptionHandler;
import com.tms.loader.exceptions.ResourceNotFoundException;
import com.tms.loader.payloads.review.ClientReviewDto;
import com.tms.loader.payloads.review.DriverReviewDto;
import com.tms.loader.repositories.order.OrderRepo;
import com.tms.loader.repositories.review.ClientReviewRepo;
import com.tms.loader.repositories.review.DriverReviewRepo;


@Service
public class ReviewService {
	@Autowired
    private ModelMapper mapper;
    @Autowired
    private ClientReviewRepo clientReviewRepo;
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private DriverReviewRepo driverReviewRepo;
    
    public ClientReviewDto addClientReview(Long orderId, ClientReviewDto clientReviewDto) {
        ClientReview clientReview = mapper.map(clientReviewDto, ClientReview.class);
        System.out.println("client id is "+clientReview.getClient().getId());
        Order order = orderRepo.findById(orderId).orElseThrow(() -> 
        	new ResourceNotFoundException("order", "id", orderId)
        );
        clientReview.setOrder(order);
        try {
        	clientReview = clientReviewRepo.save(clientReview);
        	return mapper.map(clientReview, ClientReviewDto.class);
		}catch (ConstraintViolationException e) {
			throw new ConstraintViolationExceptionHandler(clientReviewDto.getOrder().getOrderId().toString());
		}catch(DataIntegrityViolationException e) {
			throw new DataIntegrityExceptionHandler();
		}catch(Exception e) {
			throw new AllExceptionHandler();
		}
        
        
    }
    public DriverReviewDto addDriverReview(Long orderId, DriverReviewDto driverReviewDto) {
        DriverReview driverReview = mapper.map(driverReviewDto, DriverReview.class);
        Order order = orderRepo.findById(orderId).orElseThrow(() -> 
        	new ResourceNotFoundException("order", "id", orderId)
        );
        driverReview.setOrder(order);
        try {
        	driverReview = driverReviewRepo.save(driverReview);
            
        	return mapper.map(driverReview, DriverReviewDto.class);
		}catch (ConstraintViolationException e) {
			throw new ConstraintViolationExceptionHandler(driverReviewDto.getOrder().getOrderId().toString());
		}catch(DataIntegrityViolationException e) {
			throw new DataIntegrityExceptionHandler();
		}catch(Exception e) {
			throw new AllExceptionHandler();
		}
        
    }
    public ClientReviewDto getClientReviewsByOrderId(Long orderId) {
    	System.out.println("here at client reviews");
    	Order order = orderRepo.findById(orderId).orElseThrow(() -> 
    	new ResourceNotFoundException("order", "id", orderId)
    );
        ClientReview clientReview = clientReviewRepo.findByOrder(order);
        if (clientReview==null) {
        	throw new ResourceNotFoundException("order ", "id", orderId);
            
        }
//       return null;
        return mapper.map(clientReview, ClientReviewDto.class);
    }
    public DriverReviewDto getDriverReviewsByOrderId(Long orderId) {
        DriverReview driverReview = (DriverReview) driverReviewRepo.findByOrder(orderId);
        if (driverReview==null) {
        	throw new ResourceNotFoundException("order ", "id", orderId);
        }
        return mapper.map(driverReview, DriverReviewDto.class);
    }
    public List<DriverReviewDto> getAllReviewsOfDrivers() {
        List<DriverReview> driverReviews = driverReviewRepo.findAll();
        return driverReviews.stream().map(driverReview ->
            mapper.map(driverReview, DriverReviewDto.class)).collect(Collectors.toList());
    }
    public List<ClientReviewDto> getAllReviewsOfClients() {
        List<ClientReview> clientReviews = clientReviewRepo.findAll();
        return clientReviews.stream().map(clientReview ->
            mapper.map(clientReview, ClientReviewDto.class)).collect(Collectors.toList());
    }
}
