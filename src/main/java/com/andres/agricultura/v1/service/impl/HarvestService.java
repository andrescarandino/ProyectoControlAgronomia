package com.andres.agricultura.v1.service.impl;

import com.andres.agricultura.v1.Exceptions.NotFoundException;
import com.andres.agricultura.v1.dto.HarvestDto;
import com.andres.agricultura.v1.entities.Campaign;
import com.andres.agricultura.v1.entities.Harvest;
import com.andres.agricultura.v1.repository.CampaignRepository;
import com.andres.agricultura.v1.repository.HarvestRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HarvestService {

    @Autowired
    HarvestRepository harvestRepository;
    @Autowired
    ObjectMapper mapper;
    @Autowired
    CampaignRepository campaignRepository;

    public HarvestDto save(HarvestDto harvestDto){
        Harvest harvest = mapper.convertValue(harvestDto, Harvest.class);
        Campaign campaign = campaignRepository.findById(harvestDto.getCampaing().getId())
                .orElseThrow(() -> new NotFoundException("Campaign not found with id=" +harvestDto.getCampaing().getId()));
        harvestRepository.save(harvest);
        campaign.setHarvest(harvest);
        campaignRepository.save(campaign);
        return mapper.convertValue(harvest, HarvestDto.class);
    }

    public void delete(Long id) {
        Harvest harvest = harvestRepository.findById(id).orElseThrow(() -> new NotFoundException("Harvest not found with id=" +id));
        Campaign campaign = campaignRepository.findById(harvest.getCampaign().getId())
                .orElseThrow(() -> new NotFoundException("Campaign not found with id=" +harvest.getCampaign().getId()));
        campaign.setHarvest(null);
        campaignRepository.save(campaign);
        harvestRepository.deleteById(id);
    }


    public HarvestDto update(HarvestDto harvestDto) {
        Harvest harvest = harvestRepository.findById(harvestDto.getId())
                .orElseThrow(() -> new NotFoundException("Harvest not found with id=" +harvestDto.getId()));
        harvest.setDate(harvestDto.getDate());
        harvest.setPrice(harvestDto.getPrice());
        harvest.setPerformance(harvestDto.getPerformance());
        harvest.setCoastComercialization(harvestDto.getCoastComercialization());
        harvestRepository.save(harvest);
        return mapper.convertValue(harvest, HarvestDto.class);
    }
}
