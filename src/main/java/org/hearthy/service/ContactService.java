package org.hearthy.service;

import lombok.RequiredArgsConstructor;
import org.hearthy.model.ContactRequest;
import org.hearthy.repository.ContactRequestRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContactService {
    private final ContactRequestRepository contactRequestRepository;

    public ContactRequest submitContactRequest(ContactRequest contactRequest) {
        contactRequest.setStatus("new");
        return contactRequestRepository.save(contactRequest);
    }
}
