package org.example.model;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.hateoas.RepresentationModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User extends RepresentationModel<User> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false, length = 50)
    private String name;

    @Column(name = "last_name", nullable = false, length = 50)
    private String surname;

    @Column(name = "money_spent")
    private Double moneySpent = 0.0;

    @Column(name = "is_exported")
    @JsonIgnore
    private boolean exported = false;
}