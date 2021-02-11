package com.tr8.pdf.web;

import com.tr8.pdf.entities.circuit_format.FormatCircuit;
import com.tr8.pdf.jpa.circuit_format.IFormatCircuitJpaRepository;
import com.tr8.pdf.web.dto.Tournament;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/pdf")
public class XMLResource {

    @Autowired
    private IFormatCircuitJpaRepository iFormatCircuitJpaRepository ;


    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/format/circuit", method = RequestMethod.GET)
    @ApiOperation(value= "Get available xml", response= Tournament.class)
    public ResponseEntity<?> get() {
        List<FormatCircuit> circuits = iFormatCircuitJpaRepository.findAll() ;
        List<Tournament> tournaments = new LinkedList<>() ;
        for(FormatCircuit circuit: circuits) {
            tournaments.add(new Tournament(circuit.getYear(), circuit.getId(), circuit.getEvent())) ;
        }
        return ResponseEntity.ok(tournaments) ;
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value="/format/circuit/{year}/{id}", produces= MediaType.APPLICATION_XML_VALUE)
    @ApiOperation(value= "Get XML", produces = MediaType.APPLICATION_ATOM_XML_VALUE)
    public ResponseEntity<?> findCity(@PathVariable String year, @PathVariable int id) {
        System.out.println("OK");
        System.out.println("OK");
        System.out.println("OK");
        FormatCircuit circuit = iFormatCircuitJpaRepository.findByYearAndId(year, id) ;
        return ResponseEntity.ok(circuit) ;
    }


}
