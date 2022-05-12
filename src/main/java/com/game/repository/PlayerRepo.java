package com.game.repository;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PlayerRepo extends JpaRepository<Player, Long> {

    @Query("SELECT count(p.id) FROM Player p " +
            "WHERE (:name is null OR p.name LIKE %:name%) " +
            "AND (:title is null OR p.title LIKE %:title%) " +
            "AND (:race is null OR p.race = :race) " +
            "AND (:profession is null OR p.profession = :profession) " +
            "AND (:banned is null OR p.banned = :banned) " +
            "AND (:minExperience is null OR p.experience >= :minExperience) " +
            "AND (:maxExperience is null OR p.experience <= :maxExperience) " +
            "AND (:minLevel is null OR p.level >= :minLevel) " +
            "AND (:maxLevel is null OR p.level <= :maxLevel) " +
            "AND (:after is null OR p.birthday >= :after) " +
            "AND (:before is null OR p.birthday <= :before)")
    int count(@Param("name") String name, @Param("title") String title, @Param("race") Race race,
              @Param("profession") Profession profession, @Param("banned") Boolean banned,
              @Param("minExperience") Integer minExperience, @Param("maxExperience") Integer maxExperience,
              @Param("minLevel") Integer minLevel, @Param("maxLevel") Integer maxLevel, @Param("after") Date after,
              @Param("before") Date before);


    @Query("SELECT p FROM Player p " +
            "WHERE (:name is null OR p.name LIKE %:name%) " +
            "AND (:title is null OR p.title LIKE %:title%) " +
            "AND (:race is null OR p.race = :race) " +
            "AND (:profession is null OR p.profession = :profession) " +
            "AND (:banned is null OR p.banned = :banned) " +
            "AND (:minExperience is null OR p.experience >= :minExperience) " +
            "AND (:maxExperience is null OR p.experience <= :maxExperience) " +
            "AND (:minLevel is null OR p.level >= :minLevel) " +
            "AND (:maxLevel is null OR p.level <= :maxLevel) " +
            "AND (:after is null OR p.birthday >= :after) " +
            "AND (:before is null OR p.birthday <= :before)")
    List<Player> findAll(@Param("name") String name, @Param("title") String title, @Param("race") Race race,
                         @Param("profession") Profession profession, @Param("banned") Boolean banned,
                         @Param("minExperience") Integer minExperience, @Param("maxExperience") Integer maxExperience,
                         @Param("minLevel") Integer minLevel, @Param("maxLevel") Integer maxLevel,
                         @Param("after") Date after, @Param("before") Date before, Pageable pageable);

}
