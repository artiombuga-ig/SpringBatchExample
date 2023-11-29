package dev.buga.springbatchexample.repository;

import dev.buga.springbatchexample.entity.DateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DateEntityRepository extends JpaRepository<DateEntity, Integer> {
}
