package main.java.datatransfer;

import java.io.Serializable;

public class ActionHandler implements Serializable {

    private final String action;
    private final Object object;

    public ActionHandler(String action, Object object){
        this.action = action;
        this.object = object;
    }

    public Object getObject() {
        return object;
    }

    public String getAction() {
        return action;
    }
}
