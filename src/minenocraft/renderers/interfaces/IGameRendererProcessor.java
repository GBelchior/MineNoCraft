/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minenocraft.renderers.interfaces;

import com.sun.opengl.util.GLUT;
import javax.media.opengl.GL;

/**
 *
 * @author gabri
 */
public interface IGameRendererProcessor
{
    void Process(GL gl, GLUT glut);
}
