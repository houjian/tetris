package io.github.houjian.tetris.dao.impl;

import io.github.houjian.tetris.config.Constants;
import io.github.houjian.tetris.dao.PlayerDao;
import io.github.houjian.tetris.model.Player;
import io.github.houjian.tetris.util.IOUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author houjian
 */
@Slf4j
public class PlayerDaoImpl implements PlayerDao {

    private List<Player> records;

    @SuppressWarnings("unchecked")
    @Override
    public List<Player> load() {
        if (records == null) {
            Object object = IOUtils.readObject(Constants.PATH_PLAYER_SCORE_RECORD);
            if (object != null) {
                this.records = (List<Player>) object;
            } else {
                this.records = new ArrayList<>();
            }
        }

        return Collections.unmodifiableList(records);
    }

    @Override
    public void save(Player player) {
        if (records == null) {
            records = new ArrayList<>();
        }
        records.add(player);
        Collections.sort(records);

        // 如果大于5则删掉多余的
        if (records.size() > Constants.SCORE_RECORD_SHOW_SIZE) {
            records.subList(Constants.SCORE_RECORD_SHOW_SIZE, records.size()).clear();
        }

        IOUtils.writeObject(records, Constants.PATH_PLAYER_SCORE_RECORD);
    }
}
