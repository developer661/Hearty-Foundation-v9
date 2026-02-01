package org.hearthy.service;

import lombok.RequiredArgsConstructor;
import org.hearthy.model.Opportunity;
import org.hearthy.repository.OpportunityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OpportunityService {
    private final OpportunityRepository opportunityRepository;

    public List<Opportunity> getAllOpportunities() {
        return opportunityRepository.findAll();
    }

    public List<Opportunity> getUrgentOpportunities() {
        return opportunityRepository.findUrgent();
    }

    public Optional<Opportunity> getOpportunityById(UUID id) {
        return opportunityRepository.findById(id);
    }

    public Opportunity createOpportunity(Opportunity opportunity) {
        opportunity.setStatus("active");
        return opportunityRepository.save(opportunity);
    }
}
