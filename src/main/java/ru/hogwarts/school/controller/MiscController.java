package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.service.MiscService;

@RestController
@RequestMapping("misc")
public class MiscController {


    private final MiscService miscService;

    public MiscController(MiscService miscService) {
        this.miscService = miscService;
    }

    @GetMapping("/4-5")
    public ResponseEntity<Integer> lesson4_5() {
        return ResponseEntity.ok(miscService.lesson45Step4());
    }

}
