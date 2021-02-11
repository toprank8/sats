package com.tr8.pdf.entities.circuit_format;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "format_circuit")
@JacksonXmlRootElement(localName = "tournament")
public class FormatCircuit implements Serializable {

    @Column(unique = true)
    @Id
    @NotNull
    @JacksonXmlProperty(isAttribute = true)
    private int id ;

    @Enumerated
    @JacksonXmlProperty
    private EventType eventType ;

    @JacksonXmlProperty
    private String year ;

    @JacksonXmlProperty
    private String event ;

    @JacksonXmlProperty
    private String category ;

    @JacksonXmlProperty
    private String weekOf ;

    @JacksonXmlProperty
    private String city ;

    @JacksonXmlProperty
    private String prizeType;

    @JacksonXmlProperty
    private String money;

    @JacksonXmlProperty
    private String tourKey;

    @JacksonXmlProperty
    private String supervisor;

    @JacksonXmlProperty
    private String dateTime;

    @JacksonXmlProperty(localName = "round1")
    @JacksonXmlElementWrapper(useWrapping = false)
    @OneToMany(fetch = FetchType.LAZY)
    private List<FormatCircuitInitialGame> initialGames ;

    @JacksonXmlProperty(localName = "quarterfinals")
    @JacksonXmlElementWrapper(useWrapping = false)
    @OneToMany(fetch = FetchType.LAZY)
    private List<FormatCircuitGame> quarterfinals ;

    @JacksonXmlProperty(localName = "semifinals")
    @JacksonXmlElementWrapper(useWrapping = false)
    @OneToMany(fetch = FetchType.LAZY)
    private List<FormatCircuitGame> semifinals ;

    @JacksonXmlProperty(localName = "finals")
    @JacksonXmlElementWrapper(useWrapping = false)
    @OneToMany(fetch = FetchType.LAZY)
    private List<FormatCircuitGame> finals ;

    @JacksonXmlProperty(localName = "seeded-players")
    @JacksonXmlElementWrapper(useWrapping = false)
    @OneToOne
    private FormatCircuitSeededPlayers seededPlayers ;

    public FormatCircuit() {
        this.initialGames = new LinkedList<>() ;
        this.quarterfinals = new LinkedList<>() ;
        this.semifinals = new LinkedList<>() ;
        this.finals = new LinkedList<>() ;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getWeekOf() {
        return weekOf;
    }

    public void setWeekOf(String weekOf) {
        this.weekOf = weekOf;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPrizeType() {
        return prizeType;
    }

    public void setPrizeType(String prizeType) {
        this.prizeType = prizeType;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getTourKey() {
        return tourKey;
    }

    public void setTourKey(String tourKey) {
        this.tourKey = tourKey;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public List<FormatCircuitInitialGame> getInitialGames() {
        return initialGames;
    }

    public void setInitialGames(List<FormatCircuitInitialGame> initialGames) {
        this.initialGames = initialGames;
    }

    public List<FormatCircuitGame> getQuarterfinals() {
        return quarterfinals;
    }

    public void setQuarterfinals(List<FormatCircuitGame> quarterfinals) {
        this.quarterfinals = quarterfinals;
    }

    public List<FormatCircuitGame> getSemifinals() {
        return semifinals;
    }

    public void setSemifinals(List<FormatCircuitGame> semifinals) {
        this.semifinals = semifinals;
    }

    public List<FormatCircuitGame> getFinals() {
        return finals;
    }

    public void setFinals(List<FormatCircuitGame> finals) {
        this.finals = finals;
    }

    public FormatCircuitSeededPlayers getSeededPlayers() {
        return seededPlayers;
    }

    public void setSeededPlayers(FormatCircuitSeededPlayers seededPlayers) {
        this.seededPlayers = seededPlayers;
    }

}
