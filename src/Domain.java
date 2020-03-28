class Domain {
    private String domain;
    private String source;

    public Domain(String domain, String source) {
        this.domain = domain;
        this.source = source;
    }

    @Override
    public String toString() {
        return "Domain{" +
                "domain='" + domain + '\'' +
                '}';
    }

    String getDomain() {
        return domain;
    }

    void setDomain(String domain) {
        this.domain = domain;
    }

    String getSource() {
        return source;
    }

    void setSource(String source) {
        this.source = source;
    }
}
