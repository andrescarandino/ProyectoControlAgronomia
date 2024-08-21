package com.andres.agricultura.v1.service.impl;

import com.andres.agricultura.v1.Exceptions.BadRequestException;
import com.andres.agricultura.v1.Exceptions.NotFoundException;
import com.andres.agricultura.v1.dto.CampaignDto;
import com.andres.agricultura.v1.dto.ParcelDto;
import com.andres.agricultura.v1.entities.Campaign;
import com.andres.agricultura.v1.entities.Parcel;
import com.andres.agricultura.v1.repository.CampaignRepository;
import com.andres.agricultura.v1.repository.ParcelRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ParcelServiceTest {

    @Mock
    ParcelRepository parcelRepository;
    @Mock
    CampaignRepository campaignRepository;
    @Mock
    ObjectMapper mapper;

    @InjectMocks
    private ParcelService parcelService;
    private Parcel parcel;
    private ParcelDto parcelDto;
    private Campaign campaign;
    private CampaignDto campaignDto;



    @BeforeEach
    void setUp() {
        campaign = new Campaign();
        campaign.setId(1L);

        campaignDto = new CampaignDto();
        campaignDto.setId(1L);

        parcel = new Parcel();
        parcel.setId(1L);
        parcel.addCampaign(campaign);

        parcelDto = new ParcelDto();
        parcelDto.setId(1L);
        parcelDto.setCampaings(Set.of(campaignDto));


    }

    @Test
    void testParcelSave_Success() {
        when(mapper.convertValue(parcelDto, Parcel.class)).thenReturn(parcel);
        when(campaignRepository.findById(1L)).thenReturn(Optional.of(campaign));
        when(parcelRepository.save(any(Parcel.class))).thenReturn(parcel);
        when(mapper.convertValue(parcel, ParcelDto.class)).thenReturn(parcelDto);

        ParcelDto result = parcelService.save(parcelDto);

        assertEquals(parcelDto, result);
        assertEquals(1L, result.getId());
    }
    @Test
    void testParcelSave_BadRequestException() {
        when(mapper.convertValue(parcelDto, Parcel.class)).thenReturn(parcel);
        when(campaignRepository.findById(1L)).thenReturn(Optional.empty());


        assertThrows(BadRequestException.class, () -> parcelService.save(parcelDto));

        verify(parcelRepository, times(0)).save(parcel);
    }

    @Test
    void testParcelList_Success() {
        when(parcelRepository.findAll()).thenReturn(List.of(parcel));
        when(mapper.convertValue(parcel, ParcelDto.class)).thenReturn(parcelDto);

        List<ParcelDto> result = parcelService.list();

        assertEquals(parcelDto, result.get(0));
        assertEquals(1L, result.get(0).getId());
        assertEquals(1, result.size());
    }

    @Test
    void testParcelFindById_Success() {
        when(parcelRepository.findById(1L)).thenReturn(Optional.of(parcel));
        when(mapper.convertValue(parcel, ParcelDto.class)).thenReturn(parcelDto);

        ParcelDto result = parcelService.findById(1L);

        assertEquals(parcelDto, result);
        assertEquals(1L, result.getId());
    }
    @Test
    void testParcelFindById_NotFoundException() {
        when(parcelRepository.findById(1L)).thenReturn(Optional.empty());


        assertThrows(NotFoundException.class, () -> parcelService.findById(1L));

        verify(parcelRepository, times(1)).findById(1L);
    }

    @Test
    void testParcelDelete_Success() {
        when(parcelRepository.findById(1L)).thenReturn(Optional.of(parcel));

        parcelService.delete(1L);

        verify(parcelRepository, times(1)).deleteById(1L);
        verify(parcelRepository, times(1)).findById(1L);
    }
    @Test
    void testParcelDelete_NotFoundException() {
        when(parcelRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> parcelService.delete(1L));

        verify(parcelRepository, times(0)).deleteById(1L);
        verify(parcelRepository, times(1)).findById(1L);
    }

    @Test
    void testParcelUpdate_Success() {
        when(parcelRepository.findById(1L)).thenReturn(Optional.of(parcel));
        when(campaignRepository.findById(1L)).thenReturn(Optional.of(campaign));
        when(parcelRepository.save(any(Parcel.class))).thenReturn(parcel);
        when(mapper.convertValue(parcel, ParcelDto.class)).thenReturn(parcelDto);

        ParcelDto result = parcelService.update(parcelDto);

        assertEquals(parcelDto, result);
        assertEquals(1L, result.getId());

    }

    @Test
    void testParcelUpdate_ParcelNotFoundException() {
        when(parcelRepository.findById(1L)).thenReturn(Optional.empty());




        assertThrows(NotFoundException.class, () -> parcelService.update(parcelDto));

        verify(parcelRepository, times(1)).findById(1L);
        verify(parcelRepository, times(0)).save(parcel);

    }

    @Test
    void testParcelUpdate_CampaingBadRequestException() {
        when(parcelRepository.findById(1L)).thenReturn(Optional.of(parcel));
        when(campaignRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(BadRequestException.class, () -> parcelService.update(parcelDto));

        verify(parcelRepository, times(1)).findById(1L);
        verify(campaignRepository, times(1)).findById(1L);
        verify(parcelRepository, times(0)).save(parcel);
    }

}