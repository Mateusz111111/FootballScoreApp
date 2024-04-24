package com.programiszczowie.footballscoreapp.Controller;

import com.programiszczowie.footballscoreapp.Service.FootballService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/football")
@RequiredArgsConstructor
public class FootballController {

    private final FootballService footballService;

    @GetMapping("/get-all-football-data")
    public ResponseEntity<?> getAllFootballData() {
        return ResponseEntity.ok(footballService.getAllFootballData());
    }

}
