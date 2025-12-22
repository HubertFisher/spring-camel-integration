package org.example.dto;

import lombok.Data;
import java.util.Date;

@Data
public class ExportUser {
    private Long id;
    private String firstName;
    private String lastName;
    private Double moneySpent;
    private Date exportDate;

    public ExportUser(Long id, String firstName, String lastName, Double moneySpent) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.moneySpent = moneySpent;
        setExportDate(exportDate);
    }
    private void setExportDate() {
        this.exportDate = new Date();
    }
}
