package com.app.evento.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "jwtEvento") //maaneha stockage ysir localemnt o tabaa swagger annotation hedhi
public class TestController {
    //@PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "n9oul 3aslama") // hedhi documentation bzayed tahki feha chneya api taamel
    @GetMapping("/test/{name}")
    public ResponseEntity<Map<String, String>> sayHello(@PathVariable String name){
       return ResponseEntity.ok(Map.of("msg", String.format("3aslama !", name)));
    }

}
