package com.andres.agricultura.v1.service.impl;

import com.andres.agricultura.v1.Exceptions.NotFoundException;
import com.andres.agricultura.v1.dto.WorkDto;
import com.andres.agricultura.v1.entities.Work;
import com.andres.agricultura.v1.repository.WorkRepository;
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
class WorkServiceTest {

    @Mock
    WorkRepository workRepository;

    @Mock
    ObjectMapper mapper;

    @InjectMocks
    private WorkService workService;
    private WorkDto workDto;
    private Work work;

    @BeforeEach
    void SetUp(){
        work = new Work();
        work.setId(1L);

        workDto = new WorkDto();
        workDto.setId(1L);
    }

    @Test
    void testWorkSave_Success() {
        when(mapper.convertValue(workDto, Work.class)).thenReturn(work);
        when(workRepository.save(any(Work.class))).thenReturn(work);
        when(mapper.convertValue(work, WorkDto.class)).thenReturn(workDto);

        WorkDto result = workService.save(workDto);

        assertEquals(workDto, result);
    }

    @Test
    void testWorkList_Success() {
        when(workRepository.findAll()).thenReturn(List.of(work));
        when(mapper.convertValue(work, WorkDto.class)).thenReturn(workDto);

        List<WorkDto> result = workService.list();

        assertEquals(1, result.size());
        assertEquals(workDto, result.get(0));
    }

    @Test
    void testWorkFindById_Success() {
        when(workRepository.findById(1L)).thenReturn(Optional.of(work));
        when(mapper.convertValue(work, WorkDto.class)).thenReturn(workDto);

        WorkDto result = workService.findById(1L);

        assertEquals(workDto, result);
        assertEquals(1L, result.getId());
        assertEquals(WorkDto.class, result.getClass());
    }
    @Test
    void testWorkFindById_NotFoundException() {
        when(workRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            workService.findById(1L);
        });

        verify(mapper, times(0)).convertValue(work, WorkDto.class);
    }

    @Test
    void testWorkDelete_Success() {
        when(workRepository.findById(1L)).thenReturn(Optional.of(work));

        workService.delete(1L);

        verify(workRepository, times(1)).findById(1L);
        verify(workRepository, times(1)).deleteById(1L);

    }
    @Test
    void testWorkDelete_NotFoundException() {
        when(workRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            workService.findById(1L);
        });

        verify(workRepository, times(1)).findById(1L);
        verify(workRepository, times(0)).deleteById(1L);

    }

    @Test
    void testWorkUpdate_Success() {
        when(mapper.convertValue(workDto, Work.class)).thenReturn(work);
        when(workRepository.findById(1L)).thenReturn(Optional.of(work));
        when(workRepository.save(any(Work.class))).thenReturn(work);
        when(mapper.convertValue(work, WorkDto.class)).thenReturn(workDto);

        WorkDto result = workService.update(workDto);

        assertEquals(workDto, result);

        verify(workRepository, times(1)).findById(1L);
        verify(workRepository, times(1)).save(work);
    }

    @Test
    void testWorkUpdate_NotFoundException() {
        when(mapper.convertValue(workDto, Work.class)).thenReturn(work);
        when(workRepository.findById(1L)).thenReturn(Optional.empty());


        assertThrows(NotFoundException.class, () -> {
            workService.update(workDto);
        });

        verify(workRepository, times(1)).findById(1L);
        verify(workRepository, times(0)).save(work);
    }
}