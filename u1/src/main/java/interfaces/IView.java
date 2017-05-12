package interfaces;

import java.util.Map;

/**
 * This interface is used as a skeleton for every view
 */
public interface IView {
    /**
     * Loads localization file
     * @param locale
     */
    void setLocale(Map<String, String> locale);
}
