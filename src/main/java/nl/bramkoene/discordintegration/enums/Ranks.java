package nl.bramkoene.discordintegration.enums;

public enum Ranks {
    RECTOR("rector"),
    TEAMLEIDER("teamleider"),
    DOCENT("docent"),
    STAGAIR("stagair"),
    VRIJWILLIGER("vrijwilliger"),
    KMCER("kmcer");

    public final String label;

    private Ranks(String label){
        this.label = label;
    }
}
