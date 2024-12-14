package logic.phases;

import logic.FacesContext;
import logic.Printer;
import ui.ComponentParser;

public class ApplyRequestValuesPhase implements Phase{
    @Override
    public void execute(FacesContext facesContext) {
        Printer.globalInfo("Apply Request Values Phase");
        Printer.info("каждый компонент обновляет своё состояние на основании информации текущего запроса");
        if(!facesContext.getUiViewRoot().processDecodes(ComponentParser.parseComponents(facesContext))){
            facesContext.addMessage("Ошибка преобразования (ApplyRequestPhase)");
        }
        facesContext.getUiViewRoot().showTree();
    }
}
