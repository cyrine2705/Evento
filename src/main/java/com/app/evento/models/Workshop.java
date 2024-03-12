package com.app.evento.models;

import com.app.evento.dto.AgentDto;
import com.app.evento.dto.GuestDto;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Document(collation = "workshop")
public class Workshop {
    @Id
    private String id;
    private String name;
    private List<String> trainerName;
    private Map<String,Double> prices;
    private String description;
    private List<GuestDto> trainees;
    private AgentDto agent;

}
