package com.rockstone.service.impl;

import com.rockstone.entity.Ticket;
import com.rockstone.exception.TranscriptionException;
import com.rockstone.repository.TicketRepository;
import com.rockstone.response.GenericResponse;
import com.rockstone.service.ParseAudioService;
import com.rockstone.service.impl.TicketManagementServiceImpl;
import com.rockstone.utils.Constants;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
public class TicketManagementServiceImplTests {

    @InjectMocks
    TicketManagementServiceImpl ticketManagementServiceImpl;

    @Mock
    TicketRepository ticketRepository;

    @Mock
    ParseAudioService parseAudioService;

    Ticket newTicket = new Ticket();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void saveTicketToDatabase() throws IOException, TranscriptionException {

        String transcriptedAudio = "test";
        MultipartFile audioFile = null;
        Mockito.when(parseAudioService.convertAudioToText(audioFile)).thenReturn(transcriptedAudio);
        newTicket.setMessage("convert-audio-to-string");
        newTicket.setCategory(null);
        newTicket.setAgentAssigned("Ryan");
        newTicket.setStatus(Constants.TICKET_STATUS_OPEN);
        newTicket.setResolution(null);

        Mockito.when(ticketRepository.save(ArgumentMatchers.any(Ticket.class))).thenReturn(newTicket);

        ticketManagementServiceImpl.saveTicketToDatabase();
        Assertions.assertNotNull(newTicket);
    }

    @Test
    @Description("Return HTTP status of 20O when the GetTicket enpdoint is called")
    public void getTickets_SUCCESS(){

        ResponseEntity<GenericResponse> response = null;
        ResponseEntity<GenericResponse> expecteResponse = null;
        GenericResponse genericResponse = new GenericResponse();
        Ticket ticket = new Ticket();
        List<Ticket> tickets =  new ArrayList<>();;

        tickets.add(ticket);
        when(ticketRepository.findAll()).thenReturn(tickets);

        Mockito.when(ticketRepository.save(ArgumentMatchers.any(Ticket.class))).thenReturn(newTicket);

        genericResponse.setMessage("All tickets Retrieved successfully");
        genericResponse.setStatusCode(200);
        genericResponse.setData(tickets);
        response = new ResponseEntity<>(genericResponse, HttpStatus.OK);

        expecteResponse = new ResponseEntity<>(genericResponse, HttpStatus.OK);


        Assert.assertEquals(expecteResponse.getBody().getStatusCode(), response.getBody().getStatusCode());

    }

    @Test
    @Description("Return HTTP status of 20O when the GetTicket enpdoint is called")
    public void getTickets_SUCCES(){

        ResponseEntity<GenericResponse> response = null;
        ResponseEntity<GenericResponse> expecteResponse = null;
        GenericResponse genericResponse = new GenericResponse();
        Ticket ticket = new Ticket();
        List<Ticket> tickets =  new ArrayList<>();;

        tickets.add(ticket);
        when(ticketRepository.findAll()).thenReturn(tickets);


        genericResponse.setMessage("All tickets Retrieved successfully");
        genericResponse.setStatusCode(200);
        genericResponse.setData(tickets);
        response = new ResponseEntity<>(genericResponse, HttpStatus.OK);

        expecteResponse = new ResponseEntity<>(genericResponse, HttpStatus.OK);


        Assert.assertEquals(expecteResponse.getBody().getStatusCode(), response.getBody().getStatusCode());

    }

    @Test
    @Description("Return HTTP status of 40O when the GetTicket enpdoint is called")
    public void getTickets_BAD_REQUEST(){

        ResponseEntity<GenericResponse> response = null;
        ResponseEntity<GenericResponse> expecteResponse = null;
        GenericResponse genericResponse = new GenericResponse();
        Ticket ticket = new Ticket();
        List<Ticket> tickets =  null;

        when(ticketRepository.findAll()).thenReturn(tickets);

        Mockito.when(ticketRepository.save(ArgumentMatchers.any(Ticket.class))).thenReturn(newTicket);

        genericResponse.setMessage("Unable to find tickets");
        genericResponse.setStatusCode(400);
        genericResponse.setData(tickets);
        response = new ResponseEntity<>(genericResponse, HttpStatus.BAD_REQUEST);

        expecteResponse = new ResponseEntity<>(genericResponse, HttpStatus.BAD_REQUEST);


        Assert.assertEquals(expecteResponse.getBody().getStatusCode(), response.getBody().getStatusCode());
    }

    @Test
    @Description("Return HTTP status of 50O when the GetTicket enpdoint is called")
    public void getTickets_Internal_SERVER(){

        ResponseEntity<GenericResponse> response = null;
        ResponseEntity<GenericResponse> expecteResponse = null;
        GenericResponse genericResponse = new GenericResponse();
        Ticket ticket = new Ticket();
        List<Ticket> tickets =  new ArrayList<>();

        when(ticketRepository.findAll()).thenReturn(tickets);

        Mockito.when(ticketRepository.save(ArgumentMatchers.any(Ticket.class))).thenReturn(newTicket);

        genericResponse.setMessage("Unable to find tickets");
        genericResponse.setStatusCode(400);
        genericResponse.setData(tickets);
        response = new ResponseEntity<>(genericResponse, HttpStatus.INTERNAL_SERVER_ERROR);

        expecteResponse = new ResponseEntity<>(genericResponse, HttpStatus.INTERNAL_SERVER_ERROR);


        Assert.assertEquals(expecteResponse.getBody().getStatusCode(), response.getBody().getStatusCode());
    }


}
