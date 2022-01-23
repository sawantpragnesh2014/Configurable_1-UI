package com.example.configurableui.models;

import java.util.ArrayList;

public class Screen{
    public String groupName;
    public String seperatorColor;
    public ArrayList<Field> fields;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getSeperatorColor() {
        return seperatorColor;
    }

    public void setSeperatorColor(String seperatorColor) {
        this.seperatorColor = seperatorColor;
    }

    public ArrayList<Field> getFields() {
        return fields;
    }

    public void setFields(ArrayList<Field> fields) {
        this.fields = fields;
    }
}