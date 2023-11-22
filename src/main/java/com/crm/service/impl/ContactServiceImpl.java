package com.crm.service.impl;

import com.crm.entity.Contact;
import com.crm.entity.Lead;
import com.crm.exception.LeadExist;
import com.crm.payload.ContactDto;
import com.crm.repository.ContactRepository;
import com.crm.repository.LeadRepository;
import com.crm.service.ContactService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ContactServiceImpl implements ContactService {
    private LeadRepository leadRepo;

    private ContactRepository contactRepo;

    private ModelMapper modelMapper;

    public ContactServiceImpl(LeadRepository leadRepo, ContactRepository contactRepo, ModelMapper modelMapper) {
        this.leadRepo = leadRepo;
        this.contactRepo = contactRepo;
        this.modelMapper = modelMapper;
    }



    @Override
    public ContactDto createContact(String leadId) {
        Lead lead = leadRepo.findById(leadId).orElseThrow(() ->
                new LeadExist("Lead With this id does not exist" + leadId));

        Contact contact = convertLeadToContact(lead);
        String cid = UUID.randomUUID().toString();
        contact.setCid(cid);

        Contact savedContact = contactRepo.save(contact);

        if(savedContact.getCid()!=null){
            leadRepo.deleteById(lead.getLid());
        }

        ContactDto dto = mapToContactDto(savedContact);
        dto.setCid(savedContact.getCid());

        return dto;
    }

    ContactDto mapToContactDto(Contact contact){
        return modelMapper.map(contact, ContactDto.class);
    }

    Contact convertLeadToContact(Lead lead){
        return modelMapper.map(lead,Contact.class);
    }
}
