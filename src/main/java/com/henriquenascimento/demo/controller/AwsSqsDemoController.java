package com.henriquenascimento.demo.controller;

import com.henriquenascimento.demo.dto.AwsSqsDemoRequestDTO;
import com.henriquenascimento.demo.service.AwsSqsDemoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.henriquenascimento.demo.controller.AwsSqsDemoController.BASE_URI;


@RestController
@RequiredArgsConstructor
@RequestMapping(BASE_URI)
public class AwsSqsDemoController {

    static final String BASE_URI = "/aws-sqs";
    private final AwsSqsDemoService awsSqsDemoService;

    @PostMapping("/demo-producer")
    public ResponseEntity<Void> demoProducer(@RequestBody @Valid final AwsSqsDemoRequestDTO request) {
        awsSqsDemoService.sendDemoMessage(request);
        return ResponseEntity.ok().build();
    }

}
