package com.example.game.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

    @RestController
    @RequestMapping("/api/test")
    public class TestController {

        @GetMapping("/hello")
        public ResponseEntity<String> sayHello() {
            return ResponseEntity.ok("Xin chào! Bạn đã có vé VIP (Token xịn).");
        }
}
