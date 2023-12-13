package com.rockstone.response;

import com.rockstone.entity.Ticket;

import java.util.List;

public class TicketManamgementResponse extends GenericResponse<List<Ticket>> {
    private static final long serialVersionUID = 1L;

    public TicketManamgementResponse (int code, String msg) {
        super(code, msg);
    }

    public TicketManamgementResponse (int code, String msg, List<Ticket> data) {
        super(code, msg, data);
    }
}

