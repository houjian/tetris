package io.github.houjian.tetris.dao;

import io.github.houjian.tetris.model.Player;

import java.util.List;

/**
 * 玩家信息访问
 *
 * @author houjian
 */
public interface PlayerDao {

    /**
     * 获取得分最高几条玩家数据
     *
     * @return 玩家记录数据
     */
    List<Player> load();

    /**
     * 保存玩家数据
     *
     * @param player 玩家得分数据
     */
    void save(Player player);
}
