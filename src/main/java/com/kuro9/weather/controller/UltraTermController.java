package com.kuro9.weather.controller;

import com.kuro9.weather.dataclass.UltraTermDto;
import com.kuro9.weather.service.UltraTermInterface;
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
@RequestMapping(value = "/ultra-short-term", produces = MediaType.APPLICATION_JSON_VALUE)
public class UltraTermController {

    private final UltraTermInterface service;

    public UltraTermController(UltraTermInterface service) {
        this.service = service;
    }

    @GetMapping(value = "/forecast")
    public ResponseEntity<UltraTermDto> getForecast(@RequestParam int nx, @RequestParam int ny) {
        try {
            return ResponseEntity.ok(service.readUltraTermLog(nx, ny));
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