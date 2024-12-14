package logic.phases;

import logic.FacesContext;
import logic.Printer;

public class InvokeApplicationPhase implements Phase{
    @Override
    public void execute(FacesContext facesContext) {
        Printer.globalInfo("Invoke Application Phase");
        Printer.info("Все обновления модели выполнены и оставшиеся события передаются для обработки приложению.");
        facesContext.getUiViewRoot().processApplication();
        facesContext.getUiViewRoot().showTree();
    }
}
