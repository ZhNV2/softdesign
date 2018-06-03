package ru.spbau.zhidkov.view.drawer;

import org.codetome.zircon.api.Position;

/**
 * Basic interface for classes drawing smth on screen
 */
public interface Drawer {
    void draw(Position startPosition);
}
