package org.event.core.domain;

public class Event {
    public String className;
    public Object object;

    public Event() {
    }

    public Event(String className, Object object) {
        this.className = className;
        this.object = object;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
