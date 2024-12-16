import logic.FacesServlet;
import ui.UiDom;

import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FacesServlet facesServlet = new FacesServlet();

        UiDom index = new UiDom();
        UiDom login = new UiDom("{[login=user], [password=pass]}", "/login.xhtml");
        UiDom main = new UiDom("{[count=0], [date=01.01.2025]}", "/main.xhtml");
        UiDom invalid = new UiDom("{[invalid=invalid], [date=01.01.2025]}", "/invalid.xhtml");
        UiDom decode = new UiDom("{[decode=decodeError], [date=01.01.2025]}", "/decode.xhtml");
        UiDom errors = new UiDom("{[decode=decodeError], [invalid=invalid]}", "/errors.xhtml");

        while (true) {
            System.out.println("Введите запрос, пример \"GET index\" \n(есть GET и POST, а из страниц index, login, main, invalid, decode, errors)");
            String line = scanner.nextLine();
            String[] parts = line.split(" ");


            String method = parts[0].toUpperCase(Locale.ROOT);
            String page = parts[1].toUpperCase(Locale.ROOT);

            String response = "";

            switch (page) {
                case "INDEX":
                    response = handleRequestMethod(method, index, facesServlet);
                    break;
                case "LOGIN":
                    response = handleRequestMethod(method, login, facesServlet);
                    break;
                case "MAIN":
                    response = handleRequestMethod(method, main, facesServlet);
                    break;
                case "INVALID":
                    response = handleRequestMethod(method, invalid, facesServlet);
                    break;
                case "DECODE":
                    response = handleRequestMethod(method, decode, facesServlet);
                    break;
                case "ERRORS":
                    response = handleRequestMethod(method, errors, facesServlet);
                    break;
                default:
                    response = facesServlet.receiveRequest(index.sendGetRequest());
                    break;
            }

            System.out.println(response);
        }
    }

    // Метод для обработки GET и POST запросов
    private static String handleRequestMethod(String method, UiDom pageDom, FacesServlet facesServlet) {
        String response = "";

        // Для GET запроса
        if (method.equals("GET")) {
            response = facesServlet.receiveRequest(pageDom.sendGetRequest());
        }
        // Для POST запроса
        else if (method.equals("POST")) {
            response = facesServlet.receiveRequest(pageDom.sendPostRequest());
        }

        // Устанавливаем DOM после получения ответа
        pageDom.setDom(response.split(";")[0]);

        return response;
    }
}
