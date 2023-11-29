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
@Table(name = "DWELLING_INFO")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DwellingInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "TOTAL_DWELLING_UNITS")
    private int total_dwellings;
    @Column(name = "HOUSES")
    private int houses;
    @Column(name = "APARTMENTS")
    private int apartments;
    @Column(name = "RETIREMENT_VILLAGE_UNITS")
    private int retirement_villas;
    @Column(name = "TOWNHOUSE_FLATS_UNITS_OTHER")
    private int townhouses;
    @OneToOne(mappedBy = "dwellingInfo")
    private Dwelling dwelling;
}
