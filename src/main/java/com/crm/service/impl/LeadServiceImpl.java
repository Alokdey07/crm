package com.crm.service.impl;

import com.crm.entity.Lead;
import com.crm.exception.LeadExist;
import com.crm.payload.LeadDto;
import com.crm.repository.LeadRepository;
import com.crm.service.LeadService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LeadServiceImpl implements LeadService {

    public LeadRepository leadRepo;

    public ModelMapper modelMapper;

    public LeadServiceImpl(LeadRepository leadRepo, ModelMapper modelMapper) {
        this.leadRepo = leadRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public LeadDto createLead(LeadDto leadDto) {
        boolean mobileExists = leadRepo.existsByMobile(leadDto.getMobile());
        boolean emailExists = leadRepo.existsByEmail(leadDto.getEmail());
        if(mobileExists){
            throw new LeadExist("Mobile Already Exists" + leadDto.getMobile());
        }
        if(emailExists){
            throw new LeadExist("Email Already Exists: " + leadDto.getEmail());
        }

        Lead lead = mapToEntity(leadDto);
        String lid = UUID.randomUUID().toString();
        lead.setLid(lid);
        Lead saveLead = leadRepo.save(lead);
        return mapToDto(saveLead);


    }

    @Override
    public String deleteByLid(String lid) {
        LeadDto leadDto = mapToDto(leadRepo.findById(lid).orElseThrow(()->new LeadExist("Led doesnot exist with  id: "+ lid)));
        leadRepo.deleteById(lid);
        return new String("Lead deleated with id"+lid);
    }

    @Override
    public List<LeadDto> getAllLeads() {
        List<Lead> allLeads = leadRepo.findAll();
        List<LeadDto> allLeadDto = allLeads.stream().map(leads -> mapToDto(leads)).collect(Collectors.toList());
        return allLeadDto;
    }

    @Override
    public LeadDto findByLid(String lid) {
        Lead lead = leadRepo.findById(lid).orElseThrow(() -> new LeadExist("lead with id not exist: " + lid));
        return mapToDto(lead);

    }

    @Override
    public List<Lead> getLeadsExcelReports() {
        return leadRepo.findAll();
    }

    private Lead mapToEntity(LeadDto dto){
        return modelMapper.map(dto,Lead.class);
    }



    private LeadDto mapToDto(Lead lead){
        return modelMapper.map(lead,LeadDto.class);
    }
}
