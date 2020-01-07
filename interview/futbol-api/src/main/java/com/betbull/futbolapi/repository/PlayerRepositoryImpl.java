package com.betbull.futbolapi.repository;

import com.betbull.futbolapi.model.PlayerEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;


@Repository
@RequiredArgsConstructor
public class PlayerRepositoryImpl implements PlayerRepositoryCustom{

    private final EntityManager entityManager;

    @Override
    public List<PlayerEntity> findByNameAndSurname(String name, String surname) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<PlayerEntity> query = cb.createQuery(PlayerEntity.class);

        Root<PlayerEntity> from = query.from(PlayerEntity.class);

        List<Predicate> predicates = new ArrayList<>();

        if (name != null) {
            predicates.add(cb.equal(from.get("name"), name));
        }

        if (surname != null) {
            predicates.add(cb.equal(from.get("surname"), surname ));
        }

        query.where(predicates.toArray(new Predicate[0])).orderBy(cb.asc(from.get("name")));

        return entityManager.createQuery(query).getResultList();

    }
}
