package com.oh29oh29.domain.item;

import com.oh29oh29.domain.Category;
import com.oh29oh29.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "dtype")
@Getter
@Setter
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    /**
     * stock 증가
     * */
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    /**
     * stock 감소
     * */
    public void removeStock(int quantity) {
        int restTock = this.stockQuantity - quantity;
        if (restTock < 0) {
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restTock;
    }
}
