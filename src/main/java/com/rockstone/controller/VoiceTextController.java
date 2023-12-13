package com.rockstone.controller;


import com.rockstone.response.TicketManamgementResponse;
import com.rockstone.service.TicketManagementService;
import lombok.extern.slf4j.Slf4j;
import org.graalvm.compiler.word.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/voice/text")
@Slf4j
public class VoiceTextController {
    @Autowired
    TicketManagementService ticketManagementService;
    @RequestMapping(value = "/profile", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity<TicketManamgementResponse> getTickets() {
        log.trace("Enter Method getTickets");
        ResponseEntity<TicketManamgementResponse> response = null;

        try {

            TicketManamgementResponse ticketManamgementResponse = ticketManagementService.getTickets();
            response = ResponseEntity.status(ticketManamgementResponse.getStatusCode()).body(ticketManamgementResponse);

        } catch (Exception e) {
            log.error("Error occured");


        }


        return response;
    }
}
