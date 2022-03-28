package catalog;

import java.util.List;

public class Validators {

    public static boolean isBlank(String s) {
        if (s == null || s.isEmpty() || s.isBlank()) {
            return true;
        }
        return false;
    }

    public static boolean isEmpty(List<String> list) {
        if (list == null || list.isEmpty()) {
            return true;
        }
        return false;
    }
}
