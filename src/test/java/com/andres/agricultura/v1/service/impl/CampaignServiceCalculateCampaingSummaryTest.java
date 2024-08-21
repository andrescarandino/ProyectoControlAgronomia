package com.andres.agricultura.v1.service.impl;

import com.andres.agricultura.v1.Exceptions.BadRequestException;
import com.andres.agricultura.v1.Exceptions.NotFoundException;
import com.andres.agricultura.v1.entities.*;
import com.andres.agricultura.v1.repository.CampaignRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CampaignServiceCalculateCampaingSummaryTest {
    @Mock
    private CampaignRepository campaignRepository;

    @InjectMocks
    private CampaignService campaignService;

    private Campaign campaign;
    private Work work;
    private Fertilizer fertilizer;
    private Application application;
    private Supply supply;
    private Harvest harvest;
    private Product product;

    @BeforeEach
    void setUp() {
        // Inicializa tus objetos de prueba aquí
        work = new Work();
        work.setName("Work1");
        work.setDate(LocalDate.parse("2023-07-23"));
        work.setPriceHectare(149.9);

        fertilizer = new Fertilizer();
        fertilizer.setName("Fertilizer1");
        fertilizer.setDate(LocalDate.parse("2023-07-23"));
        fertilizer.setPriceHectare(100.0);

        application = new Application();
        application.setActivity("Activity1");
        application.setDate(LocalDate.parse("2023-07-23"));

        product = new Product();
        product.setId(1L);
        product.setPriceHectare(100.0);
        application.setProducts(Set.of(product));
        application.calculateTotalPriceProducts();

        supply = new Supply();
        supply.setName("Supply1");
        supply.setPriceHectare(18.12);

        harvest = new Harvest();
        harvest.setPrice(39.0);
        harvest.setCoastComercialization(0.17); // porcentaje
        harvest.setPerformance(28.0);

        campaign = new Campaign();
        campaign.setId(1L);
        campaign.setWorks(Set.of(work));
        campaign.setFertilizers(Set.of(fertilizer));
        campaign.setApplications(Set.of(application));
        campaign.setSupplies(Set.of(supply));
        campaign.setHarvest(harvest);
    }

    @Test
    void testCalculateSummary_Success() {

        when(campaignRepository.findById(1L)).thenReturn(Optional.of(campaign));


        Map<String, Object> result = campaignService.calculateCampaignSummary(1L);

        assertEquals(campaign, result.get("campaign"));
        assertEquals(149.90, ((AtomicReference<Double>)result.get("worksTotal")).get());
        assertEquals(218.12, ((AtomicReference<Double>)result.get("suppliesTotal")).get());
        assertEquals(32.37, result.get("netPrice"));
        assertEquals(368.02, result.get("directCosts"));
        assertEquals(906.3599999999999, result.get("grossIncome"));
        assertEquals(538.3399999999999, result.get("mb"));
        assertEquals(11.369168983626816, result.get("performanceIndifference"));
        assertEquals(1.4628009347318078, result.get("mb/cd"));

        // Verifica que los métodos correctos se llamaron
        verify(campaignRepository, times(1)).findById(1L);
    }

    @Test
    void testCalculateSummary_CampaignNotFound() {
        when(campaignRepository.findById(1L)).thenReturn(Optional.empty());

        // Verifica que se lanza la excepción
        assertThrows(NotFoundException.class, () -> {
            campaignService.calculateCampaignSummary(1L);
        });

        verify(campaignRepository, times(1)).findById(1L);
    }

    @Test
    void testCalculateSummary_NullPointerException() {
        campaign.setHarvest(null);


        when(campaignRepository.findById(1L)).thenReturn(Optional.of(campaign));


        assertThrows(BadRequestException.class, () -> {
            campaignService.calculateCampaignSummary(1L);
        });


        verify(campaignRepository, times(1)).findById(1L);
    }


}