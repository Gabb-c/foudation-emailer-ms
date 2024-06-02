package com.foundation.emailer.service;

import com.foundation.emailer.dto.NonprofitDTO;
import com.foundation.emailer.model.Nonprofit;
import com.foundation.emailer.repository.NonprofitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NonprofitServiceTest {

    @Mock
    private NonprofitRepository nonprofitRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private NonprofitService nonprofitService;

    private Nonprofit nonprofit;
    private NonprofitDTO nonprofitDTO;

    @BeforeEach
    void setUp() {
        nonprofit = new Nonprofit();
        nonprofit.setId(1L);
        nonprofit.setName("Test Nonprofit");
        nonprofit.setAddress("123 Test St");
        nonprofit.setEmail("test@example.com");

        nonprofitDTO = new NonprofitDTO();
        nonprofitDTO.setId(1L);
        nonprofitDTO.setName("Test Nonprofit");
        nonprofitDTO.setAddress("123 Test St");
        nonprofitDTO.setEmail("test@example.com");
    }

    @Test
    @DisplayName("Test creating a new Nonprofit")
    void createNonprofit() {
        when(modelMapper.map(any(NonprofitDTO.class), eq(Nonprofit.class))).thenReturn(nonprofit);
        when(nonprofitRepository.save(any(Nonprofit.class))).thenReturn(nonprofit);
        when(modelMapper.map(any(Nonprofit.class), eq(NonprofitDTO.class))).thenReturn(nonprofitDTO);

        NonprofitDTO createdNonprofit = nonprofitService.createNonprofit(nonprofitDTO);

        assertNotNull(createdNonprofit);
        assertEquals("Test Nonprofit", createdNonprofit.getName());
        verify(nonprofitRepository, times(1)).save(any(Nonprofit.class));
    }

    @Test
    @DisplayName("Test updating an existing Nonprofit")
    void updateNonprofit() {
        when(nonprofitRepository.findById(1L)).thenReturn(Optional.of(nonprofit));
        when(nonprofitRepository.save(any(Nonprofit.class))).thenReturn(nonprofit);
        when(modelMapper.map(any(Nonprofit.class), eq(NonprofitDTO.class))).thenReturn(nonprofitDTO);

        NonprofitDTO updatedNonprofit = nonprofitService.updateNonprofit(1L, nonprofitDTO);

        assertNotNull(updatedNonprofit);
        assertEquals("Test Nonprofit", updatedNonprofit.getName());
        verify(nonprofitRepository, times(1)).findById(1L);
        verify(nonprofitRepository, times(1)).save(any(Nonprofit.class));
    }

    @Test
    @DisplayName("Test deleting a Nonprofit")
    void deleteNonprofit() {
        doNothing().when(nonprofitRepository).deleteById(1L);

        nonprofitService.deleteNonprofit(1L);

        verify(nonprofitRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Test retrieving a Nonprofit by ID")
    void getNonprofitById() {
        when(nonprofitRepository.findById(1L)).thenReturn(Optional.of(nonprofit));
        when(modelMapper.map(any(Nonprofit.class), eq(NonprofitDTO.class))).thenReturn(nonprofitDTO);

        Optional<NonprofitDTO> foundNonprofit = nonprofitService.getNonprofitById(1L);

        assertTrue(foundNonprofit.isPresent());
        assertEquals("Test Nonprofit", foundNonprofit.get().getName());
        verify(nonprofitRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Test retrieving all Nonprofits with pagination and sorting")
    void getAllNonprofits() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "id"));
        Page<Nonprofit> nonprofitPage = new PageImpl<>(Collections.singletonList(nonprofit), pageable, 1);

        when(nonprofitRepository.findAll(pageable)).thenReturn(nonprofitPage);
        when(modelMapper.map(any(Nonprofit.class), eq(NonprofitDTO.class))).thenReturn(nonprofitDTO);

        Page<NonprofitDTO> nonprofitDTOPage = nonprofitService.getAllNonprofits(0, 10, "id", "asc");

        assertNotNull(nonprofitDTOPage);
        assertEquals(1, nonprofitDTOPage.getTotalElements());
        assertEquals("Test Nonprofit", nonprofitDTOPage.getContent().get(0).getName());
        verify(nonprofitRepository, times(1)).findAll(pageable);
    }

}
