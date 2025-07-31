package com.gregorian.api.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/check")
public class CheckController {
    
    @GetMapping()
    public ResponseEntity<String> checkAPI() {
        return new ResponseEntity<>("<h1>Up!</h1>", HttpStatus.OK);
    }
    
}
