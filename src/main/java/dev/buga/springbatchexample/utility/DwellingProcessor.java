package dev.buga.springbatchexample.utility;

import dev.buga.springbatchexample.entity.DateEntity;
import dev.buga.springbatchexample.entity.Dwelling;
import dev.buga.springbatchexample.entity.DwellingInfo;
import dev.buga.springbatchexample.entity.SA2Entity;
import dev.buga.springbatchexample.repository.DateEntityRepository;
import dev.buga.springbatchexample.repository.DwellingInfoRepository;
import dev.buga.springbatchexample.repository.SA2EntityRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.batch.item.validator.Validator;

public class DwellingProcessor implements ItemProcessor<FieldSet, Dwelling> {
    private final Validator<FieldSet> validator;
    private final DwellingInfoRepository dwellingInfoRepository;
    private final DateEntityRepository dateEntityRepository;
    private final SA2EntityRepository sa2EntityRepository;

    public DwellingProcessor(DwellingValidator validator, DwellingInfoRepository dwellingInfoRepository, DateEntityRepository dateEntityRepository, SA2EntityRepository sa2EntityRepository) {
        this.validator = validator;
        this.dwellingInfoRepository = dwellingInfoRepository;
        this.dateEntityRepository = dateEntityRepository;
        this.sa2EntityRepository = sa2EntityRepository;
    }

    @Override
    public Dwelling process(FieldSet fieldSet) {
        validator.validate(fieldSet);

        Dwelling dwelling = new Dwelling();

        String dateStr = fieldSet.readString("month");
        int sa2Code = fieldSet.readInt("SA2_code");
        String sa2Name = fieldSet.readString("SA2_name");
        String authority = fieldSet.readString("territorial_authority");
        int totalDwellings = fieldSet.readInt("total_dwelling_units");
        int houses = fieldSet.readInt("houses");
        int apartments = fieldSet.readInt("apartments");
        int retirementVillas = fieldSet.readInt("retirement_village_units");
        int townhouses = fieldSet.readInt("townhouses_flats_units_other");

        DateEntity dateEntity = new DateEntity();
        dateEntity.setDate(dateStr);
        dateEntity.setDwelling(dwelling);

        SA2Entity sa2Entity = new SA2Entity();
        sa2Entity.setSA2_code(sa2Code);
        sa2Entity.setSA2_name(sa2Name);
        sa2Entity.setAuthority(authority);
        sa2Entity.setDwelling(dwelling);

        DwellingInfo dwellingInfo = new DwellingInfo();
        dwellingInfo.setTotal_dwellings(totalDwellings);
        dwellingInfo.setHouses(houses);
        dwellingInfo.setApartments(apartments);
        dwellingInfo.setRetirement_villas(retirementVillas);
        dwellingInfo.setTownhouses(townhouses);
        dwellingInfo.setDwelling(dwelling);

        dateEntityRepository.save(dateEntity);
        sa2EntityRepository.save(sa2Entity);
        dwellingInfoRepository.save(dwellingInfo);

        return dwelling;
    }
}
