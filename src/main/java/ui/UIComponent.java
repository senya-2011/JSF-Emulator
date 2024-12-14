package ui;

import logic.Binding;
import logic.Model;
import logic.Printer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UIComponent {
    private String value;
    private String id;
    private boolean valid=true;
    private boolean isDate=false;
    private int intValue;
    private Date dateValue;
    private boolean isString=false;
    private final Binding model = new Model();

    public String getValue() {
        return value;
    }

    public String encodeXX() {
        Printer.info("UIComponent id="+id+": encodeXX");
        return "[" + id + "=" + value + "]";
    }

    public void checkValueBinding(){
        Printer.info("UIComponent id="+id+": binding");
        model.setValue(id, value);
        showModel();
    }

    public boolean processValidators(){
        Printer.info("UIComponent id="+id+": processValidators");
        if(value==null){
            validValue();
        }else if(value.equals("invalid")){
            validValue();
        }else{
            valid = true;
            return true;
        }
        return false;
    }

    public void processUpdate(){
        Printer.info("UIComponent id="+id+": processUpdate");
        if(isString){
            model.setValue(id, value);
        }else if(isDate){
            model.setValue(id, dateValue);
        }else{
            model.setValue(id, intValue);
        }

        showModel();
    }

    public boolean processDecodes(String value){
        Printer.info("UIComponent id="+id+": processDecodes");
        if(value==null){
            Printer.info("UIComponent id="+id+": ошибка преобразования!");
        }else if(value.equals("decodeError")){
            Printer.info("UIComponent id="+id+": ошибка преобразования!");
        }else{
            try{
                intValue = Integer.parseInt(value.trim());
                Printer.info("UIComponent id="+id+": преобразован в int");
            }catch (Exception e){
                try {
                    isDate = true;
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
                    dateValue = dateFormat.parse(value);
                    Printer.info("UIComponent id="+id+": преобразован в Date");
                } catch (ParseException ex) {
                    isString = true;
                    Printer.info("UIComponent id="+id+": преобразован в String");
                }
            }
            this.value = value;
            return true;
        }
        return false;
    }

    public void showModel(){
        Printer.info(model.toString());
    }

    private void validValue(){
        Printer.info("UIComponent id="+id+": ошибка валидации!");
    }

    public void processApplication(){
        Printer.info("UIComponent id="+id+": processApplication");

        if(!isDate && !isString){
            Printer.info("UIComponent id="+id+": имитируем нажатие на кнопку +1");
            intValue++;
            value = String.valueOf(intValue);
            model.setValue(id, intValue);
            //надо вообще делать все в модели а потом восстанавливать но это просто эмулятор поэтому норм
        }
        showModel();
    }


    public void setValue(String value) {
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isString() {
        return isString;
    }

    @Override
    public String toString(){
        return String.format("UIComponent: [id: %s, value: %s, valid: %b]", id, value, valid);
    }
}
