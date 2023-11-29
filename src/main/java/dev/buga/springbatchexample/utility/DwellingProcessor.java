package dev.buga.springbatchexample.utility;

import dev.buga.springbatchexample.entity.DateEntity;
import dev.buga.springbatchexample.entity.Dwelling;
import dev.buga.springbatchexample.dto.DwellingDTO;
import dev.buga.springbatchexample.entity.DwellingInfo;
import dev.buga.springbatchexample.entity.SA2Entity;
import dev.buga.springbatchexample.repository.DateEntityRepository;
import dev.buga.springbatchexample.repository.DwellingInfoRepository;
import dev.buga.springbatchexample.repository.SA2EntityRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.validator.Validator;

public class DwellingProcessor implements ItemProcessor<DwellingDTO, Dwelling> {
    private final Validator<DwellingDTO> validator;
    private final DwellingInfoRepository dwellingInfoRepository;
    private final DateEntityRepository dateEntityRepository;
    private final SA2EntityRepository sa2EntityRepository;

    public DwellingProcessor(Validator<DwellingDTO> validator, DwellingInfoRepository dwellingInfoRepository, DateEntityRepository dateEntityRepository, SA2EntityRepository sa2EntityRepository) {
        this.validator = validator;
        this.dwellingInfoRepository = dwellingInfoRepository;
        this.dateEntityRepository = dateEntityRepository;
        this.sa2EntityRepository = sa2EntityRepository;
    }

    @Override
    public Dwelling process(DwellingDTO dwellingDTO) {
        validator.validate(dwellingDTO);

        Dwelling dwelling = new Dwelling();

        DateEntity dateEntity = new DateEntity();
        dateEntity.setDate(dwellingDTO.getDate());
        dateEntity.setDwelling(dwelling);
        dateEntityRepository.save(dateEntity);

        SA2Entity sa2Entity = new SA2Entity();
        sa2Entity.setSA2_code(dwellingDTO.getSA2Code());
        sa2Entity.setSA2_name(dwellingDTO.getSA2Name());
        sa2Entity.setAuthority(dwellingDTO.getAuthority());
        sa2Entity.setDwelling(dwelling);
        sa2EntityRepository.save(sa2Entity);

        DwellingInfo dwellingInfo = new DwellingInfo();
        dwellingInfo.setTotal_dwellings(dwellingDTO.getTotalDwellings());
        dwellingInfo.setHouses(dwellingDTO.getHouses());
        dwellingInfo.setApartments(dwellingDTO.getApartments());
        dwellingInfo.setRetirement_villas(dwellingDTO.getRetirementVillas());
        dwellingInfo.setTownhouses(dwellingDTO.getTownhouses());
        dwellingInfo.setDwelling(dwelling);
        dwellingInfoRepository.save(dwellingInfo);

        return dwelling;
    }
}
