package com.tr8.pdf.jpa.circuit_format;

import com.tr8.pdf.entities.circuit_format.FormatCircuit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IFormatCircuitJpaRepository extends JpaRepository<FormatCircuit, Integer> {

    public FormatCircuit findByYearAndId(String year, int id) ;

}
