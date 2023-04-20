package com.tms.loader.services.payment;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.hibernate.exception.ConstraintViolationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

import com.tms.loader.entities.payment.Payment;
import com.tms.loader.exceptions.AllExceptionHandler;
import com.tms.loader.exceptions.ConstraintViolationExceptionHandler;
import com.tms.loader.exceptions.DataIntegrityExceptionHandler;
import com.tms.loader.exceptions.ResourceNotFoundException;
import com.tms.loader.payloads.payment.PaymentDto;
import com.tms.loader.repositories.payment.PaymentRepo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
public class PaymentService {
	@PersistenceContext
    private EntityManager em;
	@Autowired
	private PaymentRepo paymentRepo;
	@Autowired
	private ModelMapper mapper;
	public PaymentDto addPaymentMode(PaymentDto paymentDto) {
		Payment paymentEntity =  mapper.map(paymentDto, Payment.class);
		Integer id = paymentDto.getPaymentId();
		if (paymentRepo.existsById(id)) {
			throw new DataIntegrityExceptionHandler();
		}
		try {
			paymentRepo.save(paymentEntity);
			System.out.println("Saved");
		}catch (ConstraintViolationException e) {
			throw new ConstraintViolationExceptionHandler(paymentDto.getPaymentMode());
		}catch(DataIntegrityViolationException e) {
			throw new DataIntegrityExceptionHandler();
		}catch(Exception e) {
			System.out.println("Here");
			throw new AllExceptionHandler();
		}
		PaymentDto respDto = mapper.map(paymentEntity, PaymentDto.class);
		return respDto;
	}
	public List<PaymentDto> getAllPaymentModes(){
		List<Payment> possiblePaymentModes = paymentRepo.findAll();
		List<PaymentDto> paymentModeDtoList = possiblePaymentModes.stream()
                .map(status -> mapper.map(status, PaymentDto.class))
                .collect(Collectors.toList());
		return paymentModeDtoList;
	}
	    
	    public void deletePaymentMode(Integer paymentId) {
	        paymentRepo.deleteById(paymentId);
	    }
	    @Transactional
	    public PaymentDto updatePaymentMode(PaymentDto paymentDto, Integer id) {
	    	paymentRepo.findById(id)
			.orElseThrow(()-> new ResourceNotFoundException("Payment Mode ", "Id", id));
			try {
				System.out.println("payment mode is "+paymentDto.getPaymentMode());
				paymentRepo.updatePaymentModeById(paymentDto.getPaymentMode(), id);
			}catch(JpaSystemException e){
				throw new ConstraintViolationExceptionHandler(paymentDto.getPaymentMode());
			}catch(Exception e) {
				throw new AllExceptionHandler();
			}
			Payment payment = paymentRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Payment Mode", "Id", id));;
			em.refresh(payment);
			System.out.println(payment.getPaymentMode()+" :payment mode ");
			return mapper.map(payment, PaymentDto.class);
	    }
	    
	    public PaymentDto getPaymentMode(Integer paymentId) {
	        Optional<Payment> optionalPayment = paymentRepo.findById(paymentId);
	        if (optionalPayment.isPresent()) {
	            return mapper.map(optionalPayment.get(), PaymentDto.class);
	        } else {
	            throw new RuntimeException("Payment mode not found");
	        }
	    }
}
