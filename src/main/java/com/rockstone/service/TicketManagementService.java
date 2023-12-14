package com.rockstone.service;

import com.google.protobuf.ByteString;
import com.rockstone.response.GenericResponse;
import com.rockstone.response.TicketManamgementResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;


public interface TicketManagementService {

    ResponseEntity<GenericResponse> getTickets();

    void saveTicketToDatabase();
}
