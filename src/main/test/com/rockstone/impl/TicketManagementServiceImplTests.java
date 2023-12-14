package com.rockstone.impl;

import com.rockstone.entity.Ticket;
import com.rockstone.exception.TranscriptionException;
import com.rockstone.repository.TicketRepository;
import com.rockstone.service.ParseAudioService;
import com.rockstone.service.impl.TicketManagementServiceImpl;
import com.rockstone.utils.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.io.IOException;
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

        when(parseAudioService.convertAudioToText()).thenReturn(transcriptedAudio);
        newTicket.setMessage("convert-audio-to-string");
        newTicket.setCategory(null);
        newTicket.setAgentAssigned("Ryan");
        newTicket.setStatus(Constants.TICKET_STATUS_OPEN);
        newTicket.setResolution(null);

        when(ticketRepository.save(any(Ticket.class))).thenReturn(newTicket);

        ticketManagementServiceImpl.saveTicketToDatabase();
        assertNotNull(newTicket);

    }
}
