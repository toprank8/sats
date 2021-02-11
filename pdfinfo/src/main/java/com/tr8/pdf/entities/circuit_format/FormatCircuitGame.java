package com.tr8.pdf.entities.circuit_format;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;


@Entity
@Table(name = "format_circuit_game")
@JacksonXmlRootElement(localName = "game")
public class FormatCircuitGame {

    @Column(unique = true)
    @Id
    @NotNull
    private String id = UUID.randomUUID().toString();

    @JacksonXmlProperty
    private String team1 ;

    @JacksonXmlProperty
    private String team2;

    @JacksonXmlProperty
    private String score ;

    public FormatCircuitGame() {
    }

    public FormatCircuitGame(String team1, String team2, String score) {
        this.team1 = team1;
        this.team2 = team2;
        this.score = score;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeam1() {
        return team1;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public String getTeam2() {
        return team2;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "FormatCircuitGame{" +
                "id='" + id + '\'' +
                ", team1='" + team1 + '\'' +
                ", team2='" + team2 + '\'' +
                ", score='" + score + '\'' +
                '}';
    }
}
