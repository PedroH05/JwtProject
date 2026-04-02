package com.pedro.auth.controller;

import com.pedro.auth.dto.LoginRequestDTO;
import com.pedro.auth.dto.RegisterRequestDTO;
import com.pedro.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<Map<String, String>> register(@RequestBody RegisterRequestDTO dto) {
        String token = userService.register(dto);
        return ResponseEntity.ok(Map.of("token", token));
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequestDTO dto) {
        String token = userService.login(dto);
        return ResponseEntity.ok(Map.of("token", token));
    }

    @GetMapping("/login")
    public String loginPage() { return "login"; }

    @GetMapping("/register")
    public String registerPage() { return "register"; }

}

