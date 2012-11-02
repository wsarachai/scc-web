package org.ktm.domain.party;

public enum EPrenameType {
    Mr("prename.mr"), Miss("prename.miss"), Mis("prename.mis");

    private String value;

    private EPrenameType(String value) {
        this.setValue(value);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public EPrenameType parse(String parseValue) {
        return Enum.valueOf(EPrenameType.class, parseValue);
    }

    @Override
    public String toString() {
        return value;
    }
}
