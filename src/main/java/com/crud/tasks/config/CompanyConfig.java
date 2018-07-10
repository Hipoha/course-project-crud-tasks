package com.crud.tasks.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class CompanyConfig {
    @Value("${info.company.name}")
    private String companyName;

    @Value("${info.company.address.number}")
    private String companyAddressNumber;

    @Value("${info.company.address.street}")
    private String companyAddressStreet;

    @Value("${info.company.address.city}")
    private String companyAddressCity;

    @Value("${info.company.address.zipcode}")
    private String companyAddressZipCode;

    @Value("${info.company.address.state}")
    private String companyAddressState;

    @Value("${info.company.address.country}")
    private String companyAddressCountry;

    @Value("${info.company.email}")
    private String companyEmail;

    @Value("${info.company.phone}")
    private String companyPhone;
}