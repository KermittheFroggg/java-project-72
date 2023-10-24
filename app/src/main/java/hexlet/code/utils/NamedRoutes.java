package hexlet.code.utils;

public class NamedRoutes {
    public static String root() {
        return "/";
    }

    public static String urls() {
        return "/urls";
    }
    public static String showUrl(Long id) {
        return showUrl(String.valueOf(id));
    }
    public static String showUrl(String id) {
        return urls() + "/" + id;
    }
    public static String checkUrl(String id) {
        return urls() + "/" + id + "/checks";
    }
    public static String checkUrl(Long id) {
        return checkUrl(String.valueOf(id));
    }
}
