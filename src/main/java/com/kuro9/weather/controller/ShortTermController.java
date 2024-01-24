package com.kuro9.weather.controller;

import com.kuro9.weather.dataclass.ShortTermDto;
import com.kuro9.weather.service.interfaces.ShortTermInterface;
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
@RequestMapping(value = "/short-term", produces = MediaType.APPLICATION_JSON_VALUE)
public class ShortTermController {

    private final ShortTermInterface service;

    public ShortTermController(ShortTermInterface service) {
        this.service = service;
    }

    @GetMapping(value = "/forecast")
    public ResponseEntity<ShortTermDto> getForecast(@RequestParam int nx, @RequestParam int ny, @RequestParam int hourOffset) {
        try {
            return ResponseEntity.ok(service.readShortTermLog(nx, ny, hourOffset));
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