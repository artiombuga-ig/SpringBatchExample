package dev.buga.springbatchexample.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SA2_INFO")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SA2Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "SA2_CODE")
    private int SA2_code;
    @Column(name = "SA2_NAME")
    private String SA2_name;
    @Column(name = "TERRITORIAL_AUTHORITY")
    private String authority;
    @OneToOne(mappedBy = "sa2Entity")
    private Dwelling dwelling;
}
