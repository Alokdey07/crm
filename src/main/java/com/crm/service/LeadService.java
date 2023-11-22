package com.crm.service;


import com.crm.payload.LeadDto;

import java.util.List;

public interface LeadService {
    LeadDto createLead(LeadDto leadDto);
    String deleteByLid(String lid);

    List<LeadDto> getAllLeads();
}
