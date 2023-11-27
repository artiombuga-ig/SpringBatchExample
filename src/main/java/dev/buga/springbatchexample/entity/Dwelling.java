package dev.buga.springbatchexample.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "DWELLINGS_INFO")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dwelling {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "date_id")
    private DateEntity dateEntity;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "sa2_id")
    private SA2Entity sa2Entity;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "dwelling_info_id")
    private DwellingInfo dwellingInfo;
}
