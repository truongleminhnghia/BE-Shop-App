package org.project.beecommerceproject.entities;

import jakarta.persistence.*;
import lombok.*;
import org.project.beecommerceproject.enums.EnumStatusUser;

@Entity
@Table(name = "users")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "first_name", columnDefinition = "NVARCHAR(300)")
    private String firstName;

    @Column(name = "last_name", columnDefinition = "NVARCHAR(300)")
    private String lastName;

    @Column(name = "password")
    private String password;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "phone", length = 12)
    private String phone;

    @Column(name = "address", columnDefinition = "NVARCHAR(300)")
    private String address;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "google_account_id")
    private String googleAccountId;

    @Column(name = "facebook_account_id")
    private String facebookAccountId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private EnumStatusUser status;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;
}
