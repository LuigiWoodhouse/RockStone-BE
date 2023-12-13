package com.rockstone.service.impl;

import com.rockstone.entity.Ticket;
import com.rockstone.repository.TicketRepository;
import com.rockstone.response.GenericResponse;
import com.rockstone.response.TicketManamgementResponse;
import com.rockstone.service.TicketManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Slf4j

public class TicketManagementServiceImpl implements TicketManagementService {


    @Autowired
    TicketRepository ticketRepository;
    @Override
    public ResponseEntity<GenericResponse>  getTickets() {
        log.trace("Enter method getTickets");
        ResponseEntity<GenericResponse> response;
        List<TicketManamgementResponse>ticketManamgementResponseList;
        List<Ticket> tickets;

        GenericResponse genericResponse = new GenericResponse();
        try {
            tickets  = ticketRepository.findAll();
            if(tickets == null){

                genericResponse.setStatusCode(400);
                genericResponse.setMessage("Unable to find tickets");

                response = new ResponseEntity<>(genericResponse, HttpStatus.BAD_REQUEST);
            }

            genericResponse.setMessage("All tickets Retrieved successfully");
            genericResponse.setStatusCode(200);
            genericResponse.setData(tickets);
            response = new ResponseEntity<>(genericResponse, HttpStatus.OK);

        }catch(Exception e){

            genericResponse.setMessage("Error occured while retrieving tickets");
            genericResponse.setStatusCode(500);
            response = new ResponseEntity<>(genericResponse, HttpStatus.INTERNAL_SERVER_ERROR);

        }
        return response;
    }
}
