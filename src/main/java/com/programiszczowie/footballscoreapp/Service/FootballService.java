package com.programiszczowie.footballscoreapp.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Service
@Slf4j
public class FootballService {

    @Value("${api.football.url}")
    private String footballApiUrl;

    @Value("${api.football.key}")
    private String XRapidApiKey;

    @Value("${api.football.host}")
    private String XRapidApiHost;

    private final RestTemplate restTemplate;

    public FootballService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Object getAllFootballData() {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-rapidapi-key", XRapidApiKey);
            headers.set("X-rapidapi-host", XRapidApiHost);

            ResponseEntity<String> responseEntity = restTemplate.exchange(footballApiUrl, HttpMethod.GET,
                    new HttpEntity<>(headers), String.class);

            log.info("Output from football API response: {}", responseEntity.getBody());
            return responseEntity.getBody();

        } catch (Exception e) {
            log.error("Something went wrong while getting data from Football API", e);
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Exception while calling endpoint of Football API", e);
        }
    }
}
