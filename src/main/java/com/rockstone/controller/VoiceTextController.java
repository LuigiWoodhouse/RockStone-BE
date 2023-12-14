package com.rockstone.controller;


import com.google.protobuf.ByteString;
import com.rockstone.response.GenericResponse;
import com.rockstone.response.TicketManamgementResponse;
import com.rockstone.service.TicketManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/voice")
@Slf4j
public class VoiceTextController {
    @Autowired
    TicketManagementService ticketManagementService;
    @RequestMapping(value = "/text", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse> getTickets() {
        log.trace("Enter Method getTickets");
        ResponseEntity<GenericResponse> response = null;

        try {
             response = ticketManagementService.getTickets();
        }
        catch (Exception e) {
            log.error("Error occured");
            response = new ResponseEntity<>(new GenericResponse(500, "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.trace("Return method getTickets. Results: {}", response);
        return response;
    }
    @PostMapping(path ="/convert/bytes" )
    public ResponseEntity<GenericResponse> getAudio(@RequestParam("image") MultipartFile file) {
        log.trace("Enter Method getTickets");
        ByteString response = null;

        ResponseEntity<GenericResponse> responseEntity;
        GenericResponse result = new GenericResponse();

        try {
            response = ticketManagementService.getAudio(file);
            result.setStatusCode(200);
            result.setMessage(HttpStatus.OK.getReasonPhrase());
            result.setData(response);
            responseEntity = new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch (Exception e) {
            result.setStatusCode(500);
            result.setMessage(e.getMessage());
            responseEntity = new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
            log.error("Return Method getAudio: Status:500(Internal Server Error)", e);
        }
        return responseEntity;
    }
}
