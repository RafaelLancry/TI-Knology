package br.com.ti_knology.enums;

import lombok.Getter;

import java.util.Objects;

public enum ServicesType {
    DESENVOLVIMENTO_DE_SOFTWARE("Desenvolvimento de Software", 1L),
    CONSULTORIA_EM_TI("Consultoria em TI", 2L),
    SOLUCOES_EM_NUVEM("Soluções em Nuvem", 3L),
    DESENVOLVIMENTO_PERSONALIZADO("Desenvolvimento de Software Personalizado", 4L),
    CONSULTORIA_TRANSFORMACAO("Consultoria em Transformação Digital", 5L),
    INTELIGENCIA_ARTIFICIAL("Inteligência Artificial e Aprendizado de Máquina", 6L),
    SOLUCAO_IOT("Soluções de Internet das Coisas (IoT)", 7L),
    SOLUCOES_EMBARCADAS("Soluções em Tecnologias Embarcadas", 8L);

    @Getter
    private final String value; // Campo para armazenar o valor
    @Getter
    private final Long id;

    // Construtor privado
    ServicesType(String value, Long i) {
        this.value = value;
        this.id = i;
    }

    // Método para obter um Status a partir de seu valor
    public static ServicesType fromValue(String value) {
        for (ServicesType servicesType : ServicesType.values()) {
            if (servicesType.getValue().equals(value)) {
                return servicesType;
            }
        }
        throw new IllegalArgumentException("No enum constant for value: " + value);
    }

    // Método para obter um Status a partir de seu valor
    public static ServicesType fromRawValue(String value) {
        for (ServicesType servicesType : ServicesType.values()) {
            if (Objects.equals(servicesType.toString(), value)) {
                return servicesType;
            }
        }
        throw new IllegalArgumentException("No enum constant for value: " + value);
    }

    public static ServicesType fromId(Long id) {
        for (ServicesType servicesType : ServicesType.values()) {
            if(servicesType.getId().equals(id)) {
                return servicesType;
            }
        }
        throw new IllegalArgumentException("No enum constant for value: " + id);
    }
}
