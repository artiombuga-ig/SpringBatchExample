package dev.buga.springbatchexample.utility;

import dev.buga.springbatchexample.entity.DateEntity;
import dev.buga.springbatchexample.entity.Dwelling;
import dev.buga.springbatchexample.dto.DwellingDTO;
import dev.buga.springbatchexample.entity.DwellingInfo;
import dev.buga.springbatchexample.entity.SA2Entity;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.validator.Validator;

public class DwellingProcessor implements ItemProcessor<DwellingDTO, Dwelling> {
    private final Validator<DwellingDTO> validator;
    public DwellingProcessor(Validator<DwellingDTO> validator) {
        this.validator = validator;
    }

    @Override
    public Dwelling process(DwellingDTO dwellingDTO) {
        validator.validate(dwellingDTO);

        Dwelling dwelling = new Dwelling();

        DateEntity dateEntity = new DateEntity();
        dateEntity.setDate(dwellingDTO.getDate());
        dwelling.setDateEntity(dateEntity);

        SA2Entity sa2Entity = new SA2Entity();
        sa2Entity.setSA2_code(dwellingDTO.getSA2Code());
        sa2Entity.setSA2_name(dwellingDTO.getSA2Name());
        sa2Entity.setAuthority(dwellingDTO.getAuthority());
        dwelling.setSa2Entity(sa2Entity);

        DwellingInfo dwellingInfo = new DwellingInfo();
        dwellingInfo.setTotal_dwellings(dwellingDTO.getTotalDwellings());
        dwellingInfo.setHouses(dwellingDTO.getHouses());
        dwellingInfo.setApartments(dwellingDTO.getApartments());
        dwellingInfo.setRetirement_villas(dwellingDTO.getRetirementVillas());
        dwellingInfo.setTownhouses(dwellingDTO.getTownhouses());
        dwelling.setDwellingInfo(dwellingInfo);

        return dwelling;
    }
}
