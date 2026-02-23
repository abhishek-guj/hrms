package com.roima.hrms.repository;

import com.roima.hrms.entities.EmployeeProfile;
import com.roima.hrms.entities.GameQueue;
import com.roima.hrms.entities.GameSlot;
import com.roima.hrms.entities.SlotBooking;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GameQueueRepository extends JpaRepository<GameQueue, Long> {

        Optional<GameQueue> findBySlotAndOwner(GameSlot gameSlot, EmployeeProfile currentPlayer);

        @Query("""
                        select q
                        from GameQueue q
                        where q.slot = :gameSlot
                        order by q.priorityAtRequest asc, q.requestedOn asc
                        """)
        List<GameQueue> findBySlotOrderByPriorityAtRequest(GameSlot gameSlot);

        @Query("""
                        select gq
                        from GameQueue gq
                        where gq.slot.id = :gameSlotId
                        and gq.owner = :player
                        """)
        List<GameQueue> findByGameSlotAndOwner(Long gameSlotId, EmployeeProfile player);

        // select distinct sb,gs from SlotBooking sb
        // join sb.playerGroup pg
        // join pg.players p
        // join sb.gameSlot gs
        // where p.id = :playerId
        // and sb.isDeleted = false
        // order by gs.slotStart desc
        @Query("""
                        select distinct gq,gs from GameQueue gq
                        join gq.playerGroup pg
                        join pg.players p
                        join gq.slot gs
                        where p.id = :playerId
                        order by gs.slotStart desc
                              """)
        List<GameQueue> findAllByPlayer(Long playerId);
}