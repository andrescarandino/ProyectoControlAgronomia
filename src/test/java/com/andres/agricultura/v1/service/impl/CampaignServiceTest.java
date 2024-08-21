package com.andres.agricultura.v1.service.impl;

import com.andres.agricultura.v1.Exceptions.BadRequestException;
import com.andres.agricultura.v1.Exceptions.NotFoundException;
import com.andres.agricultura.v1.dto.CampaignDto;
import com.andres.agricultura.v1.entities.*;
import com.andres.agricultura.v1.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CampaignServiceTest {
    @Mock
    CampaignRepository campaignRepository;
    @Mock
    ApplicationRepository applicationRepository;
    @Mock
    WorkRepository workRepository;
    @Mock
    FertilizerRepository fertilizerRepository;
    @Mock
    SupplyRepository supplyRepository;
    @Mock
    ObjectMapper mapper;

    @InjectMocks
    private CampaignService campaignService;

    private Campaign campaign;
    private CampaignDto campaignDto;
    private Application application;
    private Work work;
    private Fertilizer fertilizer;
    private Supply supply;

    @BeforeEach
    void SetUp() {
        campaignDto = new CampaignDto();
        campaignDto.setId(1L);

        campaign = new Campaign();
        campaign.setId(1L);

        application = new Application();
        application.setId(1L);

        work = new Work();
        work.setId(1L);

        fertilizer = new Fertilizer();
        fertilizer.setId(1L);

        supply = new Supply();
        supply.setId(1L);

        campaign.addSupply(supply);
        campaign.addWork(work);
        campaign.addApplication(application);
        campaign.addFertilizer(fertilizer);

    }

    @Test
    void testCampaignSave_Success() {
        when(mapper.convertValue(campaignDto, Campaign.class)).thenReturn(campaign);
        when(supplyRepository.findById(1L)).thenReturn(Optional.of(supply));
        when(workRepository.findById(1L)).thenReturn(Optional.of(work));
        when(fertilizerRepository.findById(1L)).thenReturn(Optional.of(fertilizer));
        when(applicationRepository.findById(1L)).thenReturn(Optional.of(application));
        when(campaignRepository.save(any(Campaign.class))).thenReturn(campaign);
        when(mapper.convertValue(campaign, CampaignDto.class)).thenReturn(campaignDto);

        CampaignDto result = campaignService.save(campaignDto);

        assertEquals(campaignDto, result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testCampaignSave_SupplyBadRequestException() {
        when(mapper.convertValue(campaignDto, Campaign.class)).thenReturn(campaign);
        when(supplyRepository.findById(1L)).thenReturn(Optional.empty());


        assertThrows(BadRequestException.class, () -> campaignService.save(campaignDto));

        verify(supplyRepository, times(1)).findById(1L);
        verify(campaignRepository, times(0)).save(campaign);
    }

    @Test
    void testCampaignSave_WorkBadRequestException() {
        when(mapper.convertValue(campaignDto, Campaign.class)).thenReturn(campaign);
        when(supplyRepository.findById(1L)).thenReturn(Optional.of(supply));
        when(workRepository.findById(1L)).thenReturn(Optional.empty());


        assertThrows(BadRequestException.class, () -> campaignService.save(campaignDto));


        verify(supplyRepository, times(1)).findById(1L);
        verify(workRepository, times(1)).findById(1L);
        verify(campaignRepository, times(0)).save(campaign);
    }

    @Test
    void testCampaignSave_FertilizerBadRequestException() {
        when(mapper.convertValue(campaignDto, Campaign.class)).thenReturn(campaign);
        when(supplyRepository.findById(1L)).thenReturn(Optional.of(supply));
        when(workRepository.findById(1L)).thenReturn(Optional.of(work));
        when(fertilizerRepository.findById(1L)).thenReturn(Optional.empty());


        assertThrows(BadRequestException.class, () -> campaignService.save(campaignDto));


        verify(supplyRepository, times(1)).findById(1L);
        verify(workRepository, times(1)).findById(1L);
        verify(fertilizerRepository, times(1)).findById(1L);
        verify(campaignRepository, times(0)).save(campaign);
    }

    @Test
    void testCampaignSave_ApplicationBadRequestException() {
        when(mapper.convertValue(campaignDto, Campaign.class)).thenReturn(campaign);
        when(supplyRepository.findById(1L)).thenReturn(Optional.of(supply));
        when(workRepository.findById(1L)).thenReturn(Optional.of(work));
        when(fertilizerRepository.findById(1L)).thenReturn(Optional.of(fertilizer));
        when(applicationRepository.findById(1L)).thenReturn(Optional.empty());


        assertThrows(BadRequestException.class, () -> campaignService.save(campaignDto));


        verify(supplyRepository, times(1)).findById(1L);
        verify(workRepository, times(1)).findById(1L);
        verify(fertilizerRepository, times(1)).findById(1L);
        verify(applicationRepository, times(1)).findById(1L);
        verify(campaignRepository, times(0)).save(campaign);
    }

    @Test
    void testCampaingList_Success() {
        when(campaignRepository.findAll()).thenReturn(List.of(campaign));
        when(mapper.convertValue(campaign, CampaignDto.class)).thenReturn(campaignDto);

        List<CampaignDto> result = campaignService.list();

        assertEquals(1, result.size());
        assertEquals(campaignDto, result.get(0));

    }

    @Test
    void testCampaingFindById_Success() {
        when(campaignRepository.findById(1L)).thenReturn(Optional.of(campaign));
        when(mapper.convertValue(campaign, CampaignDto.class)).thenReturn(campaignDto);

        CampaignDto result = campaignService.findById(1L);

        assertEquals(1l, result.getId());
        assertEquals(campaignDto, result);
    }

    @Test
    void testCampaingFindById_NotFoundException() {
        when(campaignRepository.findById(1L)).thenReturn(Optional.empty());

       assertThrows(NotFoundException.class, () -> campaignService.findById(1L));

        verify(campaignRepository, times(1)).findById(1L);
    }

    @Test
    void testCampaingDelete_Success() {
        when(campaignRepository.findById(1L)).thenReturn(Optional.of(campaign));

        campaignService.delete(1L);

        verify(campaignRepository, times(1)).deleteById(1L);
    }

    @Test
    void testCampaingDelete_NotFoundException() {
        when(campaignRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> campaignService.delete(1L));

        verify(campaignRepository, times(1)).findById(1L);
        verify(campaignRepository, times(0)).deleteById(1L);
    }

    @Test
    void TestCampaingUpdate_Success() {
        when(mapper.convertValue(campaignDto, Campaign.class)).thenReturn(campaign);
        when(campaignRepository.findById(1L)).thenReturn(Optional.of(campaign));
        when(supplyRepository.findById(1L)).thenReturn(Optional.of(supply));
        when(workRepository.findById(1L)).thenReturn(Optional.of(work));
        when(fertilizerRepository.findById(1L)).thenReturn(Optional.of(fertilizer));
        when(applicationRepository.findById(1L)).thenReturn(Optional.of(application));
        when(campaignRepository.save(any(Campaign.class))).thenReturn(campaign);
        when(mapper.convertValue(campaign, CampaignDto.class)).thenReturn(campaignDto);

        CampaignDto result = campaignService.update(campaignDto);

        assertEquals(campaignDto, result);
        assertEquals(1L, result.getId());
    }
    @Test
    void TestCampaingUpdate_NotFoundException() {
        when(mapper.convertValue(campaignDto, Campaign.class)).thenReturn(campaign);
        when(campaignRepository.findById(1L)).thenReturn(Optional.empty());


        assertThrows(NotFoundException.class, () -> campaignService.update(campaignDto));

        verify(campaignRepository, times(0)).save(campaign);
        verify(campaignRepository, times(1)).findById(1l);

    }
    @Test
    void TestCampaingUpdate_BadRequestExceptionSupply() {
        when(mapper.convertValue(campaignDto, Campaign.class)).thenReturn(campaign);
        when(campaignRepository.findById(1L)).thenReturn(Optional.of(campaign));
        when(supplyRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(BadRequestException.class, () -> campaignService.update(campaignDto));

        verify(campaignRepository, times(0)).save(campaign);
        verify(campaignRepository, times(1)).findById(1l);
        verify(supplyRepository, times(1)).findById(1L);
    }
    @Test
    void TestCampaingUpdate_BadRequestExceptionWork() {
        when(mapper.convertValue(campaignDto, Campaign.class)).thenReturn(campaign);
        when(campaignRepository.findById(1L)).thenReturn(Optional.of(campaign));
        when(supplyRepository.findById(1L)).thenReturn(Optional.of(supply));
        when(workRepository.findById(1L)).thenReturn(Optional.empty());


        assertThrows(BadRequestException.class, () -> campaignService.update(campaignDto));

        verify(campaignRepository, times(0)).save(campaign);
        verify(campaignRepository, times(1)).findById(1l);
        verify(supplyRepository, times(1)).findById(1L);
        verify(workRepository, times(1)).findById(1L);
    }

    @Test
    void TestCampaingUpdate_BadRequestExceptionFertilizer() {
        when(mapper.convertValue(campaignDto, Campaign.class)).thenReturn(campaign);
        when(campaignRepository.findById(1L)).thenReturn(Optional.of(campaign));
        when(supplyRepository.findById(1L)).thenReturn(Optional.of(supply));
        when(workRepository.findById(1L)).thenReturn(Optional.of(work));
        when(fertilizerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(BadRequestException.class, () -> campaignService.update(campaignDto));

        verify(campaignRepository, times(0)).save(campaign);
        verify(campaignRepository, times(1)).findById(1l);
        verify(supplyRepository, times(1)).findById(1L);
        verify(workRepository, times(1)).findById(1L);
        verify(fertilizerRepository, times(1)).findById(1L);
    }
    @Test
    void TestCampaingUpdate_BadRequestExceptionApplication() {
        when(mapper.convertValue(campaignDto, Campaign.class)).thenReturn(campaign);
        when(campaignRepository.findById(1L)).thenReturn(Optional.of(campaign));
        when(supplyRepository.findById(1L)).thenReturn(Optional.of(supply));
        when(workRepository.findById(1L)).thenReturn(Optional.of(work));
        when(fertilizerRepository.findById(1L)).thenReturn(Optional.of(fertilizer));
        when(applicationRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(BadRequestException.class, () -> campaignService.update(campaignDto));

        verify(campaignRepository, times(0)).save(campaign);
        verify(campaignRepository, times(1)).findById(1l);
        verify(supplyRepository, times(1)).findById(1L);
        verify(workRepository, times(1)).findById(1L);
        verify(fertilizerRepository, times(1)).findById(1L);
        verify(applicationRepository, times(1)).findById(1L);
    }

}