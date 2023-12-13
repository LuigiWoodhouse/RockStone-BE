package com.rockstone.service.impl;

import com.rockstone.entity.Ticket;
import com.rockstone.repository.TicketRepository;
import com.rockstone.response.GenericResponse;
import com.rockstone.response.TicketManamgementResponse;
import com.rockstone.service.TicketManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Slf4j

public class TicketManagementServiceImpl implements TicketManagementService {


@Autowired
TicketRepository ticketRepository;
    @Override
    public TicketManamgementResponse getTickets() {
    log.trace("Enter method getTickets");
        ResponseEntity<GenericResponse> response;
        List<TicketManamgementResponse>ticketManamgementResponseList;
        List<Ticket> tickets;

        GenericResponse genericResponse = new GenericResponse();
        try {
            tickets  = ticketRepository.findAll();
            if(tickets == null){

            }

            genericResponse.setMessage("All tickets");



        }catch(Exception e){


        }
        return null;
    }
}
