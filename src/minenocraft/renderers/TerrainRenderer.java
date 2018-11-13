/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minenocraft.renderers;

import com.sun.opengl.util.GLUT;
import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
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
        for (int i = 0; i < chunkSize; i++)
        {
            gl.glPushMatrix();
            gl.glTranslatef(0, 0, i * cubeSize);
            
            for (int j = 0; j < chunkSize; j++)
            {
                gl.glPushMatrix();  
                
                gl.glTranslatef(j * cubeSize, 0, 0);
                
                Grass.Instance().draw(gl);
                //drawCube(gl);

                gl.glPopMatrix();
            }
            
            gl.glPopMatrix();
            
        }

//        BufferedImage grass_side = null;
//        try
//        {
//            grass_side = ImageIO.read(new File("./textures/grass_side.png"));
//        }
//        catch (IOException ex)
//        {
//            Logger.getLogger(TerrainRenderer.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        Texture t = TextureIO.newTexture(grass_side, true);
//        
//        t.enable();
//        t.bind();
//        
//        gl.glBegin(GL.GL_QUADS);
//        
//            gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_NEAREST);
//        
//            gl.glVertex3d(-0.5, 0, -0.5);
//            gl.glTexCoord2d(0, 0);
//            
//            gl.glVertex3d(0.5, 0, -0.5);
//            gl.glTexCoord2d(0, 1);
//            
//            gl.glVertex3d(0.5, 0, 0.5);
//            gl.glTexCoord2d(1, 1);
//            
//            gl.glVertex3d(-0.5, 0, 0.5);
//            gl.glTexCoord2d(1, 0);
//            
//        
//        gl.glEnd();
//        glut.glutSolidCube(1);
    }

    void drawCube(GL gl)
    {
        gl.glBegin(GL.GL_QUADS);
        gl.glVertex3d(-halfCube, halfCube, halfCube);
        gl.glVertex3d(halfCube, halfCube, halfCube);
        gl.glVertex3d(halfCube, -halfCube, halfCube);
        gl.glVertex3d(-halfCube, -halfCube, halfCube);
        gl.glEnd();

        gl.glBegin(GL.GL_QUADS);
        gl.glVertex3d(-halfCube, halfCube, -halfCube);
        gl.glVertex3d(halfCube, halfCube, -halfCube);
        gl.glVertex3d(halfCube, -halfCube, -halfCube);
        gl.glVertex3d(-halfCube, -halfCube, -halfCube);
        gl.glEnd();

        gl.glBegin(GL.GL_QUADS);
        gl.glVertex3d(-halfCube, halfCube, -halfCube);
        gl.glVertex3d(halfCube, halfCube, -halfCube);
        gl.glVertex3d(halfCube, halfCube, halfCube);
        gl.glVertex3d(-halfCube, halfCube, halfCube);
        gl.glEnd();

        gl.glBegin(GL.GL_QUADS);
        gl.glVertex3d(-halfCube, -halfCube, -halfCube);
        gl.glVertex3d(halfCube, -halfCube, -halfCube);
        gl.glVertex3d(halfCube, -halfCube, halfCube);
        gl.glVertex3d(-halfCube, -halfCube, halfCube);
        gl.glEnd();

        gl.glBegin(GL.GL_QUADS);
        gl.glVertex3d(halfCube, halfCube, halfCube);
        gl.glVertex3d(halfCube, halfCube, -halfCube);
        gl.glVertex3d(halfCube, -halfCube, -halfCube);
        gl.glVertex3d(halfCube, -halfCube, halfCube);
        gl.glEnd();

        gl.glBegin(GL.GL_QUADS);
        gl.glVertex3d(-halfCube, halfCube, halfCube);
        gl.glVertex3d(-halfCube, halfCube, -halfCube);
        gl.glVertex3d(-halfCube, -halfCube, -halfCube);
        gl.glVertex3d(-halfCube, -halfCube, halfCube);
        gl.glEnd();
    }
}
