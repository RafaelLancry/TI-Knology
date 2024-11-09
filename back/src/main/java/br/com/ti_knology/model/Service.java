package br.com.ti_knology.model;

import br.com.ti_knology.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "services")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Status status = Status.AGUARDANDO_PAGAMENTO; // Valor padrão

    private Float price;

    private Integer due;

    @Column(name = "deliver_date")  // Nome da coluna no banco de dados
    private Date deliverDate;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category; // Relacionamento com Category

    public Service(Category category, Date deliverDate, int due,  String name, Float price, Status status) {
        this.name = name;
        this.status = status;
        this.price = price;
        this.due = due;
        this.deliverDate = deliverDate;
        this.category = category;
    }
}