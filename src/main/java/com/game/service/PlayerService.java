package com.game.service;

import com.game.controller.PlayerOrder;
import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.repository.PlayerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepo playerRepo;

    private Integer calculateLevel(Integer experience) {
        return (int) (Math.sqrt(2500 + 200 * experience) - 50) / 100;
    }

    private Integer calculateUntilNextLevel(Integer level, Integer experience) {
        return 50 * (level + 1) * (level + 2) - experience;
    }

    public Player createPlayer(Player player) {
        player.setLevel(calculateLevel(player.getExperience()));
        player.setUntilNextLevel(calculateUntilNextLevel(player.getLevel(), player.getExperience()));
        return playerRepo.save(player);
    }

    public Player updatePlayer(Long id, Player player) {
        if (!playerRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Player player1 = playerRepo.findById(id).get();
        if (player.getName() != null) {
            player1.setName(player.getName());
        }
        if (player.getTitle() != null) {
            player1.setTitle(player.getTitle());
        }
        if (player.getRace() != null) {
            player1.setRace(player.getRace());
        }
        if (player.getProfession() != null) {
            player1.setProfession(player.getProfession());
        }
        if (player.getBirthday() != null) {
            player1.setBirthday(player.getBirthday());
        }
        if (player.getBanned() != null) {
            player1.setBanned(player.getBanned());
        }
        if (player.getExperience() != null) {
            player1.setExperience(player.getExperience());
        }
        player1.setLevel(calculateLevel(player1.getExperience()));
        player1.setUntilNextLevel(calculateUntilNextLevel(player1.getLevel(), player1.getExperience()));
        return playerRepo.save(player1);
    }

    public void deletePlayer(Long id) {
        if (!playerRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        playerRepo.deleteById(id);
    }

    public Player getPlayer(Long id) {
        if (!playerRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return playerRepo.findById(id).get();
    }

    public List<Player> getPlayers(String name, String title, Race race, Profession profession, Long after, Long before, Boolean banned, Integer minExperience,
                                   Integer maxExperience, Integer minLevel, Integer maxLevel, PlayerOrder order, Integer pageNumber, Integer pageSize) {
        return playerRepo.findAll(name, title, race, profession, banned, minExperience, maxExperience, minLevel, maxLevel, after == null ? null : new Date(after), before == null ? null : new Date(before), PageRequest.of(pageNumber, pageSize, Sort.by(order.getFieldName())));
    }

    public int getPlayersCount(String name, String title, Race race, Profession profession, Long after, Long before, Boolean banned, Integer minExperience,
                               Integer maxExperience, Integer minLevel, Integer maxLevel) {
        return (int) playerRepo.count(name, title, race, profession, banned, minExperience, maxExperience, minLevel, maxLevel, after == null ? null : new Date(after), before == null ? null : new Date(before));
    }
}
