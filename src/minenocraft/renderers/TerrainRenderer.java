/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minenocraft.renderers;

import com.sun.opengl.util.GLUT;
import javax.media.opengl.GL;
import minenocraft.blocks.Grass;
import minenocraft.renderers.interfaces.IGameRendererProcessor;

/**
 *
 * @author gabri
 */
public class TerrainRenderer implements IGameRendererProcessor
{
    private final float cubeSize = 1f;
    private final float halfCube = cubeSize / 2;

    private final int chunkSize = 16;

    @Override
    public void Process(GL gl, GLUT glut)
    {
        for (int c = 0; c < 4; c++)
        {
            gl.glPushMatrix();
            gl.glTranslatef(0, cubeSize * c, chunkSize * c * cubeSize);

            for (int i = 0; i < chunkSize; i++)
            {
                gl.glPushMatrix();
                gl.glTranslatef(0, 0, i * cubeSize);

                for (int j = 0; j < chunkSize; j++)
                {
                    gl.glPushMatrix();

                    gl.glTranslatef(j * cubeSize, 0, 0);

                    Grass.instance().draw(gl);

                    gl.glPopMatrix();
                }

                gl.glPopMatrix();

            }
            gl.glPopMatrix();
        }
    }
}
