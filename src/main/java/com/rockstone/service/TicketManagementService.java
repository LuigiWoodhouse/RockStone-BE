package com.rockstone.service;

import com.rockstone.response.GenericResponse;
import org.springframework.http.ResponseEntity;
public interface TicketManagementService {

    ResponseEntity<GenericResponse> getTickets();

    void saveTicketToDatabase( String transcription);
}