package com.andres.agricultura.v1.service.impl;

import com.andres.agricultura.v1.Exceptions.NotFoundException;
import com.andres.agricultura.v1.dto.SupplyDto;
import com.andres.agricultura.v1.entities.Supply;
import com.andres.agricultura.v1.repository.SupplyRepository;
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
class SupplyServiceTest {

    @Mock
    SupplyRepository supplyRepository;
    @Mock
    ObjectMapper mapper;

    @InjectMocks
    private SupplyService supplyService;
    private Supply supply;
    private SupplyDto supplyDto;


    @BeforeEach
    void SetUp(){

        supply = new Supply();
        supply.setId(1L);

        supplyDto = new SupplyDto();
        supplyDto.setId(1L);
    }

    @Test
    void testSupplySave_Success() {
        when(mapper.convertValue(supplyDto, Supply.class)).thenReturn(supply);
        when(supplyRepository.save(any(Supply.class))).thenReturn(supply);
        when(mapper.convertValue(supply, SupplyDto.class)).thenReturn(supplyDto);

        SupplyDto result = supplyService.save(supplyDto);

        assertEquals(supplyDto, result);
        assertEquals(SupplyDto.class, result.getClass());

    }

    @Test
    void testSupplyList_Success() {
        when(supplyRepository.findAll()).thenReturn(List.of(supply));
        when(mapper.convertValue(supply, SupplyDto.class)).thenReturn(supplyDto);

        List<SupplyDto> result = supplyService.list();

        assertEquals(supplyDto, result.get(0));
        assertEquals(1, result.size());
    }

    @Test
    void testSupplyFindById_Success() {
        when(supplyRepository.findById(1L)).thenReturn(Optional.of(supply));
        when(mapper.convertValue(supply, SupplyDto.class)).thenReturn(supplyDto);

        SupplyDto result = supplyService.findById(1L);

        assertEquals(1L, result.getId());
        assertEquals(supplyDto, result);

    }
    @Test
    void testSupplyFindById_NotFoundException() {
        when(supplyRepository.findById(1L)).thenReturn(Optional.empty());


        assertThrows(NotFoundException.class, () -> {
            supplyService.findById(1L);
        });

        verify(supplyRepository, times(1)).findById(1L);
        verify(mapper, times(0)).convertValue(supply, SupplyDto.class);

    }

    @Test
    void testSupplyDelete_Success() {
        when(supplyRepository.findById(1L)).thenReturn(Optional.of(supply));

        supplyService.delete(1L);

        verify(supplyRepository, times(1)).findById(1L);
        verify(supplyRepository, times(1)).deleteById(1L);
    }

    @Test
    void testSupplyDelete_NotFoundException() {
        when(supplyRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            supplyService.delete(1L);
        });

        verify(supplyRepository, times(1)).findById(1L);
        verify(supplyRepository, times(0)).deleteById(1L);
    }

    @Test
    void testSupplyUpdate_Success() {
        when(mapper.convertValue(supplyDto, Supply.class)).thenReturn(supply);
        when(supplyRepository.findById(1L)).thenReturn(Optional.of(supply));
        when(supplyRepository.save(any(Supply.class))).thenReturn(supply);
        when(mapper.convertValue(supply, SupplyDto.class)).thenReturn(supplyDto);

        SupplyDto result = supplyService.update(supplyDto);

        assertEquals(supplyDto, result);

        verify(supplyRepository, times(1)).findById(1L);
        verify(supplyRepository, times(1)).save(supply);
    }

    @Test
    void testSupplyUpdate_NotFoundException() {
        when(mapper.convertValue(supplyDto, Supply.class)).thenReturn(supply);
        when(supplyRepository.findById(1L)).thenReturn(Optional.empty());


        assertThrows(NotFoundException.class, () -> {
            supplyService.update(supplyDto);
        });



        verify(supplyRepository, times(1)).findById(1L);
        verify(supplyRepository, times(0)).save(supply);
    }
}