package com.crm.service.impl;

import com.crm.entity.Contact;
import com.crm.entity.Email;
import com.crm.entity.Lead;
import com.crm.exception.ContactExist;
import com.crm.exception.LeadExist;
import com.crm.payload.EmailDto;
import com.crm.repository.ContactRepository;
import com.crm.repository.EmailRepository;
import com.crm.repository.LeadRepository;
import com.crm.service.EmailService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EmailServiceImpl implements EmailService {

    private EmailRepository emailRepo;

    private LeadRepository leadRepo;


    private JavaMailSender javaMailSender;

    public EmailServiceImpl(EmailRepository emailRepo, LeadRepository leadRepo, JavaMailSender javaMailSender, ModelMapper modelMapper, ContactRepository contactRepo) {
        this.emailRepo = emailRepo;
        this.leadRepo = leadRepo;
        this.javaMailSender = javaMailSender;
        this.modelMapper = modelMapper;
        this.contactRepo = contactRepo;
    }

    private ModelMapper modelMapper;

    private ContactRepository contactRepo;



    @Override
    public EmailDto sendEmail(EmailDto emailDto) {
        Lead lead = leadRepo.findByEmail(emailDto.getTo()).orElseThrow(() ->
                new LeadExist("Email Id Not registered - " + emailDto.getTo()));

//        Contact contact = contactRepo.findByEmail(emailDto.getTo()).orElseThrow(()->
//                new ContactExist("Email Id Not registered - "+emailDto.getTo()));


        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailDto.getTo());
        message.setSubject(emailDto.getSubject());
        message.setText(emailDto.getMessage());

        javaMailSender.send(message);

        Email email = mapToEntity(emailDto);
        String eid = UUID.randomUUID().toString();

        email.setEid(eid);

        Email sentEmail = emailRepo.save(email);
        return mapToDto(sentEmail);

    }

    private Email mapToEntity(EmailDto emailDto){
        return modelMapper.map(emailDto,Email.class);
    }

    private EmailDto mapToDto(Email email){
        return modelMapper.map(email,EmailDto.class);
    }
}
