package com.tr8.pdf.service;

import com.tr8.pdf.entities.circuit_format.*;
import com.tr8.pdf.jpa.circuit_format.IFormatCircuitGameJpaRepository;
import com.tr8.pdf.jpa.circuit_format.IFormatCircuitJpaRepository;
import com.tr8.pdf.jpa.circuit_format.IFormatCircuitInitialGameJpaRepository;
import com.tr8.pdf.jpa.circuit_format.IFormatCircuitSeededPlayersJpaRepository;
import com.tr8.pdf.serviceContrats.IPDFFormatCircuitProcessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Service
public class PDFFormatCircuitProcessorService implements IPDFFormatCircuitProcessorService {

    @Autowired
    private IFormatCircuitJpaRepository iFormatCircuitJpaRepository;

    @Autowired
    private IFormatCircuitInitialGameJpaRepository iFormatCircuitInitialGameJpaRepository;

    @Autowired
    private IFormatCircuitGameJpaRepository iFormatCircuitGameJpaRepository;

    @Autowired
    private IFormatCircuitSeededPlayersJpaRepository iFormatCircuitSeededPlayersJpaRepository;


    public void add(FormatCircuit formatCircuit) {
        List<FormatCircuitInitialGame> initialGames = new LinkedList<>() ;
        for(FormatCircuitInitialGame initialGame : formatCircuit.getInitialGames()) {
            FormatCircuitInitialGame ig = new FormatCircuitInitialGame(initialGame.getPosition(), initialGame.getRank(), initialGame.getSt(), initialGame.getCountryTeam1(), initialGame.getTeam1(), initialGame.getCountryTeam2(), initialGame.getTeam2()) ;
            initialGames.add(iFormatCircuitInitialGameJpaRepository.save(ig));
        }
        formatCircuit.setInitialGames(initialGames);

        List<FormatCircuitGame> quarterfinals = new LinkedList<>() ;
        for(FormatCircuitGame game: formatCircuit.getQuarterfinals()) {
            FormatCircuitGame g = new FormatCircuitGame(game.getTeam1(), game.getTeam2(), game.getScore()) ;
            quarterfinals.add(iFormatCircuitGameJpaRepository.save(g));
        }
        formatCircuit.setQuarterfinals(quarterfinals);

        List<FormatCircuitGame> semifinals = new LinkedList<>() ;
        for(FormatCircuitGame game: formatCircuit.getSemifinals()) {
            FormatCircuitGame g = new FormatCircuitGame(game.getTeam1(), game.getTeam2(), game.getScore()) ;
            semifinals.add(iFormatCircuitGameJpaRepository.save(g));
        }
        formatCircuit.setSemifinals(semifinals);

        List<FormatCircuitGame> finals = new LinkedList<>() ;
        for(FormatCircuitGame game: formatCircuit.getFinals()) {
            FormatCircuitGame g = new FormatCircuitGame(game.getTeam1(), game.getTeam2(), game.getScore()) ;
            finals.add(iFormatCircuitGameJpaRepository.save(g));
        }
        formatCircuit.setFinals(finals);
        formatCircuit.setSeededPlayers(iFormatCircuitSeededPlayersJpaRepository.save(formatCircuit.getSeededPlayers()));
        iFormatCircuitJpaRepository.save(formatCircuit);
    }


    @Override
    public void load(String xmlPath, String year, String id) throws ParserConfigurationException, IOException, SAXException {
        FormatCircuit formatCircuit = iFormatCircuitJpaRepository.findByYearAndId(year, Integer.parseInt(id)) ;
        if(formatCircuit == null){
            formatCircuit = new FormatCircuit() ;
            formatCircuit.setId(Integer.parseInt(id));
            formatCircuit.setYear(year);
            formatCircuit.setEventType(EventType.MDD);
        }
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document document = dBuilder.parse(xmlPath);
        document.getDocumentElement().normalize();
        generate(document,formatCircuit);
    }


