package com.tr8.pdf.entities.circuit_format;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Table(name = "format_circuit_initial_game")
@JacksonXmlRootElement(localName = "initial-game")
public class FormatCircuitInitialGame {

    @Column(unique = true)
    @Id
    @NotNull
    private String id = UUID.randomUUID().toString();

    @JacksonXmlProperty
    private String position ;

    @JacksonXmlProperty
    private String rank ;

    @JacksonXmlProperty
    private String st ;

    @JacksonXmlProperty
    private String countryTeam1 ;

    @JacksonXmlProperty
    private String team1 ;

    @JacksonXmlProperty
    private String countryTeam2 ;

    @JacksonXmlProperty
    private String team2 ;

    public FormatCircuitInitialGame() {}

    public FormatCircuitInitialGame(String position, String rank, String st, String countryTeam1, String team1, String countryTeam2, String team2) {
        this.position = position;
        this.rank = rank;
        this.st = st;
        this.countryTeam1 = countryTeam1;
        this.team1 = team1;
        this.countryTeam2 = countryTeam2;
        this.team2 = team2;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getSt() {
        return st;
    }

    public void setSt(String st) {
        this.st = st;
    }

    public String getCountryTeam1() {
        return countryTeam1;
    }

    public void setCountryTeam1(String countryTeam1) {
        this.countryTeam1 = countryTeam1;
    }

    public String getTeam1() {
        return team1;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public String getCountryTeam2() {
        return countryTeam2;
    }

    public void setCountryTeam2(String countryTeam2) {
        this.countryTeam2 = countryTeam2;
    }

    public String getTeam2() {
        return team2;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }

    @Override
    public String toString() {
        return "PDFFormatCircuitOneRound{" +
                "id='" + id + '\'' +
                ", position='" + position + '\'' +
                ", rank='" + rank + '\'' +
                ", st='" + st + '\'' +
                ", countryTeam1='" + countryTeam1 + '\'' +
                ", team1='" + team1 + '\'' +
                ", countryTeam2='" + countryTeam2 + '\'' +
                ", team2='" + team2 + '\'' +
                '}';
    }


}
