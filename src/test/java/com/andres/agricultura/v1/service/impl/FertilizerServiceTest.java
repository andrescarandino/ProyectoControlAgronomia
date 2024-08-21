package com.andres.agricultura.v1.service.impl;

import com.andres.agricultura.v1.Exceptions.NotFoundException;
import com.andres.agricultura.v1.dto.FertilizerDto;
import com.andres.agricultura.v1.entities.Fertilizer;
import com.andres.agricultura.v1.repository.FertilizerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FertilizerServiceTest {

    @Mock
    FertilizerRepository fertilizerRepository;
    @Mock
    ObjectMapper mapper;

    @InjectMocks
    private FertilizerService fertilizerService;
    private Fertilizer fertilizer;
    private FertilizerDto fertilizerDto;

    @BeforeEach
    void setUp() {
        fertilizer = new Fertilizer();
        fertilizer.setId(1L);

        fertilizerDto = new FertilizerDto();
        fertilizerDto.setId(1L);
    }

    @Test
    void testFertilizerSave_Success() {
        when(mapper.convertValue(fertilizerDto, Fertilizer.class)).thenReturn(fertilizer);
        when(fertilizerRepository.save(any(Fertilizer.class))).thenReturn(fertilizer);
        when(mapper.convertValue(fertilizer, FertilizerDto.class)).thenReturn(fertilizerDto);

        FertilizerDto result = fertilizerService.save(fertilizerDto);

        assertEquals(fertilizerDto, result);
        assertEquals(1L, result.getId());
        assertEquals(FertilizerDto.class, result.getClass());
    }

    @Test
    void testFertilizerList_Success() {
        when(fertilizerRepository.findAll()).thenReturn(List.of(fertilizer));
        when(mapper.convertValue(fertilizer, FertilizerDto.class)).thenReturn(fertilizerDto);

        List<FertilizerDto> result = fertilizerService.list();

        assertEquals(fertilizerDto, result.get(0));
        assertEquals(1, result.size());
    }

    @Test
    void testFertilizerFindById_Success() {
        when(fertilizerRepository.findById(1L)).thenReturn(Optional.of(fertilizer));
        when(mapper.convertValue(fertilizer, FertilizerDto.class)).thenReturn(fertilizerDto);

        FertilizerDto result = fertilizerService.findById(1L);

        assertEquals(fertilizerDto, result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testFertilizerFindById_NotFoundException() {
        when(fertilizerRepository.findById(1L)).thenReturn(Optional.empty());


       assertThrows(NotFoundException.class, () -> fertilizerService.findById(1L));

        verify(fertilizerRepository, times(1)).findById(1L);
        verify(mapper, times(0)).convertValue(fertilizer, FertilizerDto.class);
    }

    @Test
    void testFertilizerDelete_Success() {
        when(fertilizerRepository.findById(1L)).thenReturn(Optional.of(fertilizer));

        fertilizerService.delete(1L);

        verify(fertilizerRepository, times(1)).findById(1L);
        verify(fertilizerRepository, times(1)).deleteById(1L);
    }

    @Test
    void testFertilizerDelete_NotFoundException() {
        when(fertilizerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,() -> fertilizerService.delete(1L));

        verify(fertilizerRepository, times(1)).findById(1L);
        verify(fertilizerRepository, times(0)).deleteById(1L);
    }

    @Test
    void testFertilizerUpdate_Success() {
        when(mapper.convertValue(fertilizerDto, Fertilizer.class)).thenReturn(fertilizer);
        when(fertilizerRepository.findById(1L)).thenReturn(Optional.of(fertilizer));
        when(fertilizerRepository.save(any(Fertilizer.class))).thenReturn(fertilizer);
        when(mapper.convertValue(fertilizer, FertilizerDto.class)).thenReturn(fertilizerDto);

        FertilizerDto result = fertilizerService.update(fertilizerDto);

        assertEquals(fertilizerDto, result);
        assertEquals(1L, result.getId());
        assertEquals(FertilizerDto.class, result.getClass());
        verify(fertilizerRepository, times(1)).findById(1L);
        verify(fertilizerRepository, times(1)).save(fertilizer);
    }

    @Test
    void testFertilizerUpdate_NotFoundException() {
        when(mapper.convertValue(fertilizerDto, Fertilizer.class)).thenReturn(fertilizer);
        when(fertilizerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> fertilizerService.update(fertilizerDto));


        verify(fertilizerRepository, times(1)).findById(1L);
        verify(fertilizerRepository, times(0)).save(fertilizer);
    }


}