    private void generate(Document document, FormatCircuit formatCircuit)  {
        NodeList texts = document.getElementsByTagName("text");
        if(texts.item(1).getTextContent().contains("Circuit")) {
            /**
             * Event
             */
            formatCircuit.setEvent(texts.item(0).getTextContent().trim());
            /**
             * Category
             */
            formatCircuit.setCategory(texts.item(1).getTextContent().trim());
            for (int i = 0; i < texts.getLength(); i++) {
                Node text = texts.item(i);
                String content = text.getTextContent() ;
                if(content.equals("Week of")) {
                    /**
                     * WeekOf
                     */
                    formatCircuit.setWeekOf(texts.item(i + 1).getTextContent().trim());
                }
                if(content.equals("City,Country")) {
                    /**
                     * City,Country
                     */
                    formatCircuit.setCity(texts.item(i + 1).getTextContent().trim());
                }
                if(content.contains("Prize Money")) {
                    /**
                     * Prize Money
                     */
                    formatCircuit.setPrizeType(texts.item(i).getTextContent().trim());
                    formatCircuit.setMoney(texts.item(i + 1).getTextContent().trim());
                }
                if(content.contains("Tourn. Key")){
                    /**
                     * Tourn. Key
                     */
                    formatCircuit.setTourKey(texts.item(i+1).getTextContent().trim());
                }
                if(content.contains("Supervisor")) {
                    /**
                     * Supervisor
                     */
                    formatCircuit.setSupervisor(texts.item(i+1).getTextContent().trim());
                }
                if(content.contains("date/time")){
                    /**
                     * DateTime
                     */
                    String datetime = texts.item(i).getTextContent().trim() ;
                    String[] parts = datetime.split(":");
                    if(parts.length == 2 && !parts[1].trim().equals("")){
                        formatCircuit.setDateTime(parts[1].trim()) ;
                    } else {
                        formatCircuit.setDateTime(texts.item(i+1).getTextContent().trim());
                    }
                }

                if(content.contains("Round 1")){
                    processingRound1(texts, i, formatCircuit);
                }
                if(content.contains("Quarterfinals")){
                    processingQuarterfinals(texts, i, formatCircuit);
                }
                if(content.contains("Semifinals")){
                    processingSemifinals(texts, i, formatCircuit);
                }
                if(content.contains("Final")){
                    processingFinal(texts, i, formatCircuit);
                }
                if(content.contains("Seeded Players")){
                    processingSeededPlayers(texts, i, formatCircuit);
                }
            }
            this.add(formatCircuit);
        }

    }

    private void processingRound1(NodeList texts, int positionRound1, FormatCircuit formatCircuit){
        List<FormatCircuitInitialGame> initialGames = new LinkedList<>() ;
        positionRound1 ++ ;
        for (int i = positionRound1; i < texts.getLength(); i++) {
            Node text = texts.item(i);
            String content = text.getTextContent().trim() ;
            if(content.contains("Quarterfinals")){
                break;
            } else {
                if(content.equals("1") ||
                        content.equals("2") ||
                        content.equals("3") ||
                        content.equals("4") ||
                        content.equals("5") ||
                        content.equals("6") ||
                        content.equals("7") ||
                        content.equals("8") ||
                        content.equals("9") ||
                        content.equals("10") ||
                        content.equals("11") ||
                        content.equals("12") ||
                        content.equals("13") ||
                        content.equals("14") ||
                        content.equals("15") ||
                        content.equals("16") ||
                        content.equals("17") ||
                        content.equals("18") ||
                        content.equals("19") ||
                        content.equals("20") ||
                        content.equals("21") ||
                        content.equals("22") ||
                        content.equals("23") ||
                        content.equals("24") ||
                        content.equals("25")
                ) {
                    int index = Integer.parseInt(content);
                    boolean containRank ;
                    try {
                        int nextIndex1 = Integer.parseInt(texts.item(i+5).getTextContent().trim())  ;
                        if(index == nextIndex1-1){
                            containRank = true;
                        } else {
                            containRank = false ;
                        }
                    } catch (NumberFormatException e) {
                        if(texts.item(i+5).getTextContent().equals("Quarterfinals")){
                            containRank = true;
                        } else {
                            containRank = false;
                        }
                    }
                    if(containRank) {
                        String position = texts.item(i).getTextContent().trim() ;
                        String countryTeam1 = texts.item(i + 3).getTextContent().trim().substring(0, 3) ;
                        String team1 = texts.item(i + 3).getTextContent().trim().substring(3).trim() ;
                        String countryTeam2 = texts.item(i + 2).getTextContent().trim() ;
                        String team2 = texts.item(i + 4).getTextContent().trim() ;

                        String rank = texts.item(i + 1).getTextContent().trim() ;
                        String st = "" ;
                        try {
                            Integer.parseInt(rank)  ;
                        } catch (NumberFormatException e) {
                           st = rank ;
                           rank = "" ;
                        }
                        initialGames.add(new FormatCircuitInitialGame(position, rank, st, countryTeam1, team1, countryTeam2, team2)) ;
                    } else {
                        String position = texts.item(i).getTextContent().trim() ;
                        String countryTeam1 =  texts.item(i + 2).getTextContent().trim().substring(0, 3) ;
                        String team1 = texts.item(i + 2).getTextContent().trim().substring(3).trim() ;
                        String countryTeam2 = texts.item(i + 1).getTextContent().trim() ;
                        String team2 = texts.item(i + 3).getTextContent().trim() ;
                        initialGames.add(new FormatCircuitInitialGame(position, "", "", countryTeam1, team1, countryTeam2, team2)) ;
                    }
                }
            }
        }
        formatCircuit.setInitialGames(initialGames);
    }

