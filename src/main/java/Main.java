import logic.FacesServlet;
import ui.UiDom;

import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FacesServlet facesServlet = new FacesServlet();

        // Создаем объекты для каждой страницы
        UiDom index = new UiDom();
        UiDom login = new UiDom("{[login=user], [password=pass]}", "/login.xhtml");
        UiDom main = new UiDom("{[count=0], [date=01.01.2025]}", "/main.xhtml");

        while (true) {
            System.out.println("Введите запрос, пример \"GET index\" \n(есть GET и POST, а из страниц index, login, main)");
            String line = scanner.nextLine();
            String[] parts = line.split(" ");
            String method = parts[0].toUpperCase(Locale.ROOT);
            String page = parts[1].toUpperCase(Locale.ROOT);

            String response = "";

            // Обрабатываем запрос в зависимости от метода (GET или POST) и страницы
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
                default:
                    response = facesServlet.receiveRequest(index.sendGetRequest());
                    break;
            }

            System.out.println(response); // Выводим результат
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
