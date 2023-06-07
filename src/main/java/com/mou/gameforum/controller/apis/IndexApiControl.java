package com.mou.gameforum.controller.apis;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api")
@CrossOrigin("*")
public class IndexApiControl {
    @Operation(hidden = true)
    @RequestMapping()
    public String index(){
        return "{" +
                "\"status\"" + ": \"ok\""
                +"}";
    }
}
