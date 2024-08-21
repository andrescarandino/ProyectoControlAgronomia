package com.andres.agricultura.v1.service.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.andres.agricultura.v1.Exceptions.BadRequestException;
import com.andres.agricultura.v1.Exceptions.NotFoundException;
import com.andres.agricultura.v1.dto.ClientDto;
import com.andres.agricultura.v1.entities.Parcel;
import com.andres.agricultura.v1.repository.ParcelRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andres.agricultura.v1.entities.Client;
import com.andres.agricultura.v1.repository.ClientRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientService{

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    ParcelRepository parcelRepository;

    @Autowired
    ObjectMapper mapper;

    @Transactional
    public ClientDto save(ClientDto clientDto) {
        Client client = mapper.convertValue(clientDto, Client.class);

        List<Parcel> parcels = client.getParcels().stream()
                .map(parcel -> parcelRepository.findById(parcel.getId())
                        .orElseThrow(() -> new BadRequestException("Parcel not found with id=" +parcel.getId())))
                        .toList();

        client.getParcels().clear();
        parcels.forEach(client::addParcel);

        clientRepository.save(client);
        return mapper.convertValue(client, ClientDto.class);
    }

    @Transactional(readOnly = true)
    public List<ClientDto> list() {
        return clientRepository.findAll().stream()
                .map(client -> mapper.convertValue(client, ClientDto.class))
                .toList();
    }

    @Transactional(readOnly = true)
    public ClientDto findById(Long id) {
        return mapper.convertValue(clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Client with id:" +id +" not found"))
                , ClientDto.class);
    }

    @Transactional
    public void delete(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Client with id:" +id +" not found"));
        for (Parcel parcel : client.getParcels()) {
            parcel.getCampaigns().forEach(parcel::removeCampaign);
        }
        clientRepository.deleteById(client.getId());
    }

    @Transactional
    public ClientDto update(ClientDto clientDto){
        Client client = mapper.convertValue(clientDto, Client.class);
        Client clientBD = clientRepository.findById(client.getId())
                .orElseThrow(() -> new NotFoundException("Client with id:" +client.getId()+" not found"));

        List<Parcel> newParcels = client.getParcels().stream()
                        .map(parcel ->
                            parcelRepository.findById(parcel.getId())
                                    .orElseThrow(() -> new BadRequestException("Parcel not found with id=" +parcel.getId())))
                        .collect(Collectors.toList());
        clientBD.getParcels().clear();
        newParcels.forEach(clientBD::addParcel);
        clientBD.setName(client.getName());
        clientRepository.saveAndFlush(clientBD);
        return mapper.convertValue(clientBD, ClientDto.class);
    }

}
