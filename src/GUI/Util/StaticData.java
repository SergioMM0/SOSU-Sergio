package GUI.Util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class StaticData {

    private static StaticData instance;

    private StaticData(){}

    public static StaticData getInstance(){
        if (instance == null){
            instance = new StaticData();
        }
        return instance;
    }

    public String[] getCategories(){
        return new String[]{"Funktionsniveau","Bevægeapparat","Ernæring","Hud og slimhinder",
        "Kommunikation","Psykosociale forhold", "Respiration og cirkulation","Seksualitet","Smerter og sanseindtryk",
        "Søvn og hvile", "Viden og udvikling","Udskillelse af affaldsstofferx"};
    }

    public String[] getSubcategoriesOf(String category){
        return switch (category) {

            case "Funktionsniveau" -> new String[]{"Problemer med personlig pleje", "Problemer med daglige aktiviteter"};

            case "Bevægeapparat" -> new String[]{"Problemer med mobilitet og bevægelse"};

            case "Ernæring" -> new String[]{"Problemer med væskeindtag", "Problemer med fødeindtag",
                    "Uhensigtsmæssig vægtændring", "Problemer med overvægt", "Problemer med undervægt"};

            case "Hud og slimhinder" -> new String[]{"Problemer med kirurgisk sår", "Problemer med diabetisk sår",
                    "Problemer med cancersår","Problemer med tryksår", "Problemer med arterielt sår",
                    "Problemer med venøst sår", "Problemer med blandingssår","Problemer med traumesår",
                    "Andre problemer med hud og slimhinder"};

            case "Kommunikation" -> new String[]{"Problemer med kommunikation"};

            case "Psykosociale forhold" -> new String[]{"Problemer med socialt samvær",
                    "Emotionelle problemer", "Problemer med misbrug", "Mentale problemer"};

            case "Respiration og cirkulation" -> new String[]{"Respirationsproblemer", "Cirkulationsproblemer"};

            case "Seksualitet" -> new String[]{"Problemer med seksualitet"};

            case "Smerter og sanseindtryk" -> new String[]{"Akutte smerter", "Periodevise smerter",
                    "Kroniske smerter", "Problemer med synssans", "Problemer med lugtesans",
                    "Problemer med hørelse", "Problemer med smagssans", "Problemer med følesans"};

            case "Søvn og hvile" -> new String[]{"Døgnrytmeproblemer", "Søvnproblemer"};

            case "Viden og udvikling" -> new String[]{"Problemer med hukommelse", "Problemer med sygdomsindsigt",
                    "Problemer med indsigt i behandlingsformål", "Kognitive problemer"};

            case "Udskillelse af affaldsstofferx" -> new String[]{"Problemer med vandladning",
                    "Problemer med urininkontinens", "Problemer med afføringsinkontinens", "Problemer med mave og tarm"};

            default -> null;
        };
    }

    public ObservableList<String> getGenders() {
        ObservableList<String> genders = FXCollections.observableArrayList();
        genders.add("Male");
        genders.add("Female");
        genders.add("Lockheed Martin F-16 Fighting Falcon lol");
        return genders;
    }
}
