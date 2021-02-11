package com.tr8.pdf.jpa.circuit_format;

import com.tr8.pdf.entities.circuit_format.FormatCircuitGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IFormatCircuitGameJpaRepository extends JpaRepository<FormatCircuitGame, String> {

}
