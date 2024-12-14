package logic.phases;

import logic.FacesContext;
import logic.Printer;
import logic.ViewHandler;
import ui.UIViewRoot;

public class RestoreViewPhase implements Phase{
    private ViewHandler viewHandler = ViewHandler.getInstance();
    @Override
    public void execute(FacesContext facesContext) {
        Printer.globalInfo("Restore View Phase");
        UIViewRoot viewRoot = viewHandler.restoreView(facesContext, facesContext.getRequestServletPath());
        if(viewRoot!=null){
            Printer.info("viewRoot был найден => это не первый запрос");
            String locale = facesContext.getRequestLocale();
            viewRoot.setLocale(locale);
            Printer.info("дальше идет биндинг всех компонетов");
            viewRoot.checkValueBinding();
            if(facesContext.getMethod().equals("GET")){
                Printer.info("Метод GET => сразу на Render Response");
                facesContext.renderResponse();
            }
        }else{
            Printer.info("viewRoot не был найден => первый запрос");
            viewRoot = viewHandler.createView(facesContext, facesContext.getRequestServletPath());
            Printer.info("тк это первый запрос переходим сразу на Render Response");
            facesContext.renderResponse();
        }

        facesContext.setUiViewRoot(viewRoot);
    }
}
