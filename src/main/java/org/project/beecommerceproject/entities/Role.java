package org.project.beecommerceproject.entities;

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

    @OneToMany(mappedBy = "role")
    private Set<User> user;
}
