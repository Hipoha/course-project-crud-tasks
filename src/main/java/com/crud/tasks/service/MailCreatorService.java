package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.config.CompanyConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;

@Service
public class MailCreatorService {

    @Autowired
    private AdminConfig adminConfig;

    @Autowired
    private CompanyConfig companyConfig;

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    public String buildTrelloCardEmail(String message) {

        List<String> functionality = new ArrayList<>();
        functionality.add("Tasks management");
        functionality.add("Connection with Trello Account");
        functionality.add("Sending tasks to Trello");

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", "http://localhost:8888/tasks_frontend");
        context.setVariable("button", "Visit website");
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("show_button", false);
        context.setVariable("is_friend", true);
        context.setVariable("admin_config", adminConfig);
        context.setVariable("application_functionality", functionality);
        context.setVariable("company_name", companyConfig.getCompanyName());
        context.setVariable("company_address_line1",
                companyConfig.getCompanyAddressNumber() + " " + companyConfig.getCompanyAddressStreet() + ", " +
                        companyConfig.getCompanyAddressCity() + ",");
        context.setVariable("company_address_line2",
                companyConfig.getCompanyAddressState() + " " + companyConfig.getCompanyAddressZipCode() + ", " +
                        companyConfig.getCompanyAddressCountry());
        context.setVariable("company_email", companyConfig.getCompanyEmail());
        context.setVariable("company_phone", companyConfig.getCompanyPhone());
        return templateEngine.process("mail/created-trello-card-mail", context);
    }

}

