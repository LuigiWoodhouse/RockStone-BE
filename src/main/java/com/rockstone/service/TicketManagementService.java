package com.rockstone.service;

import com.rockstone.response.TicketManamgementResponse;
import lombok.extern.slf4j.Slf4j;

public interface TicketManagementService {

    TicketManamgementResponse getTickets();
}
