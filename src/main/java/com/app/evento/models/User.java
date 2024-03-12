package com.app.evento.models;

import com.app.evento.enums.Role;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Document(collection = "users")
public class User  {
  @Id
  private String id;
  private String firstName;
  private String lastName;
  private String username;
  private String email;
  private String password;
  private Role role;
  private String resetToken;
  private Date resetTokenExpiration;
}
