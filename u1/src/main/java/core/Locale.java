package core;

import javafx.beans.property.SimpleStringProperty;

/**
 * This class provides localization
 */
public final class Locale {
    private SimpleStringProperty add, addAll, remove, play, skip, delete, detail;

    public Locale(){
        add = new SimpleStringProperty("Add");
        addAll = new SimpleStringProperty("Add all");
        remove = new SimpleStringProperty("Remove");
        play = new SimpleStringProperty("Play");
        skip = new SimpleStringProperty("Skip");
        delete = new SimpleStringProperty("All Songs");
        detail = new SimpleStringProperty("Details");
    }

    /**
     * Access the addAll string property
     * @return property for addAll text
     */
    public SimpleStringProperty addAllProperty() {
        return addAll;
    }

    /**
     * Access the addAll string property
     * @return property for addAll text
     */
    public SimpleStringProperty addProperty() {
        return add;
    }

    /**
     * Access the remove string property
     * @return property for remove text
     */
    public SimpleStringProperty removeProperty() {
        return remove;
    }

    /**
     * Access the okay string property
     * @return property for play text
     */
    public SimpleStringProperty playProperty() {
        return play;
    }

    /**
     * Access the skip string property
     * @return property for skip text
     */
    public SimpleStringProperty skipProperty() {
        return skip;
    }

    /**
     * Access the delete string property
     * @return property for delete
     */
    public SimpleStringProperty deleteProperty() {
        return delete;
    }

    /**
     * Access the delete string property
     * @return property for delete
     */
    public SimpleStringProperty detailProperty() {
        return detail;
    }
}
