package com.rockstone.repository;

import com.rockstone.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface TicketRepository extends JpaRepository<Ticket,Long> {
    List<Ticket> findAllById(Long id);
}