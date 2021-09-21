package io.github.houjian.tetris.dao;

import io.github.houjian.tetris.dao.impl.PlayerDaoImpl;
import io.github.houjian.tetris.model.Player;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author houjian
 */
public class PlayerScoreRecordDaoTest {

    private final PlayerDao dao = new PlayerDaoImpl();

    @Test
    public void saveAndLoad() {
        for (int i = 0; i < 10; i++) {
            dao.save(Player.builder()
                    .score(100 * i)
                    .time(System.currentTimeMillis())
                    .build());
        }

        List<Player> players = dao.load();
        assertEquals(5, players.size());
    }
}