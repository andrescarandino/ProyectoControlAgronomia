package com.andres.agricultura.v1.service.impl;

import java.util.List;

import com.andres.agricultura.v1.Exceptions.BadRequestException;
import com.andres.agricultura.v1.Exceptions.NotFoundException;
import com.andres.agricultura.v1.dto.ParcelDto;
import com.andres.agricultura.v1.entities.Campaign;
import com.andres.agricultura.v1.repository.CampaignRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andres.agricultura.v1.entities.Parcel;
import com.andres.agricultura.v1.repository.ParcelRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ParcelService {

    @Autowired
    ParcelRepository parcelRepository;
    @Autowired
    CampaignRepository campaignRepository;
    @Autowired
    ObjectMapper mapper;

    @Transactional
    public ParcelDto save( ParcelDto parcelDto) {
        Parcel parcel = mapper.convertValue(parcelDto, Parcel.class);

        List<Campaign> campaigns = parcel.getCampaigns().stream()
                .map(campaign -> campaignRepository.findById(campaign.getId())
                        .orElseThrow(() -> new BadRequestException("Campaing not found with id=" +campaign.getId())))
                        .toList();
        parcel.getCampaigns().clear();
        campaigns.forEach(parcel::addCampaign);

        parcelRepository.save(parcel);
        return mapper.convertValue(parcel, parcelDto.getClass());
    }

    @Transactional(readOnly = true)
    public List<ParcelDto> list() {
        return parcelRepository.findAll().stream()
                .map(parcel -> mapper.convertValue(parcel, ParcelDto.class))
                .toList();
    }

    @Transactional(readOnly = true)
    public ParcelDto findById(Long id) {
        return mapper.convertValue(parcelRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Parcel not found with id=" +id))
                , ParcelDto.class);
    }

    @Transactional
    public void delete(Long id) {
        Parcel parcel = parcelRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Parcel not found with id=" +id));
        parcel.getCampaigns().forEach(parcel::removeCampaign);
        parcelRepository.deleteById(parcel.getId());
    }

    @Transactional
    public ParcelDto update(ParcelDto parcelDto) {

        Parcel parcelBD = parcelRepository.findById(parcelDto.getId())
                        .orElseThrow(() -> new NotFoundException("Parcel not found with id=" +parcelDto.getId()));

        parcelBD.getCampaigns().clear();
        parcelDto.getCampaings().forEach( camp -> {
            Campaign campaign = campaignRepository.findById(camp.getId())
                    .orElseThrow(() -> new BadRequestException("Campaing not found with id=" +camp.getId()));
            parcelBD.addCampaign(campaign);
        });

        parcelBD.setName(parcelDto.getName());
        parcelBD.setSurface(parcelDto.getSurface());
        parcelBD.setUsability(parcelDto.getUsability());
        parcelBD.setProductivityIndex(parcelDto.getProductivityIndex());
        parcelBD.setNomeclature(parcelDto.getNomeclature());
        parcelBD.setLimitation(parcelDto.getLimitation());

        parcelRepository.save(parcelBD);
        return mapper.convertValue(parcelBD, ParcelDto.class);
    }

}
