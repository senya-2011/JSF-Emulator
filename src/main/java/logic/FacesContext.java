package logic;

import ui.UIViewRoot;

public class FacesContext {
    private String request;
    private String viewId;
    private String method;
    private boolean needResponse=false;
    private String message="";
    private String response;
    private UIViewRoot uiViewRoot;
    public FacesContext(String request){
        Printer.info("FacesContext получил request: "+request);
        this.request = request;
        setViewId();
        setMethod();
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public UIViewRoot getUiViewRoot() {
        return uiViewRoot;
    }

    public void setUiViewRoot(UIViewRoot uiViewRoot) {
        this.uiViewRoot = uiViewRoot;
    }

    private void setViewId(){
        int semicolonIndex = request.indexOf(";");
        viewId = request.substring(0, semicolonIndex);
        Printer.info("FacesContext: viewId запроса: "+viewId);
    }

    private void setMethod(){
        int firstSemicolonIndex = request.indexOf(";");
        int secondSemicolonIndex = request.indexOf(";", firstSemicolonIndex + 1);
        method = request.substring(firstSemicolonIndex + 1, secondSemicolonIndex);
        Printer.info("FacesContext: method запроса: " + method);
    }

    public void addMessage(String message){
        Printer.info("FacesContext#addMessage : "+ message);
        if(!this.message.equals("")){
            this.message = this.message + "\n"+ message;
        }else{
            this.message = message;
        }
    }

    public String getMessage() {
        return message;
    }

    public void renderResponse(){
        Printer.info("FacesContext: вызов renderResponse()");
        needResponse = true;
    }

    public String getRequestServletPath(){
        return viewId;
    }

    public String getRequestLocale(){
        Printer.info("FacesContext: ExternalContext.html#getRequestLocale()");
        return "ru";
    }

    public boolean isNeedResponse() {
        return needResponse;
    }

    public String getMethod() {
        return method;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }
}
