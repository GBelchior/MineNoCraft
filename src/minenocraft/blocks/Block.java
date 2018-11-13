/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minenocraft.blocks;

import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.media.opengl.GL;

/**
 *
 * @author gabri
 */
class Block
{
    private final float cubeSize = 1f;
    private final float halfCubeSize = cubeSize / 2;
    
    protected boolean transparent;

    // 0 - Top
    // 1 - Bottom
    // 2 - Left
    // 3 - Right
    // 4 - Back
    // 5 - Front
    protected Texture[] textures;

    protected Block(String[] texturePaths, boolean transparent)
    {
        textures = new Texture[6];
        this.transparent = transparent;
        
        for (int i = 0; i < 6; i++)
        {
            BufferedImage textureImg = null;
            try
            {
                textureImg = ImageIO.read(new File(texturePaths[i]));
            }
            catch (IOException ex)
            {
                Logger.getLogger(Block.class.getName()).log(Level.SEVERE, null, ex);
            }

            Texture t = TextureIO.newTexture(textureImg, true);
            //t.enable();

            textures[i] = t;
        }
    }

    public boolean isTransparent()
    {
        return transparent;
    }

    public void draw(GL gl)
    {
        // Top
        
        gl.glBegin(GL.GL_QUADS);
            textures[0].bind();
            gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_NEAREST);
            gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_NEAREST);
            
            gl.glVertex3d(-halfCubeSize, halfCubeSize, -halfCubeSize);
            gl.glTexCoord2d(0, 0);
            
            gl.glVertex3d(halfCubeSize, halfCubeSize, -halfCubeSize);
            gl.glTexCoord2d(0, 1);
            
            gl.glVertex3d(halfCubeSize, halfCubeSize, halfCubeSize);
            gl.glTexCoord2d(1, 1);
            
            gl.glVertex3d(-halfCubeSize, halfCubeSize, halfCubeSize);
            gl.glTexCoord2d(1, 0);
        gl.glEnd();

        // Bottom
        gl.glBegin(GL.GL_QUADS);
            textures[1].bind();
            gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_NEAREST);
            gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_NEAREST);
            
            gl.glVertex3d(-halfCubeSize, -halfCubeSize, -halfCubeSize);
            gl.glTexCoord2d(0, 0);
            
            gl.glVertex3d(halfCubeSize, -halfCubeSize, -halfCubeSize);
            gl.glTexCoord2d(0, 1);
            
            gl.glVertex3d(halfCubeSize, -halfCubeSize, halfCubeSize);
            gl.glTexCoord2d(1, 1);
            
            gl.glVertex3d(-halfCubeSize, -halfCubeSize, halfCubeSize);
            gl.glTexCoord2d(1, 0);
        gl.glEnd();

        // Left
        gl.glBegin(GL.GL_QUADS);
            textures[2].bind();
            gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_NEAREST);
            gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_NEAREST);
            
            gl.glVertex3d(-halfCubeSize, halfCubeSize, halfCubeSize);
            gl.glTexCoord2d(0, 0);
            
            gl.glVertex3d(-halfCubeSize, halfCubeSize, -halfCubeSize);
            gl.glTexCoord2d(0, 1);
            
            gl.glVertex3d(-halfCubeSize, -halfCubeSize, -halfCubeSize);
            gl.glTexCoord2d(1, 1);
            
            gl.glVertex3d(-halfCubeSize, -halfCubeSize, halfCubeSize);
            gl.glTexCoord2d(1, 0);
        gl.glEnd();

        // Right
        gl.glBegin(GL.GL_QUADS);
            textures[3].bind();
            gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_NEAREST);
            gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_NEAREST);
            
            gl.glVertex3d(halfCubeSize, halfCubeSize, halfCubeSize);
            gl.glTexCoord2d(0, 0);
            
            gl.glVertex3d(halfCubeSize, halfCubeSize, -halfCubeSize);
            gl.glTexCoord2d(0, 1);
            
            gl.glVertex3d(halfCubeSize, -halfCubeSize, -halfCubeSize);
            gl.glTexCoord2d(1, 1);
            
            gl.glVertex3d(halfCubeSize, -halfCubeSize, halfCubeSize);
            gl.glTexCoord2d(1, 0);
        gl.glEnd();
        
        // Back
        gl.glBegin(GL.GL_QUADS);
            textures[4].bind();
            gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_NEAREST);
            gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_NEAREST);
            
            gl.glVertex3d(-halfCubeSize, halfCubeSize, -halfCubeSize);
            gl.glTexCoord2d(0, 0);
            
            gl.glVertex3d(halfCubeSize, halfCubeSize, -halfCubeSize);
            gl.glTexCoord2d(0, 1);
            
            gl.glVertex3d(halfCubeSize, -halfCubeSize, -halfCubeSize);
            gl.glTexCoord2d(1, 1);
            
            gl.glVertex3d(-halfCubeSize, -halfCubeSize, -halfCubeSize);
            gl.glTexCoord2d(1, 0);
        gl.glEnd();
        
        // Front
        gl.glBegin(GL.GL_QUADS);
            textures[5].bind();
            gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_NEAREST);
            gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_NEAREST);
            
            gl.glVertex3d(-halfCubeSize, halfCubeSize, halfCubeSize);
            gl.glTexCoord2d(0, 0);
            
            gl.glVertex3d(halfCubeSize, halfCubeSize, halfCubeSize);
            gl.glTexCoord2d(0, 1);
            
            gl.glVertex3d(halfCubeSize, -halfCubeSize, halfCubeSize);
            gl.glTexCoord2d(1, 1);
            
            gl.glVertex3d(-halfCubeSize, -halfCubeSize, halfCubeSize);
            gl.glTexCoord2d(1, 0);
        gl.glEnd();
    }
}
