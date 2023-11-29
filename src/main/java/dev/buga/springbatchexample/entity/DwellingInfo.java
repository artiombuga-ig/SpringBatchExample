package dev.buga.springbatchexample.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "DWELLING_INFO")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DwellingInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "ID")
    private Dwelling dwelling;
}
