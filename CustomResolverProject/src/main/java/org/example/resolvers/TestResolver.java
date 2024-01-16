package org.example.resolvers;

import org.apache.synapse.commons.resolvers.Resolver;

/**
 * Custom resolver implementation that resolves a custom variable format.
 */
public class TestResolver implements Resolver {
    private String input;

    @Override
    public void setVariable(String input) {
        this.input = input;
    }

    @Override
    public String resolve() {
        if ("abc".equals(input)){
            return "pqr";
        } else  return input;
    }
}