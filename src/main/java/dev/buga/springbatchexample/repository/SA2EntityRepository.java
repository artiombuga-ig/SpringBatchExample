package dev.buga.springbatchexample.repository;

import dev.buga.springbatchexample.entity.SA2Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SA2EntityRepository extends JpaRepository<SA2Entity, Integer> {
}
