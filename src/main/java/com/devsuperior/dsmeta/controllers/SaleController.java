package com.devsuperior.dsmeta.controllers;

import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import com.devsuperior.dsmeta.services.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping(value = "/sales")
public class SaleController {

	@Autowired
	private SaleService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<SaleReportDTO> findById(@PathVariable Long id) {
		SaleReportDTO dto = service.findById(id);
		return ResponseEntity.ok(dto);
	}

	@GetMapping(value = "/report")
	public ResponseEntity<Page<SaleReportDTO>> getReport(
		@RequestParam(value = "minDate", defaultValue = "") String minDate,
		@RequestParam(value = "maxDate", defaultValue = "") String maxDate,
		@RequestParam(value = "name", defaultValue = "") String name,
		Pageable pageable) {
		LocalDate dateInit = LocalDate.now(), dateFinal = LocalDate.now();
		if (minDate.isBlank()){
			dateInit = null;
		}else {
			dateInit = LocalDate.parse(minDate);
		}
		if (maxDate.isBlank()){
			dateFinal = null;
		}else{
			dateFinal = LocalDate.parse(maxDate);
		}

		Page<SaleReportDTO> dto = service.findByDate(dateInit, dateFinal, name, pageable);
			return ResponseEntity.ok(dto);
	}

	@GetMapping(value = "/summary")
	public List<SaleSummaryDTO> getSummary(
			@RequestParam(value = "minDate", defaultValue = "") String minDate,
			@RequestParam(value = "maxDate", defaultValue = "") String maxDate,
			@RequestParam(value = "name", defaultValue = "") String name
			){
		    LocalDate dateInit = LocalDate.now(), dateFinal = LocalDate.now();
			if (minDate.isBlank()){
			dateInit = null;
			}else {
			dateInit = LocalDate.parse(minDate);
			}
			if (maxDate.isBlank()){
			dateFinal = null;
			}else {
				dateFinal = LocalDate.parse(maxDate);
			}
			return service.findSummary(dateInit, dateFinal);
	}
}
