package com.andres.agricultura.v1.service.impl;

import com.andres.agricultura.v1.Exceptions.NotFoundException;
import com.andres.agricultura.v1.dto.CampaignDto;
import com.andres.agricultura.v1.dto.HarvestDto;
import com.andres.agricultura.v1.entities.Campaign;
import com.andres.agricultura.v1.entities.Harvest;
import com.andres.agricultura.v1.repository.CampaignRepository;
import com.andres.agricultura.v1.repository.HarvestRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HarvestServiceTest {

    @Mock
    HarvestRepository harvestRepository;
    @Mock
    CampaignRepository campaignRepository;
    @Mock
    ObjectMapper mapper;

    @InjectMocks
    private HarvestService harvestService;
    private Harvest harvest;
    private HarvestDto harvestDto;
    private Campaign campaign;
    private CampaignDto campaignDto;


    @BeforeEach
    void setUp() {
        harvest=new Harvest();
        harvest.setId(1L);

        campaign = new Campaign();
        campaign.setId(1L);
        campaign.setHarvest(harvest);

        harvest.setCampaign(campaign);

        harvestDto = new HarvestDto();
        harvestDto.setId(1L);


        campaignDto = new CampaignDto();
        campaignDto.setId(1L);
        campaignDto.setHarvest(harvestDto);

        harvestDto.setCampaing(campaignDto);
        harvestDto.getCampaing().setId(1L);
    }

    @Test
    void testHarvestSave_Success() {
        when(mapper.convertValue(harvestDto, Harvest.class)).thenReturn(harvest);
        when(campaignRepository.findById(1L)).thenReturn(Optional.of(campaign));
        when(harvestRepository.save(any(Harvest.class))).thenReturn(harvest);
        when(campaignRepository.save(any(Campaign.class))).thenReturn(campaign);
        when(mapper.convertValue(harvest, HarvestDto.class)).thenReturn(harvestDto);

        HarvestDto result = harvestService.save(harvestDto);

        assertEquals(harvestDto, result);
        assertEquals(HarvestDto.class, result.getClass());

    }
    @Test
    void testHarvestSave_NotFoundException() {
        when(mapper.convertValue(harvestDto, Harvest.class)).thenReturn(harvest);
        when(campaignRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> harvestService.save(harvestDto));

        verify(campaignRepository, times(1)).findById(1L);
        verify(harvestRepository, times(0)).save(harvest);
        verify(campaignRepository, times(0)).save(campaign);

    }

    @Test
    void testHarvestDelete_Success() {
        when(harvestRepository.findById(1L)).thenReturn(Optional.of(harvest));
        when(campaignRepository.findById(1L)).thenReturn(Optional.of(campaign));
        when(campaignRepository.save(any(Campaign.class))).thenReturn(campaign);

        harvestService.delete(1L);

        verify(harvestRepository, times(1)).deleteById(1L);
    }

    @Test
    void testHarvestDelete_Harvest_NotFoundException() {
        when(harvestRepository.findById(1L)).thenReturn(Optional.empty());


        assertThrows(NotFoundException.class,() -> harvestService.delete(1L));

        verify(campaignRepository, times(0)).save(campaign);
        verify(harvestRepository, times(0)).deleteById(1L);
    }
    @Test
    void testHarvestDelete_Campaing_NotFoundException() {
        when(harvestRepository.findById(1L)).thenReturn(Optional.of(harvest));
        when(campaignRepository.findById(1L)).thenReturn(Optional.empty());


        assertThrows(NotFoundException.class,() -> harvestService.delete(1L));

        verify(harvestRepository, times(1)).findById(1L);
        verify(campaignRepository, times(1)).findById(1L);
        verify(campaignRepository, times(0)).save(campaign);
        verify(harvestRepository, times(0)).deleteById(1L);
    }

    @Test
    void testHarvestUpdate_Success() {
        when(harvestRepository.findById(1L)).thenReturn(Optional.of(harvest));
        when(harvestRepository.save(any(Harvest.class))).thenReturn(harvest);
        when(mapper.convertValue(harvest, HarvestDto.class)).thenReturn(harvestDto);

        HarvestDto result = harvestService.update(harvestDto);

        assertEquals(harvestDto, result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testHarvestUpdate_NotFoundException() {
        when(harvestRepository.findById(1L)).thenReturn(Optional.empty());


        assertThrows(NotFoundException.class, () -> harvestService.update(harvestDto));

        verify(harvestRepository, times(0)).save(harvest);
    }
}