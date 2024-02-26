package org.example.domain.repository;

import org.example.domain.model.Appointment;
import org.springframework.data.repository.CrudRepository;

public interface AppointmentRepository extends CrudRepository<Appointment, Integer> {
}
