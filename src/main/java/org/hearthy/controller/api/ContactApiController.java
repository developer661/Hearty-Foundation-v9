package org.hearthy.controller.api;

import lombok.RequiredArgsConstructor;
import org.hearthy.model.ContactRequest;
import org.hearthy.service.ContactService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contact")
@RequiredArgsConstructor
public class ContactApiController {
    private final ContactService contactService;

    @PostMapping
    public ResponseEntity<ContactRequest> submitContactRequest(@RequestBody ContactRequest contactRequest) {
        return ResponseEntity.ok(contactService.submitContactRequest(contactRequest));
    }
}
