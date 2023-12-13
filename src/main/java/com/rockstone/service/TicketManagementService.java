package com.rockstone.service;

import com.rockstone.response.GenericResponse;
import com.rockstone.response.TicketManamgementResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

public interface TicketManagementService {

    ResponseEntity<GenericResponse> getTickets();
}
