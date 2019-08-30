package person.fengjr.damai;

import java.util.ResourceBundle;

/**
 * @author fengjr
 * @date 2019/8/30.
 */
public class GlobalConfig {
    protected static ResourceBundle bundle;
    static {
        bundle = ResourceBundle.getBundle("config");
    }

    public static String getString(String key){
        return bundle.getString(key);
    }
}
