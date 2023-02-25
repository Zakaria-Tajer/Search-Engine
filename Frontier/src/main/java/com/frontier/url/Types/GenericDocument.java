package com.frontier.url.Types;


import lombok.Data;

@Data
public class GenericDocument<T> {
    private T value;

    public GenericDocument(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public boolean isNull() {
        return value == null;
    }

    public static class D<T> extends GenericDocument<T> {
        public D() {
            super(null);
        }
    }

    public static class N<T> extends GenericDocument<T> {
        public N(T value) {
            super(value);
        }
    }

    public static <T> GenericDocument<T> fromNullable(T value) {
        return value == null ? new GenericDocument.D<>() : new GenericDocument.N<>(value);
    }
}
