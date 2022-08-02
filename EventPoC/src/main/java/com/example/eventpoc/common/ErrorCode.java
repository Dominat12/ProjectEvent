package com.example.eventpoc.common;

import com.google.common.base.Preconditions;

/**
 * Klasse, die einen Error Code wiederspiegelt.
 * Siehe für weitere Informationen ins Wiki oder zukünftig hier:
 *
 */
public class ErrorCode {
    private int type;
    private int service;
    private int identifier;

    private ErrorCode(int type, int service, int identifier) {
        Preconditions.checkArgument(type > 0 && type < 3, "ErrorType argument is not valid");
        Preconditions.checkArgument(service > -1 && service < 100, "Service argument is not valid");
        Preconditions.checkArgument(identifier > 0 && identifier < 1000, "Identifier argument is not valid");
        this.type = type;
        this.service = service;
        this.identifier = identifier;
    }

    public static ErrorCode create(CodeType type, int service, int identifier){
        return new ErrorCode(type.type, service, identifier);
    }

    /**
     * Gibt den ErrorCode als Integer zurück
     * Erste Zahl ist der Type, zweite und dritte der Service und die letzten drei sind der Identifier
     * Bsp.
     * type = 2
     * service = 45
     * identifier = 560
     * returns: 245560
     * @return int Repräsentation des ErrorCodes
     */
    public int toInt(){
        return 100_000 * type + 1_000* service + identifier;
    }

    @Override
    public String toString() {
        return "ErrorCode{" +
                "type=" + type +
                ", service=" + service +
                ", identifier=" + identifier +
                '}';
    }
}
