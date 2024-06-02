package com.foundation.emailer.service;

import com.foundation.emailer.dto.NonprofitDTO;
import com.foundation.emailer.model.Nonprofit;
import com.foundation.emailer.repository.NonprofitRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NonprofitService {
    private final NonprofitRepository nonprofitRepository;
    private final ModelMapper modelMapper;

    public NonprofitDTO createNonprofit(NonprofitDTO nonprofitDTO) {
        nonprofitDTO.setId(null);
        Nonprofit nonprofit = dtoToEntity(nonprofitDTO);
        Nonprofit createdNonprofit = nonprofitRepository.save(nonprofit);
        return entityToDTO(createdNonprofit);
    }

    public NonprofitDTO updateNonprofit(Long id, @NonNull NonprofitDTO updatedNonprofitDTO) {
        Nonprofit existingNonprofit = nonprofitRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Nonprofit not found with id: " + id));

        existingNonprofit.setName(updatedNonprofitDTO.getName());
        existingNonprofit.setAddress(updatedNonprofitDTO.getAddress());
        existingNonprofit.setEmail(updatedNonprofitDTO.getEmail());

        Nonprofit savedNonprofit = nonprofitRepository.save(existingNonprofit);
        return entityToDTO(savedNonprofit);
    }

    public void deleteNonprofit(Long id) {
        nonprofitRepository.deleteById(id);
    }

    public Optional<NonprofitDTO> getNonprofitById(Long id) {
        return nonprofitRepository.findById(id)
            .map(this::entityToDTO);
    }

    public Page<NonprofitDTO> getAllNonprofits(int page, int size, String sortBy, String sortOrder) {
        Sort sort = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return nonprofitRepository.findAll(pageable)
            .map(this::entityToDTO);
    }

    private NonprofitDTO entityToDTO(Nonprofit nonprofit) {
        return modelMapper.map(nonprofit, NonprofitDTO.class);
    }

    private Nonprofit dtoToEntity(NonprofitDTO nonprofitDTO) {
        return modelMapper.map(nonprofitDTO, Nonprofit.class);
    }
}
