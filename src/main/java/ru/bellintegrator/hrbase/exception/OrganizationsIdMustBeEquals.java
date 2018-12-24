package ru.bellintegrator.hrbase.exception;

public class OrganizationsIdMustBeEquals extends RuntimeException {
    /**
     * Id организации из PathVariable
     */
    private String urlId;
    /**
     * Id организации из RequestBody
     */
    private String jsonId;

    public OrganizationsIdMustBeEquals() {
    }

    public OrganizationsIdMustBeEquals(String urlId, String jsonId) {
        this.urlId = urlId;
        this.jsonId = jsonId;
    }

    public String getUrlId() {
        return urlId;
    }

    public void setUrlId(String orgId) {
        this.urlId = orgId;
    }

    public String getJsonId() {
        return jsonId;
    }

    public void setJsonId(String jsonId) {
        this.jsonId = jsonId;
    }
}
