package me.predatorray.velocli;

import java.util.Set;

public class Context {

    private Set<String> keys;

    public Context(Set<String> keys) {
        this.keys = keys;
    }

    public Set<String> getKeys() {
        return keys;
    }
}
