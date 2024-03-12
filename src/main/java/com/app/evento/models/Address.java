package com.app.evento.models;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Address {
    private String postalCode;
    private String town;
}
