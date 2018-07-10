package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.config.CompanyConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

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
        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", "http://localhost:8888/tasks_frontend");
        context.setVariable("button", "Visit website");
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("companyName", companyConfig.getCompanyName());
        context.setVariable("companyAddressLine1",
                companyConfig.getCompanyAddressNumber() + " " + companyConfig.getCompanyAddressStreet() + ", " +
                        companyConfig.getCompanyAddressCity() + ",");
        context.setVariable("companyAddressLine2",
                companyConfig.getCompanyAddressState() + " " + companyConfig.getCompanyAddressZipCode() + ", " +
                        companyConfig.getCompanyAddressCountry());
        context.setVariable("companyEmail", companyConfig.getCompanyEmail());
        context.setVariable("companyPhone", companyConfig.getCompanyPhone());
        return templateEngine.process("mail/created-trello-card-mail", context);
    }

}
