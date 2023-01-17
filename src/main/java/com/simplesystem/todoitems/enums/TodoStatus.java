package com.simplesystem.todoitems.enums;

import com.simplesystem.todoitems.exception.InvalidDataException;

import java.util.Optional;

public enum TodoStatus {
    DONE(0),
    NOT_DONE(1),
    PAST_DUE(2);

    private int value;

    TodoStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static TodoStatus fromOrdinal(Integer status) {
        Optional<Integer> requested = Optional.ofNullable(status);
        return switch (requested.orElseThrow(() -> new InvalidDataException("Invalid status requested"))) {
            case 1 -> TodoStatus.NOT_DONE;
            case 2 -> TodoStatus.DONE;
            case 3 -> TodoStatus.PAST_DUE;
            default -> throw new InvalidDataException("Invalid status requested");
        };
    }
}
