package logic.phases;

import logic.FacesContext;
import logic.Printer;

public class ProcessValidationsPhase implements Phase{
    @Override
    public void execute(FacesContext facesContext) {
        Printer.globalInfo("Process Validations Phase");
        Printer.info("вызывается метод UIViewRoot#processValidators(), которые для каждого компонента дерева вызывает метод UIComponent#processValidators()");

        if(!facesContext.getUiViewRoot().processValidators()){
            facesContext.addMessage("Ошибка валидации (Process Validate Phase)");
        }
        facesContext.getUiViewRoot().showTree();
    }
}
