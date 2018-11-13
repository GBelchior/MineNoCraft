/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minenocraft.renderers;

import com.sun.javafx.geom.Vec3d;
import com.sun.opengl.util.Animator;
import com.sun.opengl.util.GLUT;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLJPanel;
import javax.media.opengl.glu.GLU;
import javax.swing.JFrame;
import minenocraft.renderers.interfaces.IGameRendererProcessor;

/**
 *
 * @author gabri
 */
public class GameRenderer implements GLEventListener
{
    private final GLU glu;
    private final GLUT glut;

    private final JFrame jFrame;
    private final GLJPanel gljPanel;

    private float yaw = 0;
    private float pitch = 0;

    private boolean wPressed = false;
    private boolean aPressed = false;
    private boolean sPressed = false;
    private boolean dPressed = false;

    float x = 0;
    float z = 0;

    Vec3d camPos = new Vec3d(-3, 3, 0);
    Vec3d camFront = new Vec3d(0, 0, -1);
    Vec3d camUp = new Vec3d(0, 1, 0);

    private final ArrayList<IGameRendererProcessor> processors;

    public GameRenderer()
    {
        glu = new GLU();
        glut = new GLUT();

        jFrame = new JFrame();

        jFrame.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                new Thread(() ->
                {
                    System.exit(0);
                }).start();
            }
        });

        jFrame.addMouseMotionListener(new MouseMotionListener()
        {
            private float lastX = 0;
            private float lastY = 0;

            @Override
            public void mouseDragged(MouseEvent e)
            {
            }

            @Override
            public void mouseMoved(MouseEvent e)
            {
                yaw += (e.getX() - lastX) / 2;
                pitch += (e.getY() - lastY) / 2;

                if (pitch > 89.0f)
                    pitch = 89.0f;
                if (pitch < -89.0f)
                    pitch = -89.0f;

                lastX = e.getX();
                lastY = e.getY();
            }
        });

        jFrame.addKeyListener(new KeyListener()
        {

            @Override
            public void keyTyped(KeyEvent e)
            {
                switch (e.getKeyChar())
                {
                    case 'w':
                        wPressed = true;
                        break;
                    case 'a':
                        aPressed = true;
                        break;
                    case 's':
                        sPressed = true;
                        break;
                    case 'd':
                        dPressed = true;
                        break;
                }
            }

            @Override
            public void keyPressed(KeyEvent e)
            {
            }

            @Override
            public void keyReleased(KeyEvent e)
            {
                switch (e.getKeyChar())
                {
                    case 'w':
                        wPressed = false;
                        break;
                    case 'a':
                        aPressed = false;
                        break;
                    case 's':
                        sPressed = false;
                        break;
                    case 'd':
                        dPressed = false;
                        break;
                }
            }
        });

        gljPanel = new GLJPanel();

        processors = new ArrayList<>();

        gljPanel.addGLEventListener(this);
        jFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        jFrame.setUndecorated(true);
        jFrame.getContentPane().add(gljPanel);
        jFrame.setVisible(true);
    }

    public void addProcessor(IGameRendererProcessor processor)
    {
        processors.add(processor);
    }

    @Override
    public void init(GLAutoDrawable glad)
    {
        Animator animator = new Animator(glad);
        animator.start();
    }

    private void processCamera(GLAutoDrawable glad)
    {
        float camSpeed = 0.1f;

        camFront = new Vec3d(
            Math.cos(Math.toRadians(pitch)) * Math.cos(Math.toRadians(yaw)),
            Math.sin(Math.toRadians(pitch)),
            Math.cos(Math.toRadians(pitch)) * Math.sin(Math.toRadians(yaw))
        );

        camFront.normalize();

        Vec3d auxWS = new Vec3d(camFront);
        auxWS.mul(camSpeed);

        Vec3d auxAD = new Vec3d();
        auxAD.cross(camFront, camUp);
        auxAD.mul(camSpeed);
        
        if (wPressed)
            camPos.add(auxWS);
        if (sPressed)
            camPos.sub(auxWS);

        if (aPressed)
            camPos.sub(auxAD);
        if (dPressed)
            camPos.add(auxAD);

        Vec3d auxFront = new Vec3d(camPos);
        auxFront.add(camFront);

        glu.gluLookAt(
            camPos.x, camPos.y, camPos.z,
            auxFront.x, auxFront.y, auxFront.z,
            camUp.x, camUp.y, camUp.z
        );
    }

    @Override
    public void display(GLAutoDrawable glad)
    {
        GL gl = glad.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

        gl.glPushMatrix();

        processCamera(glad);

        processors.forEach(p ->
        {
            gl.glPushMatrix();

            p.Process(gl, glut);

            gl.glPopMatrix();
        });

        gl.glPopMatrix();
    }

    @Override
    public void reshape(GLAutoDrawable glad, int x, int y, int w, int h)
    {
        GL gl = glad.getGL();
        
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        
        glu.gluPerspective(45, (float) w / h, 1, 200);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glTranslated(0, 0, -10);

        gl.glClearDepth(1);
        gl.glClearColor(0, 0, 0, 0);
        
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glDepthFunc(GL.GL_LEQUAL);

        gl.glEnable(GL.GL_LIGHTING);
        gl.glEnable(GL.GL_LIGHT0);
        gl.glEnable(GL.GL_TEXTURE_2D);
        gl.glEnable(GL.GL_COLOR_MATERIAL);
        
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, new float[] { 0, 1, 0 }, 0);
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_AMBIENT, new float[] { 1, 1, 1 }, 0);

        gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
    }

    @Override
    public void displayChanged(GLAutoDrawable glad, boolean bln, boolean bln1)
    {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
