package com.kuro9.weather.controller;

import com.kuro9.weather.dataclass.MidTermDto;
import com.kuro9.weather.service.interfaces.MidTermInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.SocketTimeoutException;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(value = "/mid-term", produces = MediaType.APPLICATION_JSON_VALUE)
public class MidTermController {

    private final MidTermInterface service;

    public MidTermController(MidTermInterface service) {
        this.service = service;
    }

    @GetMapping(value = "/forecast")
    public ResponseEntity<MidTermDto> getForecast(@RequestParam String regId) {
        try {
            return ResponseEntity.ok(service.readMidTermLog(regId));
        }
        catch (SocketTimeoutException ignored) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
        catch (IllegalArgumentException ignored) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        catch (NoSuchElementException ignored) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
