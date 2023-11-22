package com.crm.service;


import com.crm.payload.ContactDto;
import com.crm.payload.LeadDto;

import java.util.List;

public interface ContactService {
    ContactDto createContact(String leadId);


}
