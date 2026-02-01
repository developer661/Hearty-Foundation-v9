package org.hearthy.controller.api;

import lombok.RequiredArgsConstructor;
import org.hearthy.model.Opportunity;
import org.hearthy.service.OpportunityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/opportunities")
@RequiredArgsConstructor
public class OpportunityApiController {
    private final OpportunityService opportunityService;

    @GetMapping
    public ResponseEntity<List<Opportunity>> getAllOpportunities() {
        return ResponseEntity.ok(opportunityService.getAllOpportunities());
    }

    @GetMapping("/urgent")
    public ResponseEntity<List<Opportunity>> getUrgentOpportunities() {
        return ResponseEntity.ok(opportunityService.getUrgentOpportunities());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Opportunity> getOpportunityById(@PathVariable UUID id) {
        return opportunityService.getOpportunityById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Opportunity> createOpportunity(@RequestBody Opportunity opportunity) {
        return ResponseEntity.ok(opportunityService.createOpportunity(opportunity));
    }
}
