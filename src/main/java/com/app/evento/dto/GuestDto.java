package com.app.evento.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
public class GuestDto {
    private String id;
    private String firstName;
    private String lastName;
    private String type;
    private boolean paymentState;
    private boolean present;
}
