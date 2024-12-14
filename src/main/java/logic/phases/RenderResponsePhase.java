package logic.phases;

import logic.FacesContext;
import logic.Printer;
import logic.ViewHandler;

public class RenderResponsePhase implements Phase{
    private ViewHandler viewHandler = ViewHandler.getInstance();
    @Override
    public void execute(FacesContext facesContext) {
        Printer.globalInfo("Render Response Phase");
        Printer.info("Render Response - это завершающая фаза жизненного цикла JSF, в которой формируется ответ. ");
        Printer.info("Сохранили UIViewInfo для следующих запросов");
        viewHandler.saveView(facesContext, facesContext.getRequestServletPath());

        String newDom = facesContext.getUiViewRoot().encodeXX();

//        if(!facesContext.getMessage().isEmpty()){
//            Printer.info("\n<messages>\n " + facesContext.getMessage() + "\n</messages>");
//        }

        facesContext.setResponse(newDom + ";"+"\n<messages>\n " + facesContext.getMessage() + "\n</messages>");
        Printer.info("Ответ клиенту: \n"+newDom + ";"+"\n<messages>\n " + facesContext.getMessage() + "\n</messages>");
        Printer.info("Итоговые значения");
        Printer.info("---------------------------------------------------------------------------------------------------------------");
        facesContext.getUiViewRoot().showTree();
        facesContext.getUiViewRoot().showModel();
        Printer.info("---------------------------------------------------------------------------------------------------------------");

    }
}
