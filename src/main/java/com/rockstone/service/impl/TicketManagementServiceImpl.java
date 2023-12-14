package com.rockstone.service.impl;

import com.rockstone.entity.Ticket;
import com.rockstone.repository.TicketRepository;
import com.rockstone.response.GenericResponse;
import com.rockstone.response.TicketManamgementResponse;
import com.rockstone.service.ParseAudioService;
import com.rockstone.service.TicketManagementService;
import com.rockstone.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
@Slf4j
@Service
public class TicketManagementServiceImpl implements TicketManagementService {


    @Autowired
    ParseAudioService parseAudioService;
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

    @Override
    public void saveTicketToDatabase( String transcription ) {
        log.trace("Enter method saveTicketToDatabase Implementation");

        try {

            Ticket newTicket = new Ticket();
            newTicket.setMessage(transcription);
            newTicket.setCategory(null);
            newTicket.setAgentAssigned("Ryan");
            newTicket.setStatus(Constants.TICKET_STATUS_OPEN);
            newTicket.setResolution(null);

            log.info("Exit Method saveTicketToDatabase Implementation: ticket created successfully");
            ticketRepository.save(newTicket);

        }
        catch(Exception e){
            log.error("Exit method saveTicketToDatabase: an error occurred when trying save ticket to database", e);
            throw new RuntimeException("Failed to save ticket");
        }
    }
}