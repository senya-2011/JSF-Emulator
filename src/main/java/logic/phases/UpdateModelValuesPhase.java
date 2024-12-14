package logic.phases;

import logic.FacesContext;
import logic.Printer;
import ui.ComponentParser;

public class UpdateModelValuesPhase implements Phase{
    @Override
    public void execute(FacesContext facesContext) {
        Printer.globalInfo("Update Model Values Phase");
        Printer.info("В данной фазе вызывается метод UIViewRoot#processUpdates(), после чего для каждого компонента дерева будут вызваны методы UIComponent#processUpdates()");
        facesContext.getUiViewRoot().processUpdate();
        facesContext.getUiViewRoot().showTree();
    }
}
