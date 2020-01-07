package com.betbull.futbolapi.repository;

import com.betbull.futbolapi.model.TeamEntity;
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
public class TeamRepositoryImpl implements TeamRepositoryCustom {

    private final EntityManager entityManager;

    @Override
    public List<TeamEntity> findByName(String name) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<TeamEntity> query = cb.createQuery(TeamEntity.class);

        Root<TeamEntity> from = query.from(TeamEntity.class);

        List<Predicate> predicates = new ArrayList<>();

        if (name != null) {
            predicates.add(cb.like(from.get("name"), "%"+name + "%"));
        }

        query.where(predicates.toArray(new Predicate[0])).orderBy(cb.asc(from.get("name")));
        return entityManager.createQuery(query).getResultList();
    }
}
