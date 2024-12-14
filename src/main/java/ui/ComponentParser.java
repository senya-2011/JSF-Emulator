package ui;

import logic.FacesContext;

import java.util.ArrayList;
import java.util.List;

public class ComponentParser {
    public static List<UIComponent> parseComponents(FacesContext facesContext) {
        String request = facesContext.getRequest();
        List<UIComponent> components = new ArrayList<>();

        String parameters = request.split(";")[2];
        parameters = parameters.substring(1, parameters.length() - 1);
        String[] componentStrings = parameters.split("],");

        for (String componentString : componentStrings) {
            componentString = componentString.trim();
            if (!componentString.endsWith("]")) {
                componentString += "]";
            }
            String[] parts = componentString.substring(1, componentString.length() - 1).split("=");
            String id = parts[0].trim();
            String value = parts[1].trim();

            UIComponent uiComponent = new UIComponent();
            uiComponent.setId(id);
            uiComponent.setValue(value);
            components.add(uiComponent);
        }

        return components;
    }
}
