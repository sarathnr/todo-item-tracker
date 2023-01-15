package com.simplesystem.todoitems.enums;

import java.util.Arrays;

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

    public static TodoStatus fromOrdinal(int status) {
        return Arrays.stream(TodoStatus.values())
                .filter(o -> o.getValue() == status)
                .findFirst()
                .orElse(null);
    }
}
