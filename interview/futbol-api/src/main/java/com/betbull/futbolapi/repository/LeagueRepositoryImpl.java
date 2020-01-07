package com.betbull.futbolapi.repository;

import com.betbull.futbolapi.model.LeagueEntity;
import com.betbull.futbolapi.model.PlayerEntity;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.criteria.internal.OrderImpl;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class LeagueRepositoryImpl implements LeagueRepositoryCustom {

    private final EntityManager entityManager;


    public List<LeagueEntity> findLeaguesByPlayer(PlayerEntity player) {

        TypedQuery<LeagueEntity> typedQuery = entityManager.createQuery("from LeagueEntity l where l.player = :player order by l.startDate desc", LeagueEntity.class);
        typedQuery.setParameter("player", player );
        return typedQuery.getResultList();
    }

    public List<LeagueEntity> findLeaguesByTeamAndYear(Long teamId, int year) {

        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year+1,1,1);

        String query = "from LeagueEntity l where l.startDate >= :startDate and l.endDate < :endDate and l.team.id = :teamId";
        TypedQuery<LeagueEntity> typedQuery = entityManager.createQuery(query, LeagueEntity.class);
        typedQuery.setParameter("startDate", startDate);
        typedQuery.setParameter("endDate", endDate);
        typedQuery.setParameter("teamId", teamId );

        return typedQuery.getResultList();

    }
}
