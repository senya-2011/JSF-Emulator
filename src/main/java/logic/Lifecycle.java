package logic;

import logic.phases.*;

import java.util.Arrays;
import java.util.List;

public class Lifecycle {
    private FacesContext facesContext;

    public void execute(FacesContext facesContext, List<Phase> phases){
        Printer.info("Lifecycle принял facesContext");
        Printer.info("Lifecycle начинает вызов Phases");
        this.facesContext = facesContext;
        for (Phase phase : phases) {
            if(!facesContext.isNeedResponse()){
                phase.execute(facesContext);
            }
        }
        if(facesContext.isNeedResponse()){
            Phase responsePhase = phases.get(phases.size() - 1);
            responsePhase.execute(facesContext);
        }
    }
}
