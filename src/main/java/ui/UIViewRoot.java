package ui;

import logic.Printer;

import java.util.List;

public class UIViewRoot {
    private String locale;
    private String viewId;
    private List<UIComponent> components;

    public void checkValueBinding(){
        Printer.info("UIViewRoot: checkValueBinding");
        for (UIComponent component : components) {
            component.checkValueBinding();
        }
    }
    public void processUpdate(){
        Printer.info("UIViewRoot: processUpdate");
        for (UIComponent component : components) {
            component.processUpdate();
        }
    }

    public String encodeXX(){
        Printer.info("UIViewRoot: encodeXX");
        StringBuilder result = new StringBuilder();
        result.append("{");
        for (UIComponent component : components) {
            result.append(component.encodeXX()).append(", ");
        }
        if (result.length() > 1) {
            result.setLength(result.length() - 2);
        }
        result.append("}");
        return result.toString();
    }

    public void showModel(){
        for (UIComponent component : components) {
            component.showModel();
        }
    }

    public void processApplication(){
        Printer.info("UIViewRoot: processApplication");
        for (UIComponent component : components) {
            component.processApplication();
        }
    }

    public boolean processValidators(){
        Printer.info("UIViewRoot: processValidators");
        boolean status=true;
        for (UIComponent component : components) {
            if(!component.processValidators()){
                status = false;
            }
        }

        return status;
    }
    public boolean processDecodes(List<UIComponent> components2){
        boolean status=true;
        Printer.info("UIViewRoot: processDecodes");
        for (UIComponent component : components) {
            UIComponent component2 = findComponentById(component.getId(), components2);
            if (component2 != null) {
                if(!component.processDecodes(component2.getValue())){
                    status = false;
                }
            }
        }
        return status;
    }

    private UIComponent findComponentById(String id, List<UIComponent> components) {
        for (UIComponent component : components) {
            if (component.getId().equals(id)) {
                return component;
            }
        }
        return null;
    }

    public void makeTree(List<UIComponent> components){
        this.components = components;
        showTree();
    }

    public void showTree(){
        Printer.info("UIViewRoot: дерево компонентов: "+ components);
    }

    public void setLocale(String locale){
        Printer.info("UIViewRoot: поставлена локаль: "+ locale);
        this.locale = locale;
    }

    public String getViewId() {
        return viewId;
    }

    public void setViewId(String viewId) {
        this.viewId = viewId;
    }

    public List<UIComponent> getComponents() {
        return components;
    }

    public void setComponents(List<UIComponent> components) {
        this.components = components;
    }

    @Override
    public String toString(){
        return String.format("UIViewRoot [viewId: %s, locale: %s, components: %s]", viewId, locale, components.toString());
    }
}
