package com.threeLine.atmcardverifier.controller;

import com.threeLine.atmcardverifier.model.CardInfo;
import com.threeLine.atmcardverifier.payload.CardInfoResponse;
import com.threeLine.atmcardverifier.repository.CardInfoRepository;
import com.threeLine.atmcardverifier.service.CardInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;

@RestController
@RequestMapping("/card-scheme")
public class CardVerificationController {

    @Autowired
    CardInfoService cardInfoService;

    @PostMapping("/verify/{customerNumber}")
    public ResponseEntity<CardInfoResponse> getCardDetail(@PathVariable String customerNumber) {


        return new ResponseEntity<>(cardInfoService.getCardInfo(customerNumber), HttpStatus.OK);
    }

}
