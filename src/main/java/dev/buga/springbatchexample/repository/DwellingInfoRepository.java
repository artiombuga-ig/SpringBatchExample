package dev.buga.springbatchexample.repository;

import dev.buga.springbatchexample.entity.DwellingInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DwellingInfoRepository extends JpaRepository<DwellingInfo, Integer> {
}
