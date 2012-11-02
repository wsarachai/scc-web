package org.ktm.domain.party;

public enum ISOGender {
    MALE("isogender.male"), FEMALE("isogender.female"), NOTKNOWN("isogender.not-known"), NOTSPECIFIED("isogender.not-specified");

    private String genderType;

    private ISOGender(String genderType) {
        this.genderType = genderType;
    }

    public String getGenderType() {
        return genderType;
    }

    public ISOGender getId() {
        return this;
    }
}
