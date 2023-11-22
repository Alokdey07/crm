package com.crm.service;

import com.crm.payload.EmailDto;

public interface EmailService {

    public EmailDto sendEmail(EmailDto emailDto);
}
