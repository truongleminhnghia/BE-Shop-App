package org.project.beecommerceproject.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.project.beecommerceproject.enums.EnumRoleName;

import java.util.Set;

@Entity
@Table(name = "roles")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_name")
    private EnumRoleName roleName;

    @JsonIgnore
    @OneToMany(mappedBy = "role")
    private Set<User> user;
}
