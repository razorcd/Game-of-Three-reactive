package com.challenge.reactive.gameofthree.game.domain;

import com.challenge.reactive.gameofthree.model.Human;
import com.challenge.reactive.gameofthree.model.IPlayer;
import com.challenge.reactive.gameofthree.util.PropertiesConfigLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class PlayerAggregate implements Iterator<PlayerAggregate>{

    private static final int DEFAULT_ROOT_INDEX = 0;

    private static final int PLAYER_COUNT = 2;

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerAggregate.class);

    public static final PlayerAggregate NULL = new PlayerAggregate(Collections.emptyList(), DEFAULT_ROOT_INDEX);

    private final List<IPlayer> players;
    private final int rootIndex;

    /**
     * Create a new players aggregate with a root player a current player.
     * Functions like an iterator by providing the same list if players and always setting next root player in circular way.
     *
     * @param players list of available players.
     * @param rootIndex the index of the root player from the players list representing current player.
     */
    public PlayerAggregate(List<IPlayer> players, int rootIndex) {
        this.players = Collections.unmodifiableList(players);
        this.rootIndex = rootIndex;
    }

    /**
     * Get root player of aggregate.
     *
     * @return [Player] root player.
     */
    public IPlayer getRootPlayer() {
        return Optional.of(rootIndex)
                .filter(this::isValidRootIndex)
                .map(players::get)
                .orElse(Human.NULL);
    }

    /**
     * Add new player. Does not mutate current object.
     * Returns a copy of current player aggregate by adding the new player to it.
     *
     * @param player the new player to add
     * @return [PlayerAggregate] new player aggregate with added specified player.
     */
    public PlayerAggregate addPlayer(final IPlayer player) {
        List<IPlayer> newList = new ArrayList<>(players);
        newList.add(player);
        return new PlayerAggregate(Collections.unmodifiableList(newList), rootIndex);
    }

    public PlayerAggregate removePlayer(IPlayer player) {
        List<IPlayer> newList = players.stream()
                .filter(p -> !p.isSame(player))
                .collect(Collectors.toList());
        return new PlayerAggregate(Collections.unmodifiableList(newList), rootIndex);
    }

    /**
     * Get new aggregate with root as next player.
     *
     * @return [PlayerAggregate] new aggregate with root as next player.
     */
    public PlayerAggregate next() {
        return new PlayerAggregate(players, getNextRootIndex());
    }

    /**
     * This is cyclic iterator, always has next.
     *
     * @return {@code true}
     */
    @Override
    public boolean hasNext() {
        return true;
    }

    /**
     * Check if current aggregate root is valid.
     *
     * @return [boolean] if current aggregate is valid.
     */
    public boolean isValid() {
        return players.size() == PLAYER_COUNT &&
                isValidRootIndex(rootIndex);
    }

    /**
     * Check if current aggregate accepts more players.
     *
     * @return [boolean] if aggregate accepts more players.
     */
    public boolean acceptsMorePlayers() {
        return players.size() < PLAYER_COUNT;
    }

    /**
     * Check if player already exists in this aggregation.
     *
     * @param player the player to look for
     * @return [boolean] if player already exists in this aggregate.
     */
    public boolean hasPlayer(IPlayer player) {
        return players.stream()
                .anyMatch(p -> p.isSame(player));
    }

    /**
     * Counts the index of the next root in a circular way.
     *
     * @return next index of root
     */
    private int getNextRootIndex() {
        try {
            return (rootIndex + 1) % players.size();
        } catch(ArithmeticException e) {
            LOGGER.error("Can not divide by zero.");
        }
        return -1;
    }

    private boolean isValidRootIndex(int index) {
        return index >= 0 &&
               index < players.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerAggregate that = (PlayerAggregate) o;
        return rootIndex == that.rootIndex &&
                Objects.equals(players, that.players);
    }

    @Override
    public int hashCode() {
        return Objects.hash(players, rootIndex);
    }

    @Override
    public String toString() {
        return new StringBuffer("players: ")
                .append(players)
                .append(" and player ")
                .append(rootIndex+1)
                .append(" has next turn.")
                .toString();
    }
}
