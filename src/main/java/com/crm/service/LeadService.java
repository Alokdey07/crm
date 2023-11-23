package com.crm.service;


import com.crm.entity.Lead;
import com.crm.payload.LeadDto;

import java.util.List;

public interface LeadService {
    LeadDto createLead(LeadDto leadDto);
    String deleteByLid(String lid);

    List<LeadDto> getAllLeads();

    LeadDto findByLid(String lid);

    List<Lead> getLeadsExcelReports();
}
