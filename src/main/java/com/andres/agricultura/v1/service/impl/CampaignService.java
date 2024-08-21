package com.andres.agricultura.v1.service.impl;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import com.andres.agricultura.v1.Exceptions.BadRequestException;
import com.andres.agricultura.v1.Exceptions.NotFoundException;
import com.andres.agricultura.v1.dto.CampaignDto;
import com.andres.agricultura.v1.entities.*;
import com.andres.agricultura.v1.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import org.springframework.transaction.annotation.Transactional;

@Service
public class CampaignService {

    @Autowired
    CampaignRepository campaignRepository;

    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    WorkRepository workRepository;

    @Autowired
    FertilizerRepository fertilizerRepository;

    @Autowired
    SupplyRepository supplyRepository;



    @Autowired
    ObjectMapper mapper;

    @Transactional
    public CampaignDto save(CampaignDto campaignDto) {
        Campaign campaign = mapper.convertValue(campaignDto, Campaign.class);

        Set<Supply> supplies = campaign.getSupplies().stream()
                .map(supply -> supplyRepository.findById(supply.getId())
                        .orElseThrow(() -> new BadRequestException("Supply not found with id=" +supply.getId())))
                .collect(Collectors.toSet());
        campaign.getSupplies().clear();
        supplies.forEach(campaign::addSupply);


        Set<Work> works = campaign.getWorks().stream()
                .map(work -> workRepository.findById(work.getId())
                        .orElseThrow(() -> new BadRequestException("Work not found with id=" +work.getId())))
                .collect(Collectors.toSet());
        campaign.getWorks().clear();
        works.forEach(campaign::addWork);


        Set<Fertilizer> fertilizers = campaign.getFertilizers().stream()
                .map(fertilizer -> fertilizerRepository.findById(fertilizer.getId())
                        .orElseThrow(() -> new BadRequestException("Fertilizer not found with id=" +fertilizer.getId())))
                .collect(Collectors.toSet());
        campaign.getFertilizers().clear();
        fertilizers.forEach(campaign::addFertilizer);


        Set<Application> applications = campaign.getApplications().stream()
                .map(application -> applicationRepository.findById(application.getId())
                        .orElseThrow(() -> new BadRequestException("Application not found with id=" + application.getId())))
                .collect(Collectors.toSet());
        campaign.getApplications().clear();
        applications.forEach(campaign::addApplication);

        campaignRepository.save(campaign);
        return mapper.convertValue(campaign, CampaignDto.class);
    }

    @Transactional(readOnly = true)
    public List<CampaignDto> list() {
       return campaignRepository.findAll().stream()
               .map(campaign -> mapper.convertValue(campaign, CampaignDto.class))
               .toList();
    }

