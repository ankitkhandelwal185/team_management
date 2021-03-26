package com.example.alert_system.service.impl;

import com.example.alert_system.Utils.JsonUtil;
import com.example.alert_system.config.URLConfig;
import com.example.alert_system.dto.CreateTeamDto;
import com.example.alert_system.dto.SendSmsDto;
import com.example.alert_system.dto.StandardResponse;
import com.example.alert_system.exception.APIRuntimeException;
import com.example.alert_system.model.DeveloperDetailsEntity;
import com.example.alert_system.model.TeamDetailsEntity;
import com.example.alert_system.pojo.DeveloperDetails;
import com.example.alert_system.repository.DeveloperDetailsRepository;
import com.example.alert_system.repository.TeamDetailsRepository;
import com.example.alert_system.service.AlertService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class AlertServiceImpl implements AlertService {

    @Autowired
    TeamDetailsRepository teamDetailsRepository;

    @Autowired
    DeveloperDetailsRepository developerDetailsRepository;

    @Autowired
    private URLConfig urlConfig;

    @Override
    public ResponseEntity<StandardResponse> createTeam(CreateTeamDto createTeamDto) {
        StandardResponse<String> standardResponse = new StandardResponse();
        log.info("createTeam:: payload - {}", createTeamDto);
        if(createTeamDto.getDevelopers().size()==0){
            standardResponse.setSuccess(false);
            standardResponse.setMessage("Minimum one developer required to create team");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(standardResponse);
        }
        try{
            TeamDetailsEntity teamDetailsEntity = TeamDetailsEntity.builder()
                    .name(createTeamDto.getTeamName())
                    .developerDetailsEntityList(getDeveloperEntityList(createTeamDto.getDevelopers()))
                    .build();
            teamDetailsEntity = teamDetailsRepository.save(teamDetailsEntity);
            standardResponse.setData(String.valueOf(teamDetailsEntity.getId()));
        } catch (Exception e){
            log.error("createTeam:: exception - {}", e);
            standardResponse.setSuccess(false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(standardResponse);
        }
        standardResponse.setSuccess(true);
        return ResponseEntity.status(HttpStatus.OK).body(standardResponse);
    }

    @Override
    public ResponseEntity<StandardResponse> sendAlert(String teamId) {
        log.info("sendAlert:: teamId - {}", teamId);
        StandardResponse<String> standardResponse = new StandardResponse();
        Optional<TeamDetailsEntity> teamDetailsEntityOpt = teamDetailsRepository.findById(Long.parseLong(teamId));
        if(!teamDetailsEntityOpt.isPresent()){
            log.info("sendAlert:: team_id {} DNE", teamId);
            throw new APIRuntimeException("Team not found", HttpStatus.NOT_FOUND);
        }
        try {
            TeamDetailsEntity teamDetailsEntity = teamDetailsEntityOpt.get();
            int index = getRandomNumber(0, teamDetailsEntity.getDeveloperDetailsEntityList().size()-1);
            log.info("sendAlert:: index {}, total {}", index, teamDetailsEntity.getDeveloperDetailsEntityList().size());
            String phone_number = teamDetailsEntity.getDeveloperDetailsEntityList().get(index).getPhoneNumber();
            String url = urlConfig.getSmsURL().concat("/v3/fd99c100-f88a-4d70-aaf7-393dbbd5d99f");
            SendSmsDto payload = SendSmsDto.builder()
                    .phoneNumber(phone_number)
                    .build();
            log.info("sendAlert:: url - {} payload - {}", url ,payload);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            HttpEntity<String> requestEntity = new HttpEntity<String>(JsonUtil.toSnakeCaseJsonString(payload), headers);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
            log.info("sendAlert:: api response - {}", response);
        } catch (Exception e){
            log.error("sendAlert:: Exception - {}", e);
            standardResponse.setSuccess(false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(standardResponse);
        }
        standardResponse.setSuccess(true);
        return ResponseEntity.status(HttpStatus.OK).body(standardResponse);
    }

    private int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    private List<DeveloperDetailsEntity> getDeveloperEntityList(List<DeveloperDetails> developerDetails){
        List<DeveloperDetailsEntity> developerDetailsEntities = new ArrayList<>();
        for (DeveloperDetails developerDetail: developerDetails){
            DeveloperDetailsEntity developerDetailsEntity = DeveloperDetailsEntity.builder()
                    .name(developerDetail.getName())
                    .phoneNumber(developerDetail.getPhoneNumber())
                    .build();
            developerDetailsEntity = developerDetailsRepository.save(developerDetailsEntity);
            developerDetailsEntities.add(developerDetailsEntity);
        }
        return developerDetailsEntities;
    }

}
