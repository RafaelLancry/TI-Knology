package br.com.ti_knology.model;

import br.com.ti_knology.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "service")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String status = Status.ANALISE.name(); // Valor padrão

    private Float price;

    private Integer due;

    @Column(name = "deliver_date")  // Nome da coluna no banco de dados
    @Setter(AccessLevel.PUBLIC)
    private Date deliverDate;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category; // Relacionamento com Category

    public Service(Category category, Date deliverDate, int due,  String name, Float price, String status) {
        this.name = name;
        this.status = status;
        this.price = price;
        this.due = due;
        this.deliverDate = deliverDate;
        this.category = category;
    }

    public Service(String name, Long id, Category category, Float price, Integer due, Date deliverDate, Status status) {
    }

    public Service(Service newService) {
        this.id = newService.id;
        this.name = newService.name;
        this.status = newService.status;
        this.price = newService.price;
        this.due = newService.due;
        this.deliverDate = newService.deliverDate;
        this.category = newService.category;
    }
}