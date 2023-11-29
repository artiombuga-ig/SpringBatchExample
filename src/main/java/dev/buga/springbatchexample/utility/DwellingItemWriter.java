package dev.buga.springbatchexample.utility;

import dev.buga.springbatchexample.entity.DateEntity;
import dev.buga.springbatchexample.entity.DwellingInfo;
import dev.buga.springbatchexample.entity.SA2Entity;
import dev.buga.springbatchexample.repository.DateEntityRepository;
import dev.buga.springbatchexample.repository.DwellingInfoRepository;
import dev.buga.springbatchexample.repository.SA2EntityRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DwellingItemWriter implements ItemWriter<List<Object>> {
    private final DwellingInfoRepository dwellingInfoRepository;
    private final DateEntityRepository dateEntityRepository;
    private final SA2EntityRepository sa2EntityRepository;

    public DwellingItemWriter(DwellingInfoRepository dwellingInfoRepository, DateEntityRepository dateEntityRepository, SA2EntityRepository sa2EntityRepository) {
        this.dwellingInfoRepository = dwellingInfoRepository;
        this.dateEntityRepository = dateEntityRepository;
        this.sa2EntityRepository = sa2EntityRepository;
    }

    @Override
    public void write(Chunk<? extends List<Object>> items) throws Exception {
        for (List<Object> item : items) {
            dateEntityRepository.save((DateEntity) item.get(0));
            sa2EntityRepository.save((SA2Entity) item.get(1));
            dwellingInfoRepository.save((DwellingInfo) item.get(2));
        }
    }
}
