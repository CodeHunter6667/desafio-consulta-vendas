package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	public SaleReportDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleReportDTO(entity);
	}

	public Page<SaleReportDTO> findByDate(LocalDate dateInit, LocalDate dateFinal, String name, Pageable pageable ) {
		LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		if (dateInit == null){
			LocalDate temp = today;
			temp = temp.minusYears(1L);
			dateInit = temp;
		}
		if (dateFinal == null){
			dateFinal = today;
		}
		Page<Sale> result = repository.searchReportPerDate(dateInit, dateFinal, name, pageable);
		return result.map(x -> new SaleReportDTO(x));
	}

	public List<SaleSummaryDTO> findSummary(LocalDate dateInit, LocalDate dateFinal){
		LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		if (dateInit == null){
			LocalDate temp = today;
			temp = temp.minusYears(1L);
			dateInit = temp;
		}
		if (dateFinal == null){
			dateFinal = today;
		}
		List<SaleSummaryDTO> result = repository.searchSummary(dateInit, dateFinal);
		return result.stream().map(x -> new SaleSummaryDTO(x.getName(), x.getTotal())).toList();
	}
}
