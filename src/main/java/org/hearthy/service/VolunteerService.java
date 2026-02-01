package org.hearthy.service;

import lombok.RequiredArgsConstructor;
import org.hearthy.model.Volunteer;
import org.hearthy.model.VolunteerRegistration;
import org.hearthy.repository.VolunteerRegistrationRepository;
import org.hearthy.repository.VolunteerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VolunteerService {
    private final VolunteerRepository volunteerRepository;
    private final VolunteerRegistrationRepository volunteerRegistrationRepository;

    public List<Volunteer> getAllVolunteers() {
        return volunteerRepository.findAll();
    }

    public Optional<Volunteer> getVolunteerById(UUID id) {
        return volunteerRepository.findById(id);
    }

    public Volunteer registerVolunteer(Volunteer volunteer) {
        return volunteerRepository.save(volunteer);
    }

    public VolunteerRegistration createVolunteerRegistration(VolunteerRegistration registration) {
        registration.setStatus("pending");
        return volunteerRegistrationRepository.save(registration);
    }

    public List<VolunteerRegistration> getAllRegistrations() {
        return volunteerRegistrationRepository.findAll();
    }
}
