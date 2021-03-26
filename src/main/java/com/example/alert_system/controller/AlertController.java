package com.example.alert_system.controller;


import com.example.alert_system.dto.CreateTeamDto;
import com.example.alert_system.dto.StandardResponse;
import com.example.alert_system.service.AlertService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import javax.validation.Valid;


@RestController
@RequestMapping("/api/")
@Api(value = "Alert Service")
@Log4j2
public class AlertController {
    @Autowired
    AlertService alertService;

    @RequestMapping(value = "/v1/{team_id}/alert", method = RequestMethod.POST)
    public ResponseEntity<StandardResponse> sendEvent(@PathVariable(name = "team_id") String teamId) {
        return alertService.sendAlert(teamId);
    }

    @RequestMapping(value = "/v1/create-team", method = RequestMethod.POST)
    public ResponseEntity<StandardResponse> createTeam(@Valid @RequestBody CreateTeamDto payload) {
        return alertService.createTeam(payload);
    }
}
