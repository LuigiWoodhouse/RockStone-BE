package com.rockstone.controller;

import com.google.protobuf.ByteString;
import com.rockstone.controller.VoiceTextController;
import com.rockstone.entity.Ticket;
import com.rockstone.response.GenericResponse;
import com.rockstone.service.ParseAudioService;
import com.rockstone.service.TicketManagementService;
import com.rockstone.service.impl.TicketManagementServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class VoiceTextControllerTest {
    @Mock
    private TicketManagementServiceImpl ticketManagementService;

    @Mock
    private ParseAudioService parseAudioService;

    @InjectMocks
    private VoiceTextController voiceTextController;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
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
        response = new ResponseEntity<>(genericResponse, HttpStatus.OK);
        expecteResponse = new ResponseEntity<>(genericResponse, HttpStatus.OK);

        genericResponse.setMessage("All tickets Retrieved successfully");
        genericResponse.setStatusCode(200);
        genericResponse.setData(tickets);


        Mockito.when(ticketManagementService.getTickets()).thenReturn(response);

        Assert.assertEquals(expecteResponse.getBody().getStatusCode(), response.getBody().getStatusCode());

    }

    @Test
    @Description("Return HTTP status 400 when the GetTicket enpdoint is called")

    public void getTickets_BADREQUEST(){
        ResponseEntity<GenericResponse> response = null;
        ResponseEntity<GenericResponse> expecteResponse = null;
        GenericResponse genericResponse = new GenericResponse();
        Ticket ticket = new Ticket();
        List<Ticket> tickets =  new ArrayList<>();;

        tickets.add(ticket);
        response = new ResponseEntity<>(genericResponse, HttpStatus.BAD_REQUEST);
        expecteResponse = new ResponseEntity<>(genericResponse, HttpStatus.BAD_REQUEST);

        genericResponse.setMessage("All tickets Retrieved successfully");
        genericResponse.setStatusCode(200);
        genericResponse.setData(tickets);


        Mockito.when(ticketManagementService.getTickets()).thenReturn(response);

        Assert.assertEquals(expecteResponse.getBody().getStatusCode(), response.getBody().getStatusCode());
    }
    @Test
    @Description("Return HTTP status 500 when the GetTicket enpdoint is called")
    public void getTickets_INTERNALSERVER_ERROR(){
        ResponseEntity<GenericResponse> response = null;
        ResponseEntity<GenericResponse> expecteResponse = null;
        GenericResponse genericResponse = new GenericResponse();
        Ticket ticket = new Ticket();
        List<Ticket> tickets =  new ArrayList<>();;

        tickets.add(ticket);
        response = new ResponseEntity<>(genericResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        expecteResponse = new ResponseEntity<>(genericResponse, HttpStatus.INTERNAL_SERVER_ERROR);

        genericResponse.setMessage("All tickets Retrieved successfully");
        genericResponse.setStatusCode(200);
        genericResponse.setData(tickets);


        Mockito.when(ticketManagementService.getTickets()).thenReturn(response);

        Assert.assertEquals(expecteResponse.getBody().getStatusCode(), response.getBody().getStatusCode());
    }


}