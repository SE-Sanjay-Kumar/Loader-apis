package com.tms.loader.controllers.review;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tms.loader.payloads.review.ClientReviewDto;
import com.tms.loader.payloads.review.DriverReviewDto;
import com.tms.loader.services.review.ReviewService;

@RestController
@RequestMapping("/api/review/")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("order/{orderId}/client-review")
    public ResponseEntity<ClientReviewDto> addClientReview(
            @PathVariable("orderId") Long orderId,
            @RequestBody ClientReviewDto clientReviewDto) {
        ClientReviewDto addedClientReview = reviewService.addClientReview(orderId, clientReviewDto);
        return ResponseEntity.ok(addedClientReview);
    }

    @PostMapping("order/{orderId}/driver-review")
    public ResponseEntity<DriverReviewDto> addDriverReview(
            @PathVariable("orderId") Long orderId,
            @RequestBody DriverReviewDto driverReviewDto) {
        DriverReviewDto addedDriverReview = reviewService.addDriverReview(orderId, driverReviewDto);
        return ResponseEntity.ok(addedDriverReview);
    }

    @GetMapping("order/{orderId}/client-review")
    public ResponseEntity<ClientReviewDto> getClientReviewsByOrderId(@PathVariable("orderId") Long orderId) {
        ClientReviewDto clientReviewDto = reviewService.getClientReviewsByOrderId(orderId);
        return ResponseEntity.ok(clientReviewDto);
    }

    @GetMapping("order/{orderId}/driver-review")
    public ResponseEntity<DriverReviewDto> getDriverReviewsByOrderId(@PathVariable("orderId") Long orderId) {
        DriverReviewDto driverReviewDto = reviewService.getDriverReviewsByOrderId(orderId);
        return ResponseEntity.ok(driverReviewDto);
    }

    @GetMapping("driver-reviews")
    public ResponseEntity<List<DriverReviewDto>> getAllDriverReviews() {
        List<DriverReviewDto> driverReviewDtos = reviewService.getAllReviewsOfDrivers();
        return ResponseEntity.ok(driverReviewDtos);
    }

    @GetMapping("client-reviews")
    public ResponseEntity<List<ClientReviewDto>> getAllClientReviews() {
    	System.out.println("here in client reviews");
        List<ClientReviewDto> clientReviewDtos = reviewService.getAllReviewsOfClients();
        return ResponseEntity.ok(clientReviewDtos);
    }

}

