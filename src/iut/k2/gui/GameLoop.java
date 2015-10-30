package iut.k2.gui;

import iut.k2.Constants;
import iut.k2.data.Level;
import iut.k2.data.LevelTest;
import iut.k2.data.WorldControler;
import iut.k2.data.WorldRenderer;
import iut.k2.data.objects.Montain;
import iut.k2.data.objects.TestObject;
import iut.k2.physics.Coordinate2D;
import iut.k2.util.loggin.UtilLog;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

/**
 * Created by Nicolas Beaussart on 13/10/15 for angryBrids.
 */
public class GameLoop extends JFrame {
    public static void main(String[] args) {
        Level l = new Level() {
            @Override
            public void init() {
                for (int i = 0; i < 9000; i++) {
                    addRenderObject(new TestObject(new Coordinate2D(200, 200)), 0);
                }
                addRenderObject(new TestObject(new Coordinate2D(50, 50), false));
                addRenderObject(new TestObject(new Coordinate2D(20, 20), false));
                addRenderObject(new TestObject(new Coordinate2D(10, 10), false));
                addRenderObject(new TestObject(new Coordinate2D(1, 1), false));
                addRenderObject(new Montain("sprites/mountain_left.png", new Coordinate2D(-20, -60), 200, 200, 200), -1);
                addRenderObject(new Montain("sprites/mountain_right.png", new Coordinate2D(-90, -30), 150, 150, 150), -2);
                addRenderObject(new Montain("sprites/mountain_left.png", new Coordinate2D(-60, -15), 100, 100, 100), -3);
            }
        };
        UtilLog.setLevelGlobal(java.util.logging.Level.ALL, WorldControler.class.getName());
        UtilLog.setLevelGlobal(java.util.logging.Level.ALL, GameLoop.class.getName());
        WorldControler worldControler = new WorldControler(l);
        worldControler = new WorldControler(new LevelTest());
        new GameLoop(worldControler);
        new GameLoop(worldControler);

        new Thread(worldControler).start();

    }
    /**
     * The stragey that allows us to use accelerate page flipping
     */
    private BufferStrategy strategy;
    private boolean gameRunning = true;

    public GameLoop(WorldControler worldControler) {

        addKeyListener(worldControler.getKeyMap());

        setVisible(true);
        setSize(new Dimension(Constants.SIZE_WIDE + 20, Constants.SIZE_HEIGHT + 20));
        setLocationRelativeTo(null);

        // create the buffering strategy which will allow AWT
        // to manage our accelerated graphics
        createBufferStrategy(3);
        strategy = getBufferStrategy();
        new WorldRenderer(strategy, worldControler);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public BufferStrategy getStrategy() {
        return strategy;
    }


}