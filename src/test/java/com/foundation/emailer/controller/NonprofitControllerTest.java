package com.foundation.emailer.controller;

import com.foundation.emailer.dto.NonprofitDTO;
import com.foundation.emailer.service.NonprofitService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NonprofitControllerTest {

    @Mock
    private NonprofitService nonprofitService;

    @InjectMocks
    private NonprofitController nonprofitController;

    @Test
    @DisplayName("Test creating a new nonprofit")
    void createNonprofit() {
        NonprofitDTO nonprofitDTO = new NonprofitDTO();
        nonprofitDTO.setName("Test Nonprofit");

        when(nonprofitService.createNonprofit(nonprofitDTO)).thenReturn(nonprofitDTO);

        ResponseEntity<NonprofitDTO> responseEntity = nonprofitController.createNonprofit(nonprofitDTO);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(nonprofitDTO, responseEntity.getBody());
    }

    @Test
    @DisplayName("Test updating a nonprofit")
    void updateNonprofit() {
        Long id = 1L;
        NonprofitDTO updatedNonprofitDTO = new NonprofitDTO();
        updatedNonprofitDTO.setName("Updated Nonprofit");

        when(nonprofitService.updateNonprofit(id, updatedNonprofitDTO)).thenReturn(updatedNonprofitDTO);

        ResponseEntity<NonprofitDTO> responseEntity = nonprofitController.updateNonprofit(id, updatedNonprofitDTO);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedNonprofitDTO, responseEntity.getBody());
    }

    @Test
    @DisplayName("Test deleting a nonprofit")
    void deleteNonprofit() {
        Long id = 1L;
        ResponseEntity<Void> responseEntity = nonprofitController.deleteNonprofit(id);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(nonprofitService, times(1)).deleteNonprofit(id);
    }

    @Test
    @DisplayName("Test retrieving a nonprofit by ID")
    void getNonprofitById() {
        Long id = 1L;
        NonprofitDTO nonprofitDTO = new NonprofitDTO();
        nonprofitDTO.setId(id);
        nonprofitDTO.setName("Test Nonprofit");

        when(nonprofitService.getNonprofitById(id)).thenReturn(Optional.of(nonprofitDTO));

        ResponseEntity<NonprofitDTO> responseEntity = nonprofitController.getNonprofitById(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(nonprofitDTO, responseEntity.getBody());
    }

    @Test
    @DisplayName("Test retrieving all nonprofits with pagination and sorting")
    void getAllNonprofits() {
        int page = 0;
        int size = 10;
        String sortBy = "id";
        String sortOrder = "asc";

        ResponseEntity<Page<NonprofitDTO>> responseEntity = nonprofitController.getAllNonprofits(page, size, sortBy, sortOrder);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(nonprofitService, times(1)).getAllNonprofits(page, size, sortBy, sortOrder);
    }
}
