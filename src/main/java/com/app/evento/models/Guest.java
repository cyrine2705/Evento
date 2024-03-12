package com.app.evento.models;

import com.app.evento.enums.Civility;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Document(collection = "guests")
public class Guest {
    @Id
    private String id;
    private Civility gender;
    private String  phone;
    private String firstName;
    private String lastName;
    private String email;
    private String profession;
    private String service;
    private String workPlace;
    private Address address;
    private String type;


}
