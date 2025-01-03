package org.project.beecommerceproject.dtos.requests;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserUpdateRequest {

    private String firstName;
    private String lastName;
    private String phoneNUmber;
    private String address;
    private String avatar;
}
