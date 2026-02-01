package org.hearthy.controller.api;

import lombok.RequiredArgsConstructor;
import org.hearthy.model.Volunteer;
import org.hearthy.model.VolunteerRegistration;
import org.hearthy.service.VolunteerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/volunteers")
@RequiredArgsConstructor
public class VolunteerApiController {
    private final VolunteerService volunteerService;

    @GetMapping
    public ResponseEntity<List<Volunteer>> getAllVolunteers() {
        return ResponseEntity.ok(volunteerService.getAllVolunteers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Volunteer> getVolunteerById(@PathVariable UUID id) {
        return volunteerService.getVolunteerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Volunteer> registerVolunteer(@RequestBody Volunteer volunteer) {
        return ResponseEntity.ok(volunteerService.registerVolunteer(volunteer));
    }

    @PostMapping("/registrations")
    public ResponseEntity<VolunteerRegistration> createRegistration(@RequestBody VolunteerRegistration registration) {
        return ResponseEntity.ok(volunteerService.createVolunteerRegistration(registration));
    }

    @GetMapping("/registrations")
    public ResponseEntity<List<VolunteerRegistration>> getAllRegistrations() {
        return ResponseEntity.ok(volunteerService.getAllRegistrations());
    }
}
