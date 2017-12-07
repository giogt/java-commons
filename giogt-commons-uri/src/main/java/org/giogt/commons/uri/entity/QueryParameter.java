package org.giogt.commons.uri.entity;

import org.giogt.commons.core.Preconditions;

import java.io.Serializable;

public class QueryParameter implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String value;

    public QueryParameter() {
    }

    public QueryParameter(String name, String value) {
        Preconditions.notNull(name, "name");

        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        Preconditions.notNull(name, "name");

        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QueryParameter that = (QueryParameter) o;

        if (!name.equals(that.name)) return false;
        return value != null ? value.equals(that.value) : that.value == null;

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "QueryParameter{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
