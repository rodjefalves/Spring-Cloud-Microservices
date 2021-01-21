package br.com.conductor.gatewayservice.infrastructure.log;

public class LogBuilder {

    private String header;
    private String rows;

    public LogBuilder() {
    }

    public LogBuilder(String header, String rows) {
        super();
        this.header = header;
        this.rows = rows;
    }

    public static LogBuilder of() {
        return new LogBuilder("", "");
    }

    public LogBuilder header(String header) {
        this.header = " => " + header;
        return this;
    }

    public LogBuilder row(String row) {
        this.rows += "\n - " + row;
        return this;
    }

    public LogBuilder row(String description, Object value) {
        this.rows += "\n - " + description + ": " + value;
        return this;
    }

    public String build() {
        return header + rows;
    }

}