package dev.buga.springbatchexample.repository;

import dev.buga.springbatchexample.entity.Dwelling;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DwellingsRepository extends JpaRepository<Dwelling, Integer> {
}
