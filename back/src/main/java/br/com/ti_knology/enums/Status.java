package br.com.ti_knology.enums;

public enum Status {
    AGUARDANDO_PAGAMENTO("AGUARDANDO PAGAMENTO"),
    EM_ELABORACAO("EM ELABORAÇÃO"),
    PAUSADO("PAUSADO"),
    CANCELADO("CANCELADO"),
    CONCLUIDO("CONCLUIDO");

    private final String value;

    Status(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Status fromValue(String value) {
        for (Status status : Status.values()) {
            if (status.getValue().equals(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("No enum constant for value: " + value);
    }
}