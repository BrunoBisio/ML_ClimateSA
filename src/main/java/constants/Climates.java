package constants;

public enum Climates {
    PRECIPITATION("PRECIPITATION"),
    DRAUGH("DRAUGH"),
    OPTIMUM("OPTIMUM");

    private String value;

    private Climates(String value){
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}