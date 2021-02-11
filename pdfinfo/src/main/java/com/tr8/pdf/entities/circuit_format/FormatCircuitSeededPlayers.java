package com.tr8.pdf.entities.circuit_format;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "format_circuit_seeded_players")
@JacksonXmlRootElement(localName = "seeded-players")
public class FormatCircuitSeededPlayers {

    @Column(unique = true)
    @Id
    @NotNull
    private String id = UUID.randomUUID().toString();

    @JacksonXmlProperty(localName = "teams")
    @JacksonXmlElementWrapper(useWrapping = false)
    @ElementCollection
    private List<String> teams;

    public FormatCircuitSeededPlayers() {
        teams = new LinkedList<>() ;
    }

    public FormatCircuitSeededPlayers(List<String> teams) {
        this.teams = teams;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getTeams() {
        return teams;
    }

    public void setTeams(List<String> teams) {
        this.teams = teams;
    }

    @Override
    public String toString() {
        return "FormatCircuitSeededPlayers{" +
                "id='" + id + '\'' +
                ", teams=" + teams +
                '}';
    }
}
