package ui;

import logic.Printer;

public class UiDom {
    private String dom = "{[a=3], [b=1], [date=15.03.2006]}";
    private String path = "/index.xhtml";

    public UiDom(String dom, String path){
        this.dom=dom;
        this.path=path;
    }
    public UiDom(){}

    public String sendPostRequest(){
        Printer.requestInfo("клиент отправил POST запрос: " + path+";"+"POST"+";"+dom);
        return path+";"+"POST"+";"+dom;
    }

    public String sendGetRequest(){
        Printer.requestInfo("клиент отправил GET запрос: " + path+";"+"GET"+";"+dom);
        return path+";"+"GET"+";"+dom;
    }

    public String getDom(){
        return dom;
    }
    public void setDom(String dom){
        this.dom=dom;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


}
