package com.tms.loader.controllers.payment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tms.loader.payloads.payment.PaymentDto;
import com.tms.loader.services.payment.PaymentService;

@RestController
@RequestMapping("/api/paymentmode")
public class PaymentController {
	@Autowired
	private PaymentService paymentService;
	@PostMapping("/")
	public ResponseEntity<PaymentDto> addStatus(@RequestBody PaymentDto dto) {
		return new ResponseEntity<PaymentDto>(paymentService.addPaymentMode(dto), HttpStatus.CREATED);
	}
	@GetMapping("/{id}")
	public ResponseEntity<PaymentDto> getStatus(@PathVariable Integer id) {
		return new ResponseEntity<PaymentDto>(paymentService.getPaymentMode(id),HttpStatus.OK);
	}
	@GetMapping("/")
	public ResponseEntity<List<PaymentDto>> getAllStatus() {
		return new ResponseEntity<List<PaymentDto>>(paymentService.getAllPaymentModes(), HttpStatus.OK);
	}
	@PutMapping("/{id}")
	public ResponseEntity<PaymentDto> updateStatus(@RequestBody PaymentDto dto, @PathVariable Integer id) {
		return new ResponseEntity<PaymentDto>(paymentService.updatePaymentMode(dto, id), HttpStatus.OK);
	}	
}
