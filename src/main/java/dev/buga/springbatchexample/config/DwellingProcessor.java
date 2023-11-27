package dev.buga.springbatchexample.config;

import dev.buga.springbatchexample.entity.Dwelling;
import org.springframework.batch.item.ItemProcessor;

public class DwellingProcessor implements ItemProcessor<Dwelling, Dwelling> {
    @Override
    public Dwelling process(Dwelling dwelling) throws Exception {
        return dwelling;
    }
}
