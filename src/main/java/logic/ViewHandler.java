package logic;

import ui.ComponentParser;
import ui.UIViewRoot;

import java.util.HashMap;
import java.util.Map;

//по факту работает как StateManager
public class ViewHandler {

    private static ViewHandler viewHandler;

    private Map<String, UIViewRoot> viewRootMap = new HashMap<>();

    private ViewHandler(){}
    public static ViewHandler getInstance(){
        if(viewHandler==null){
            viewHandler=new ViewHandler();
        }
        return viewHandler;
    }

    public void saveView(FacesContext facesContext,String viewId){
        Printer.info("ViewHandler: сохраняем view: "+viewId);
        viewRootMap.put(viewId, facesContext.getUiViewRoot());
    }
    public UIViewRoot createView(FacesContext facesContext ,String viewId){
        Printer.info("ViewHandler: создаем новый UIViewRoot для viewId: "+  viewId);
        UIViewRoot newViewRoot = new UIViewRoot();
        newViewRoot.makeTree(ComponentParser.parseComponents(facesContext));
        String locale = facesContext.getRequestLocale();
        newViewRoot.setLocale(locale);
        newViewRoot.setViewId(viewId);
        viewRootMap.put(viewId, newViewRoot);
        return newViewRoot;
    }

    public UIViewRoot restoreView(FacesContext facesContext,String viewId){
        Printer.info("ViewHandler: вызов restoreView для viewId: "+viewId);
        Printer.info("ViewHandler: мапа всех viewId: " + viewRootMap.toString());
        return viewRootMap.get(viewId);
    }
}
