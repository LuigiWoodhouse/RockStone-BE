package com.rockstone.controller;


import com.rockstone.response.GenericResponse;
import com.rockstone.response.TicketManamgementResponse;
import com.rockstone.service.TicketManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/voice/text")
@Slf4j
public class VoiceTextController {
    @Autowired
    TicketManagementService ticketManagementService;
    @RequestMapping(value = "/profile", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity<GenericResponse> getTickets() {
        log.trace("Enter Method getTickets");
        ResponseEntity<GenericResponse> response = null;

        try {
             response = ticketManagementService.getTickets();
        } catch (Exception e) {
            log.error("Error occured");
            response = new ResponseEntity<>(new GenericResponse(500, "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.trace("Return method getTickets. Results: {}", response);
        return response;
    }
}
