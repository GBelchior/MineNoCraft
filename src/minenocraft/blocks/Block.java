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
public abstract class Block
{
    public static final float BLOCK_SIZE = 1f;
    public static final float HALF_BLOCK_SIZE = BLOCK_SIZE / 2;

    // 0 - Top
    // 1 - Bottom
    // 2 - Left
    // 3 - Right
    // 4 - Back
    // 5 - Front
    protected Texture[] textures;

    protected Block(String[] texturePaths)
    {
        textures = new Texture[6];

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
            t.setTexParameterf(GL.GL_TEXTURE_MIN_FILTER, GL.GL_NEAREST);
            t.setTexParameterf(GL.GL_TEXTURE_MAG_FILTER, GL.GL_NEAREST);
            
            textures[i] = t;
        }
    }

    public void draw(GL gl)
    {
        // Top
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[0].getTextureObject());
        gl.glBegin(GL.GL_QUADS);
            gl.glVertex3d(-HALF_BLOCK_SIZE, HALF_BLOCK_SIZE, -HALF_BLOCK_SIZE);
            gl.glTexCoord2d(0, 0);
            
            gl.glVertex3d(HALF_BLOCK_SIZE, HALF_BLOCK_SIZE, -HALF_BLOCK_SIZE);
            gl.glTexCoord2d(0, 1);
            
            gl.glVertex3d(HALF_BLOCK_SIZE, HALF_BLOCK_SIZE, HALF_BLOCK_SIZE);
            gl.glTexCoord2d(1, 1);
            
            gl.glVertex3d(-HALF_BLOCK_SIZE, HALF_BLOCK_SIZE, HALF_BLOCK_SIZE);
            gl.glTexCoord2d(1, 0);
        gl.glEnd();

        // Bottom
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[1].getTextureObject());
        gl.glBegin(GL.GL_QUADS);
            gl.glVertex3d(-HALF_BLOCK_SIZE, -HALF_BLOCK_SIZE, -HALF_BLOCK_SIZE);
            gl.glTexCoord2d(0, 0);
            
            gl.glVertex3d(HALF_BLOCK_SIZE, -HALF_BLOCK_SIZE, -HALF_BLOCK_SIZE);
            gl.glTexCoord2d(0, 1);
            
            gl.glVertex3d(HALF_BLOCK_SIZE, -HALF_BLOCK_SIZE, HALF_BLOCK_SIZE);
            gl.glTexCoord2d(1, 1);
            
            gl.glVertex3d(-HALF_BLOCK_SIZE, -HALF_BLOCK_SIZE, HALF_BLOCK_SIZE);
            gl.glTexCoord2d(1, 0);
        gl.glEnd();

        // Left
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[2].getTextureObject());
        gl.glBegin(GL.GL_QUADS);
            gl.glVertex3d(-HALF_BLOCK_SIZE, HALF_BLOCK_SIZE, HALF_BLOCK_SIZE);
            gl.glTexCoord2d(0, 0);
        
            gl.glVertex3d(-HALF_BLOCK_SIZE, HALF_BLOCK_SIZE, -HALF_BLOCK_SIZE);
            gl.glTexCoord2d(0, 1);
            
            gl.glVertex3d(-HALF_BLOCK_SIZE, -HALF_BLOCK_SIZE, -HALF_BLOCK_SIZE);
            gl.glTexCoord2d(1, 1);
            
            gl.glVertex3d(-HALF_BLOCK_SIZE, -HALF_BLOCK_SIZE, HALF_BLOCK_SIZE);
            gl.glTexCoord2d(1, 0);
        gl.glEnd();

        // Right
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[3].getTextureObject());
        gl.glBegin(GL.GL_QUADS);
            gl.glVertex3d(HALF_BLOCK_SIZE, HALF_BLOCK_SIZE, HALF_BLOCK_SIZE);
            gl.glTexCoord2d(0, 0);
            
            gl.glVertex3d(HALF_BLOCK_SIZE, HALF_BLOCK_SIZE, -HALF_BLOCK_SIZE);
            gl.glTexCoord2d(0, 1);
            
            gl.glVertex3d(HALF_BLOCK_SIZE, -HALF_BLOCK_SIZE, -HALF_BLOCK_SIZE);
            gl.glTexCoord2d(1, 1);
            
            gl.glVertex3d(HALF_BLOCK_SIZE, -HALF_BLOCK_SIZE, HALF_BLOCK_SIZE);
            gl.glTexCoord2d(1, 0);
        gl.glEnd();
        
        // Back
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[4].getTextureObject());
        gl.glBegin(GL.GL_QUADS);
            gl.glVertex3d(-HALF_BLOCK_SIZE, HALF_BLOCK_SIZE, -HALF_BLOCK_SIZE);
            gl.glTexCoord2d(0, 0);
            
            gl.glVertex3d(HALF_BLOCK_SIZE, HALF_BLOCK_SIZE, -HALF_BLOCK_SIZE);
            gl.glTexCoord2d(0, 1);
            
            gl.glVertex3d(HALF_BLOCK_SIZE, -HALF_BLOCK_SIZE, -HALF_BLOCK_SIZE);
            gl.glTexCoord2d(1, 1);
            
            gl.glVertex3d(-HALF_BLOCK_SIZE, -HALF_BLOCK_SIZE, -HALF_BLOCK_SIZE);
            gl.glTexCoord2d(1, 0);
        gl.glEnd();
        
        // Front
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[5].getTextureObject());
        gl.glBegin(GL.GL_QUADS);
            gl.glVertex3d(-HALF_BLOCK_SIZE, HALF_BLOCK_SIZE, HALF_BLOCK_SIZE);
            gl.glTexCoord2d(0, 0);
            
            gl.glVertex3d(HALF_BLOCK_SIZE, HALF_BLOCK_SIZE, HALF_BLOCK_SIZE);
            gl.glTexCoord2d(0, 1);
            
            gl.glVertex3d(HALF_BLOCK_SIZE, -HALF_BLOCK_SIZE, HALF_BLOCK_SIZE);
            gl.glTexCoord2d(1, 1);
            
            gl.glVertex3d(-HALF_BLOCK_SIZE, -HALF_BLOCK_SIZE, HALF_BLOCK_SIZE);
            gl.glTexCoord2d(1, 0);
        gl.glEnd();
    }
}
