package com.andres.agricultura.v1.service.impl;

import com.andres.agricultura.v1.Exceptions.BadRequestException;
import com.andres.agricultura.v1.Exceptions.NotFoundException;
import com.andres.agricultura.v1.dto.ClientDto;
import com.andres.agricultura.v1.dto.ParcelDto;
import com.andres.agricultura.v1.entities.Client;
import com.andres.agricultura.v1.entities.Parcel;
import com.andres.agricultura.v1.repository.ClientRepository;
import com.andres.agricultura.v1.repository.ParcelRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    ClientRepository clientRepository;
    @Mock
    ParcelRepository parcelRepository;
    @Mock
    ObjectMapper mapper;

    @InjectMocks
    private ClientService clientService;

    private Parcel parcel;
    private ParcelDto parcelDto;
    private Client client;
    private ClientDto clientDto;

    @BeforeEach
    void SetUp() {
        parcel = new Parcel();
        parcel.setId(1L);

        client = new Client();
        client.setId(1L);
        client.addParcel(parcel);

        parcelDto = new ParcelDto();
        parcelDto.setId(1L);

        clientDto = new ClientDto();
        clientDto.setId(1L);
        clientDto.setParcels(Set.of(parcelDto));
    }


    @Test
    void testClientSave_Success() {
        when(mapper.convertValue(clientDto, Client.class)).thenReturn(client);
        when(parcelRepository.findById(1L)).thenReturn(Optional.of(parcel));
        when(clientRepository.save(any(Client.class))).thenReturn(client);
        when(mapper.convertValue(client, ClientDto.class)).thenReturn(clientDto);

        ClientDto result = clientService.save(clientDto);

        assertEquals(clientDto, result);
    }
    @Test
    void testClientSave_BadRequestException() {
        when(mapper.convertValue(clientDto, Client.class)).thenReturn(client);
        when(parcelRepository.findById(1L)).thenReturn(Optional.empty());


        assertThrows(BadRequestException.class, () -> clientService.save(clientDto));

        verify(parcelRepository, times(1)).findById(1L);
        verify(clientRepository, times(0)).save(client);
    }

    @Test
    void testClientList_Success() {
        when(clientRepository.findAll()).thenReturn(List.of(client));
        when(mapper.convertValue(client, ClientDto.class)).thenReturn(clientDto);

        List<ClientDto> result = clientService.list();

        assertEquals(clientDto, result.get(0));
        assertEquals(1, result.size());
    }

    @Test
    void testClientFindById_Success() {
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(mapper.convertValue(client, ClientDto.class)).thenReturn(clientDto);

        ClientDto result = clientService.findById(1L);

        assertEquals(1L, result.getId());
        assertEquals(ClientDto.class, result.getClass());
    }
    @Test
    void testClientFindById_NotFoundException() {
        when(clientRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,() -> clientService.findById(1L));

        verify(clientRepository, times(1)).findById(1L);
    }

    @Test
    void testClientDelete_Success() {
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

        clientService.delete(1L);

        verify(clientRepository, times(1)).findById(1L);
        verify(clientRepository, times(1)).deleteById(1L);
    }
    @Test
    void testClientDelete_NotFoundException() {
        when(clientRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> clientService.delete(1L));

        verify(clientRepository, times(1)).findById(1L);
        verify(clientRepository, times(0)).deleteById(1L);
    }

    @Test
    void testClientUpdate_Success() {
        when(mapper.convertValue(clientDto, Client.class)).thenReturn(client);
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(parcelRepository.findById(1L)).thenReturn(Optional.of(parcel));
        when(clientRepository.saveAndFlush(any(Client.class))).thenReturn(client);
        when(mapper.convertValue(client, ClientDto.class)).thenReturn(clientDto);

        ClientDto result = clientService.update(clientDto);

        assertEquals(clientDto, result);
        assertEquals(1L, result.getId());
        assertEquals(ClientDto.class, result.getClass());
        verify(clientRepository, times(1)).saveAndFlush(client);
    }

    @Test
    void testClientUpdate_NotFoundException() {
        when(mapper.convertValue(clientDto, Client.class)).thenReturn(client);
        when(clientRepository.findById(1L)).thenReturn(Optional.empty());

       assertThrows(NotFoundException.class, () -> clientService.update(clientDto));

        verify(clientRepository, times(1)).findById(1L);
        verify(clientRepository, times(0)).saveAndFlush(client);
    }

    @Test
    void testClientUpdate_BadRequestException() {
        when(mapper.convertValue(clientDto, Client.class)).thenReturn(client);
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(parcelRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(BadRequestException.class, () -> clientService.update(clientDto));


        verify(clientRepository, times(1)).findById(1L);
        verify(parcelRepository, times(1)).findById(1L);
        verify(clientRepository, times(0)).saveAndFlush(any(Client.class));
    }

}