package calculator.domain;

public class DisplayValue {

    private String value;

    protected DisplayValue(String value){

        this.value = value;

    }

    public String getValue(){

        return value;

    }
}