    @Transactional(readOnly = true)
    public CampaignDto findById(Long id) {
        return mapper.convertValue(campaignRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Campaign with id:" + id + " not found")), CampaignDto.class);
    }

    @Transactional
    public void delete(Long id) {
        Campaign campaingBD = campaignRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Campaign with id:" + id + " not found"));

        campaingBD.getParcels().forEach(campaingBD::removeParcel);
        campaingBD.getApplications().forEach(campaingBD::removeApplication);
        campaingBD.getFertilizers().forEach(campaingBD::removeFertilizer);
        campaingBD.getWorks().forEach(campaingBD::removeWork);

        campaignRepository.deleteById(id);
    }

    @Transactional
    public CampaignDto update(CampaignDto campaignDto) {
        Campaign campaign = mapper.convertValue(campaignDto, Campaign.class);
        Campaign campaingBD = campaignRepository.findById(campaign.getId())
                .orElseThrow(() -> new NotFoundException("Campaign with id:" + campaign.getId() + " not found"));

        Set<Supply> supplies = campaign.getSupplies().stream()
                .map(supply -> supplyRepository.findById(supply.getId())
                        .orElseThrow(() -> new BadRequestException("Supply not found with id=" +supply.getId())))
                .collect(Collectors.toSet());
        campaingBD.getSupplies().clear();
        supplies.forEach(campaingBD::addSupply);


        Set<Work> works = campaign.getWorks().stream()
                .map(work -> workRepository.findById(work.getId())
                        .orElseThrow(() -> new BadRequestException("Work not found with id=" +work.getId())))
                .collect(Collectors.toSet());
        campaingBD.getWorks().clear();
        works.forEach(campaingBD::addWork);


        Set<Fertilizer> fertilizers = campaign.getFertilizers().stream()
                .map(fertilizer -> fertilizerRepository.findById(fertilizer.getId())
                        .orElseThrow(() -> new BadRequestException("Fertilizer not found with id=" +fertilizer.getId())))
                .collect(Collectors.toSet());
        campaingBD.getFertilizers().clear();
        fertilizers.forEach(campaingBD::addFertilizer);


        Set<Application> applications = campaign.getApplications().stream()
                .map(application -> applicationRepository.findById(application.getId())
                        .orElseThrow(() -> new BadRequestException("Application not found with id=" + application.getId())))
                .collect(Collectors.toSet());
        campaingBD.getApplications().clear();
        applications.forEach(campaingBD::addApplication);

        campaingBD.setName(campaign.getName());
        campaingBD.setTypeCorp(campaign.getTypeCorp());
        campaingBD.setHibryd(campaign.getHibryd());
        campaingBD.setSowingDate(campaign.getSowingDate());
        campaingBD.setDensity(campaign.getDensity());
        campaingBD.setSpaceFurrow(campaign.getSpaceFurrow());

        campaignRepository.save(campaingBD);

        return mapper.convertValue(campaingBD, CampaignDto.class);
    }

    @Transactional(readOnly = true)
    public Map<String, Object> calculateCampaignSummary(Long id) {
        Map<String, Object> summary = new HashMap<>();
        try {
            Campaign campaign = campaignRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Campaign with id:" + id + " not found"));
            AtomicReference<Double> worksTotal = new AtomicReference<>(0.0);
            AtomicReference<Double> suppliesTotal = new AtomicReference<>(0.0);
            Double netPrice;
            Double directCosts;
            Double grossIncome;
            if(campaign.getHarvest() == null){
                new NullPointerException("Harvest is not exist");
            }

            campaign.getWorks().forEach(work -> {
                summary.put(work.getName() + " " + work.getDate(), work.getPriceHectare());
                worksTotal.updateAndGet(v -> v + work.getPriceHectare());

            });
            campaign.getFertilizers().forEach(fertilizer -> {
                summary.put(fertilizer.getName() + " " + fertilizer.getDate(), fertilizer.getPriceHectare());
                suppliesTotal.updateAndGet(v -> v + fertilizer.getPriceHectare());
            });
            campaign.getApplications().forEach(application -> {
                summary.put(application.getActivity() + " " + application.getDate(), application.calculateTotalPriceProducts());
                suppliesTotal.updateAndGet(v -> v + application.calculateTotalPriceProducts());
            });
            campaign.getSupplies().forEach(supply -> {
                summary.put(supply.getName(), supply.getPriceHectare());
                suppliesTotal.updateAndGet(v -> v + supply.getPriceHectare());
            });

            netPrice = campaign.getHarvest().getPrice() -
                                    (campaign.getHarvest().getPrice() * campaign.getHarvest().getCoastComercialization());
            directCosts = suppliesTotal.get() + worksTotal.get();
            grossIncome = netPrice * campaign.getHarvest().getPerformance();

            summary.put("campaign", campaign);
            summary.put("worksTotal", worksTotal);
            summary.put("suppliesTotal", suppliesTotal);
            summary.put("directCosts", directCosts);
            summary.put("netPrice", netPrice);
            summary.put("performance", campaign.getHarvest().getPerformance());
            summary.put("grossIncome", grossIncome);
            summary.put("mb", grossIncome - directCosts);
            summary.put("performanceIndifference", directCosts / netPrice);
            summary.put("mb/cd", (grossIncome - directCosts) / directCosts);

        }catch (NullPointerException e){
           throw new BadRequestException("NullPointerException" + e.getMessage());
        }catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
        return summary;
    }
}
