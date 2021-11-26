package fr.arnoux23u.javano.data;

import java.io.Serializable;

/**
 * Class that represents an action shared between the server and the client.
 * This class is serializable.
 *
 * @author arnoux23u
 */
public class ActionHandler implements Serializable {

    private final String action;
    private final Object object;

    /**
     * Public constructor with two parameters.
     *
     * @param action the action to perform
     * @param object the object received the action
     */
    public ActionHandler(String action, Object object) {
        this.action = action;
        this.object = object;
    }

    /**
     * Get the object received the action.
     *
     * @return object
     */
    public Object getObject() {
        return object;
    }

    /**
     * Get the action to perform.
     *
     * @return action
     */
    public String getAction() {
        return action;
    }
}
