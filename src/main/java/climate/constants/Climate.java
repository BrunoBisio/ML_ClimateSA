package climate.constants;

public enum Climate {
    PRECIPITATION("lluvia"),
    DRAUGH("sequia"),
    OPTIMUM("optimo");

    private String value;

    private Climate(String value){
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
};