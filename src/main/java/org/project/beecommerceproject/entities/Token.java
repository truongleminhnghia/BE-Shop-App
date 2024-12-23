package org.project.beecommerceproject.entities;

import jakarta.persistence.*;
import lombok.*;
import org.project.beecommerceproject.enums.EnumTokenType;

import java.util.Date;

@Entity
@Table(name = "tokens")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Token {

    @Id
    private String id;
    private Date expires;
    @Enumerated(EnumType.STRING)
    private EnumTokenType type;
}
