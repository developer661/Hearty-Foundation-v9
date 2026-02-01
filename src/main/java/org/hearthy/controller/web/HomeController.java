package org.hearthy.controller.web;

import lombok.RequiredArgsConstructor;
import org.hearthy.service.OpportunityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final OpportunityService opportunityService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("urgentNeeds", opportunityService.getUrgentOpportunities());
        return "index";
    }

    @GetMapping("/opportunities")
    public String opportunities(Model model) {
        model.addAttribute("opportunities", opportunityService.getAllOpportunities());
        return "opportunities";
    }

    @GetMapping("/volunteer-registration")
    public String volunteerRegistration() {
        return "volunteer-registration";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }

    @GetMapping("/success")
    public String success() {
        return "success";
    }
}