    private void processingQuarterfinals(NodeList texts, int positionRound1, FormatCircuit formatCircuit){
        List<FormatCircuitGame> games = new LinkedList<>() ;
        positionRound1 ++ ;
        for (int i = positionRound1; i < texts.getLength(); i+=3) {
            Node text = texts.item(i);
            String content = text.getTextContent().trim() ;
            if(content.contains("Semifinals")){
                break;
            } else {
                games.add(new FormatCircuitGame(texts.item(i).getTextContent().trim(),texts.item(i + 1).getTextContent().trim(),texts.item(i + 2).getTextContent().trim())) ;
            }
        }
        formatCircuit.setQuarterfinals(games);
    }

    private void processingSemifinals(NodeList texts, int positionRound1, FormatCircuit formatCircuit){
        List<FormatCircuitGame> games = new LinkedList<>() ;
        positionRound1 ++ ;
        for (int i = positionRound1; i < texts.getLength(); i+=3) {
            Node text = texts.item(i);
            String content = text.getTextContent().trim() ;
            if(content.contains("Final")){
                break;
            } else {
                games.add(new FormatCircuitGame(texts.item(i).getTextContent().trim(),texts.item(i + 1).getTextContent().trim(),texts.item(i + 2).getTextContent().trim())) ;
            }
        }
        formatCircuit.setSemifinals(games);
    }

    private void processingFinal(NodeList texts, int positionRound1, FormatCircuit formatCircuit){
        List<FormatCircuitGame> games = new LinkedList<>() ;
        positionRound1 ++ ;
        int count = 0 ;
        for (int i = positionRound1; i < texts.getLength(); i+=3) {
            if(count==3){
                break;
            } else {
                games.add(new FormatCircuitGame(texts.item(i).getTextContent().trim(),texts.item(i + 1).getTextContent().trim(),texts.item(i + 2).getTextContent().trim())) ;
                count++;
            }
        }
        formatCircuit.setFinals(games);
    }

    private void processingSeededPlayers(NodeList texts, int positionRound1, FormatCircuit formatCircuit){
        List<String> teams = new LinkedList<>() ;
        positionRound1 ++ ;
        for (int i = positionRound1; i < texts.getLength(); i++) {
            String content = texts.item(i).getTextContent().trim();
            if(content.contains("Alternates")){
                break;
            } else {
                teams.add(texts.item(i).getTextContent().trim());
            }
        }
        formatCircuit.setSeededPlayers(new FormatCircuitSeededPlayers(teams));
    }







}
