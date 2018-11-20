/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minenocraft.renderers;

import com.sun.opengl.util.GLUT;
import java.util.HashMap;
import javax.media.opengl.GL;
import minenocraft.blocks.Block;
import minenocraft.blocks.Grass;
import minenocraft.renderers.interfaces.IGameRendererProcessor;
import minenocraft.utils.MapKeyTest;
import minenocraft.utils.OpenSimplexNoise;

/**
 *
 * @author gabri
 */
public class TerrainRenderer implements IGameRendererProcessor
{
    private final int chunkSize = 16;

    private long seed = 19112018;
    private OpenSimplexNoise noiseGenerator = new OpenSimplexNoise(seed);

    private HashMap<MapKeyTest, Integer> blocks = new HashMap<>();

    @Override
    public void Process(GL gl, GLUT glut)
    {
        // ruler
        for (int i = 0; i < 256; i++)
        {
            String iText = Integer.toString(i);

            gl.glPushMatrix();
            gl.glTranslatef(0, Block.BLOCK_SIZE * i, 0);
            gl.glScalef(0.005f, 0.005f, 0.005f);

            for (int s = 0; s < iText.length(); s++)
            {
                glut.glutStrokeCharacter(GLUT.STROKE_ROMAN, iText.charAt(s));
                gl.glTranslatef(0, 0, Block.BLOCK_SIZE / 3);
            }

            gl.glPopMatrix();
        }

        int chunksSquareSize = 4;
        int chunksToRender = chunksSquareSize * chunksSquareSize;
        for (int c = 0; c < chunksToRender; c++)
        {
            int translationX = chunkSize * (c / chunksSquareSize);
            int translationZ = chunkSize * (c % chunksSquareSize);

            gl.glPushMatrix();
            gl.glTranslatef(translationX, 0, translationZ);

            for (int x = 0; x < chunkSize; x++)
            {
                gl.glPushMatrix();
                gl.glTranslatef(x * Block.BLOCK_SIZE, 0, 0);

                for (int z = 0; z < chunkSize; z++)
                {
                    gl.glPushMatrix();

                    gl.glTranslatef(0, getBlockY(x + translationX, z + translationZ), z * Block.BLOCK_SIZE);

                    Grass.instance().draw(gl);
                    gl.glPopMatrix();
                }

                gl.glPopMatrix();

            }

            gl.glPopMatrix();
        }
    }

    float noiseScale = 50;
    private int getBlockY(int x, int z)
    {
        MapKeyTest key = new MapKeyTest(x, z);
        if (blocks.containsKey(key))
        {
            return blocks.get(key);
        }
        
        System.out.println("key miss");

        double noise = noiseGenerator.eval(x / noiseScale, z / noiseScale);
        int res = (int) ((noise + 1) * 16) + (63 - 16);
        blocks.put(key, res);
        
        return res;
    }
}
