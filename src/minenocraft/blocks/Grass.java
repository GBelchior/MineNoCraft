/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minenocraft.blocks;

/**
 *
 * @author gabri
 */
public class Grass extends Block
{
    private static Grass instance;

    private Grass()
    {
        super(new String[]
        {
            "./textures/grass_top.png",
            "./textures/dirt.png",
            "./textures/grass_side.png",
            "./textures/grass_side.png",
            "./textures/grass_side.png",
            "./textures/grass_side.png",
        });
    }

    public static Grass instance()
    {
        if (instance == null)
        {
            instance = new Grass();
        }

        return instance;
    }
}
