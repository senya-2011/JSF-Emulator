package logic;

import logic.phases.*;

import java.util.Arrays;
import java.util.List;

public class FacesServlet {

    private FacesContext facesContext;
    private Lifecycle lifecycle = new Lifecycle();
    private List<Phase> phases = Arrays.asList(
            new RestoreViewPhase(),
            new ApplyRequestValuesPhase(),
            new ProcessValidationsPhase(),
            new UpdateModelValuesPhase(),
            new InvokeApplicationPhase(),
            new RenderResponsePhase()
    );


    public String receiveRequest(String request){
        Printer.info("FacesServlet получил request: "+request);
        Printer.info("FacesServlet создает FacesContext и передает request");
        generateNewFacesContext(request);
        Printer.info("FacesServlet вызывает Lifecycle.execute");
        lifecycle.execute(facesContext, phases);
        Printer.info("FacesServlet отправляет ответ клиенту");
        return facesContext.getResponse();
    }

    public void generateNewFacesContext(String request){
        Printer.info("FacesServlet создал новый FacesContext для запроса");
        this.facesContext = new FacesContext(request);
    }
    public FacesContext getFacesContext(){
        return facesContext;
    }

}
