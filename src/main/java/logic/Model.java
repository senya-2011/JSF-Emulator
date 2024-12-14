package logic;


public class Model implements Binding{
    private Object value;
    private String id;

    public Object getDate() {
        return value;
    }

    public void setDate(Object value) {
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString(){
        return ("ModelExample [id: "+id+", value: "+value+"]");
    }

    @Override
    public void setValue(String id,Object object) {
        this.id = id;
        value = object;
    }
}
