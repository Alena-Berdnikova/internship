package com.game.controller;

import com.game.entity.*;
import com.game.entity.Validation.CreateValidationGroup;
import com.game.entity.Validation.PlayerValidator;
import com.game.entity.Validation.UpdateValidationGroup;
import com.game.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/rest/players")
public class PlayerController {
    @Autowired
    private PlayerService playerService;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(new PlayerValidator());
    }

    public Long checkConvertId(String id) {
        if (!id.matches("\\d+")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        long number;
        try {
            number = Long.parseLong(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (number <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return number;
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody List<Player> getPlayers(@RequestParam(required = false) String name, @RequestParam(required = false) String title,
                                                 @RequestParam(required = false) Race race, @RequestParam(required = false) Profession profession,
                                                 @RequestParam(required = false) Long after, @RequestParam(required = false) Long before,
                                                 @RequestParam(required = false) Boolean banned, @RequestParam(required = false) Integer minExperience,
                                                 @RequestParam(required = false) Integer maxExperience, @RequestParam(required = false) Integer minLevel,
                                                 @RequestParam(required = false) Integer maxLevel, @RequestParam(required = false) Optional<PlayerOrder> order,
                                                 @RequestParam(required = false, defaultValue = "0") Integer pageNumber, @RequestParam(required = false, defaultValue = "3") Integer pageSize) {
        return playerService.getPlayers(name, title, race, profession, after, before, banned, minExperience,
                maxExperience, minLevel, maxLevel, order.orElse(PlayerOrder.ID), pageNumber, pageSize);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public @ResponseBody void deletePlayer(@PathVariable String id) {
        long idNumber = checkConvertId(id);
        playerService.deletePlayer(idNumber);
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public @ResponseBody int getPlayersCount(@RequestParam(required = false) String name, @RequestParam(required = false) String title,
                                             @RequestParam(required = false) Race race, @RequestParam(required = false) Profession profession,
                                             @RequestParam(required = false) Long after, @RequestParam(required = false) Long before,
                                             @RequestParam(required = false) Boolean banned, @RequestParam(required = false) Integer minExperience,
                                             @RequestParam(required = false) Integer maxExperience, @RequestParam(required = false) Integer minLevel,
                                             @RequestParam(required = false) Integer maxLevel) {
        return playerService.getPlayersCount(name, title, race, profession, after, before, banned, minExperience,
                maxExperience, minLevel, maxLevel);
    }

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody Player createPlayer(@RequestBody @Validated(CreateValidationGroup.class) Player player) {
        return playerService.createPlayer(player);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{id}")
    public @ResponseBody Player updatePlayer(@PathVariable String id, @RequestBody @Validated(UpdateValidationGroup.class) Player player) {
        long idNumber = checkConvertId(id);
        return playerService.updatePlayer(idNumber, player);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public @ResponseBody Player getPlayer(@PathVariable String id) {
        long idNumber = checkConvertId(id);
        return playerService.getPlayer(idNumber);
    }
}
