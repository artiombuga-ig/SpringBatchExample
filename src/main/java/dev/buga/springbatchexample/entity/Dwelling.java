package dev.buga.springbatchexample.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "DWELLINGS_INFO")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dwelling {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "MONTH")
    private String date;
    @Column(name = "SA2_CODE")
    private int SA2_code;
    @Column(name = "SA2_NAME")
    private String SA2_name;
    @Column(name = "TERRITORIAL_AUTHORITY")
    private String authority;
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
}
