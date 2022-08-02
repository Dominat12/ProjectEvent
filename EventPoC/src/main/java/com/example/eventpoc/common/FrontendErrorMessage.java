package com.example.eventpoc.common;

public enum FrontendErrorMessage {

    USER_NOT_FOUND_ERROR_MSG("Nutzer nicht gefunden", "Entschuldigen Sie, der gesuchte Nutzer konnte nicht gefunden werden"),
    FORBIDDEN_MSG("Vorgang nicht möglich", "Sie haben auf diese Ressourcen leider keinen Zugriff."),
    USERNAME_ALREADY_EXISTS_MSG("Vorgang nicht möglich", "Der angegebene Nuztername existiert bereits in unserem System. Versuchen Sie einen anderen.");

    private final String uiMessage;

    private final String title;

    FrontendErrorMessage( String title, String uiMessage) {
        this.uiMessage = uiMessage;
        this.title = title;
    }

    public String getUiMessage() {
        return uiMessage;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "FrontendErrorMessage{" +
                "uiMessage='" + uiMessage + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
