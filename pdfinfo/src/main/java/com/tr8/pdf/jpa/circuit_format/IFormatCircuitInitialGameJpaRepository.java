package com.tr8.pdf.jpa.circuit_format;

import com.tr8.pdf.entities.circuit_format.FormatCircuitInitialGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IFormatCircuitInitialGameJpaRepository extends JpaRepository<FormatCircuitInitialGame, String> {

}
