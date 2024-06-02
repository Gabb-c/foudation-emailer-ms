package com.foundation.emailer.controller;

import com.foundation.emailer.dto.NonprofitDTO;
import com.foundation.emailer.model.Nonprofit;
import com.foundation.emailer.service.NonprofitService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1/nonprofits")
@RequiredArgsConstructor
public class NonprofitController {

    private final NonprofitService nonprofitService;

    @PostMapping
    public ResponseEntity<NonprofitDTO> createNonprofit(@RequestBody NonprofitDTO nonprofit) {
        NonprofitDTO createdNonprofit = nonprofitService.createNonprofit(nonprofit);
        return new ResponseEntity<>(createdNonprofit, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NonprofitDTO> updateNonprofit(@PathVariable Long id, @RequestBody NonprofitDTO updatedNonprofit) {
        NonprofitDTO updatedEntity = nonprofitService.updateNonprofit(id, updatedNonprofit);
        return new ResponseEntity<>(updatedEntity, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNonprofit(@PathVariable Long id) {
        nonprofitService.deleteNonprofit(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NonprofitDTO> getNonprofitById(@PathVariable Long id) {
        Optional<NonprofitDTO> nonprofit = nonprofitService.getNonprofitById(id);
        return nonprofit.map(ResponseEntity::ok)
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<Page<NonprofitDTO>> getAllNonprofits(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam(defaultValue = "asc") String sortOrder) {
        Page<NonprofitDTO> nonprofits = nonprofitService.getAllNonprofits(page, size, sortBy, sortOrder);
        return new ResponseEntity<>(nonprofits, HttpStatus.OK);
    }
}
