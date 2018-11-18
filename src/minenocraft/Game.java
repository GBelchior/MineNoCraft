/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minenocraft;

import minenocraft.renderers.GameRenderer;
import minenocraft.renderers.TerrainRenderer;

/**
 *
 * @author gabri
 */
public class Game
{
    private Thread gameLoopThread;
    private GameRenderer gameRenderer;
    private boolean running;

    public Game()
    {
        start();
    }

    private void start()
    {
//        gameLoopThread = new Thread(() ->
//        {
//            while (running)
//            {
//                update();
//            }
//        });
        gameRenderer = new GameRenderer();

        gameRenderer.addProcessor(new TerrainRenderer());

        // running = true;
        //gameLoopThread.start();
    }

    private void update()
    {

    }
}
