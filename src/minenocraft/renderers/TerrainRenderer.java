/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minenocraft.renderers;

import com.sun.opengl.util.GLUT;
import com.sun.opengl.util.texture.TextureIO;
import javax.media.opengl.GL;
import minenocraft.renderers.interfaces.IGameRendererProcessor;

/**
 *
 * @author gabri
 */
public class TerrainRenderer implements IGameRendererProcessor
{
    @Override
    public void Process(GL gl, GLUT glut)
    {
        gl.glColor3f(1, 1, 1);
        
        //TextureIO.newTexture(bi, true)
        
        glut.glutSolidCube(1);
    }
    
}
