package com.example.alert_system.service;

import com.example.alert_system.dto.CreateTeamDto;
import com.example.alert_system.dto.StandardResponse;
import org.springframework.http.ResponseEntity;

public interface AlertService {
    ResponseEntity<StandardResponse> createTeam(CreateTeamDto createTeamDto);

    ResponseEntity<StandardResponse> sendAlert(String teamId);
}
