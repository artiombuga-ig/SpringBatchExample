package dev.buga.springbatchexample.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DwellingDTO {
    private String date;
    private int SA2Code;
    private String SA2Name;
    private String authority;
    private int totalDwellings;
    private int houses;
    private int apartments;
    private int retirementVillas;
    private int townhouses;
}
