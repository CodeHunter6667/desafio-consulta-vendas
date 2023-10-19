package com.devsuperior.dsmeta.repositories;

import java.time.LocalDate;
import java.util.List;

import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.dsmeta.entities.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("SELECT obj FROM Sale obj WHERE " +
            "obj.date BETWEEN :dateInit AND :dateFinal " +
            "AND UPPER (obj.seller.name) LIKE UPPER(CONCAT('%', :name, '%'))")
    Page<Sale> searchReportPerDate(LocalDate dateInit, LocalDate dateFinal, String name, Pageable pageable);

    @Query("SELECT NEW com.devsuperior.dsmeta.dto.SaleSummaryDTO(obj.seller.name, SUM(obj.amount)) FROM Sale obj WHERE obj.date BETWEEN :dateInit AND :dateFinal GROUP BY obj.seller.name")
    List<SaleSummaryDTO> searchSummary(LocalDate dateInit, LocalDate dateFinal);
}
