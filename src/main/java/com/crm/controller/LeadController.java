package com.crm.controller;

import com.crm.payload.EmailDto;
import com.crm.payload.LeadDto;
import com.crm.service.LeadService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leads")
public class LeadController {

    public LeadService leadService;



    public LeadController(LeadService leadService) {
        this.leadService = leadService;
    }

    @PostMapping
    public ResponseEntity<LeadDto> createLead(@RequestBody LeadDto leadDto){
        LeadDto savedDto = leadService.createLead(leadDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedDto);

    }

    @DeleteMapping("/{lid}")
    public ResponseEntity<String> leadById(@PathVariable("lid") String lid){
        String s = leadService.deleteByLid(lid);
        return ResponseEntity.status(HttpStatus.OK).body(s);
    }

    @GetMapping
    public ResponseEntity<List<LeadDto>> getAllLeads(){
        List<LeadDto> listlead= leadService.getAllLeads();
        return ResponseEntity.status(HttpStatus.OK).body(listlead);
    }


}
