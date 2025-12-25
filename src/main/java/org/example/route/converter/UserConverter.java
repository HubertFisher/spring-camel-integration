package org.example.route.converter;

import org.example.dto.ExportUser;
import org.example.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    public ExportUser toExportUser(User user) {
        return new ExportUser(user.getId(),user.getFirstName(),user.getLastName(),user.getMoneySpent());
    }

}
