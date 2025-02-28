package models;

import java.util.List;

public class AppMsg {
    private boolean IsError;
    private List<String> messages;

    public AppMsg(boolean isError, List<String> messages) {
        IsError = isError;
        this.messages = messages;
    }

    public boolean isError() {
        return IsError;
    }

    public void setError(boolean error) {
        IsError = error;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }
}
