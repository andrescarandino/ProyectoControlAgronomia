package com.andres.agricultura.v1.controller.impl;

import java.util.List;
import java.util.Map;


import com.andres.agricultura.v1.dto.CampaignDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.andres.agricultura.v1.service.impl.CampaignService;

@RestController
@RequestMapping("/campaigns")
public class CampaignController {

    @Autowired
    CampaignService campaignService;

    @PostMapping
    public ResponseEntity<CampaignDto> save(@Valid @RequestBody CampaignDto campaignDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(campaignService.save(campaignDto));
    }

    @GetMapping
    public ResponseEntity<List<CampaignDto>> list() {
        return ResponseEntity.status(HttpStatus.OK).body(campaignService.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CampaignDto> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(campaignService.findById(id));
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        campaignService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping
    public ResponseEntity<CampaignDto> update(@Valid @RequestBody CampaignDto campaignDto) {
        return ResponseEntity.status(HttpStatus.OK).body(campaignService.update(campaignDto));
    }

    @GetMapping("/summary/{id}")
    public ResponseEntity<Map<String, Object>> summary(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(campaignService.calculateCampaignSummary(id));
    }

}
