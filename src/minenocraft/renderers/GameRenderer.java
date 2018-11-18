/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minenocraft.renderers;

import com.sun.javafx.geom.Vec3d;
import com.sun.opengl.util.Animator;
import com.sun.opengl.util.GLUT;
import java.awt.AWTException;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    private final Vec3d camPos = new Vec3d(0, 3, 0);
    private Vec3d camFront;
    private final Vec3d camUp = new Vec3d(0, 1, 0);

    private final ArrayList<IGameRendererProcessor> processors;

    private Robot robot;

    public GameRenderer()
    {
        glu = new GLU();
        glut = new GLUT();

        jFrame = new JFrame();

        try
        {
            robot = new Robot();
        }
        catch (AWTException ex)
        {
            Logger.getLogger(GameRenderer.class.getName()).log(Level.SEVERE, null, ex);
        }

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

            private boolean isRobot = false;
            private boolean firstMove = true;

            @Override
            public void mouseDragged(MouseEvent e)
            {
            }

            @Override
            public void mouseMoved(MouseEvent e)
            {
                if (isRobot)
                    return;

                isRobot = true;
                robot.mouseMove(jFrame.getX() + jFrame.getWidth() / 2, jFrame.getY() + jFrame.getHeight() / 2);
                isRobot = false;

                if (firstMove)
                {
                    firstMove = false;
                    lastX = jFrame.getWidth() / 2;
                    lastY = jFrame.getHeight() / 2;
                    return;
                }

                float mouseSensitivity = 1 / 3f;

                yaw += (e.getX() - lastX) * mouseSensitivity;
                pitch += (e.getY() - lastY) * mouseSensitivity * -1;

                if (pitch > 89.0f)
                    pitch = 89.0f;
                if (pitch < -89.0f)
                    pitch = -89.0f;

                lastX = jFrame.getWidth() / 2;
                lastY = jFrame.getHeight() / 2;
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
                    case 'W':
                        wPressed = true;
                        break;
                    case 'a':
                    case 'A':
                        aPressed = true;
                        break;
                    case 's':
                    case 'S':
                        sPressed = true;
                        break;
                    case 'd':
                    case 'D':
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
                    case 'W':
                        wPressed = false;
                        break;
                    case 'a':
                    case 'A':
                        aPressed = false;
                        break;
                    case 's':
                    case 'S':
                        sPressed = false;
                        break;
                    case 'd':
                    case 'D':
                        dPressed = false;
                        break;
                }
            }
        });

        gljPanel = new GLJPanel();

        processors = new ArrayList<>();

        gljPanel.addGLEventListener(this);
        //jFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        //jFrame.setUndecorated(true);
        jFrame.getContentPane().add(gljPanel);

        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
        jFrame.getContentPane().setCursor(blankCursor);

        jFrame.setSize(900, 600);

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
        float camSpeed = 0.2f;

        camFront = new Vec3d(
                Math.cos(Math.toRadians(pitch)) * Math.cos(Math.toRadians(yaw)),
                Math.sin(Math.toRadians(pitch)),
                Math.cos(Math.toRadians(pitch)) * Math.sin(Math.toRadians(yaw))
        );

        camFront.normalize();

//        Vec3d auxWS = new Vec3d(camFront);
//        auxWS.mul(camSpeed);
//        
//        Vec3d auxAD = new Vec3d();
//        auxAD.cross(camFront, camUp);
//        auxAD.mul(camSpeed);
//        if (wPressed)
//            camPos.add(auxWS);
//        if (sPressed)
//            camPos.sub(auxWS);
//
//        if (aPressed)
//            camPos.sub(auxAD);
//        if (dPressed)
//            camPos.add(auxAD);
        Vec3d movement = new Vec3d(camFront);
        movement.y = 0;
        movement.normalize();

        if (wPressed)
        {
            movement.mul(camSpeed);
            camPos.add(movement);
        }
        if (sPressed)
        {
            movement.mul(-camSpeed);
            camPos.add(movement);
        }
        if (aPressed)
        {
            movement.cross(movement, camUp);
            movement.mul(-camSpeed);
            camPos.add(movement);
        }
        if (dPressed)
        {
            movement.cross(movement, camUp);
            movement.mul(camSpeed);
            camPos.add(movement);
        }

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

        glu.gluPerspective(45, (float) w / h, 1, 200);
        gl.glMatrixMode(GL.GL_MODELVIEW);

        gl.glClearDepth(1);
        gl.glClearColor(0, 0, 0, 0);

        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glDepthFunc(GL.GL_LEQUAL);

        gl.glEnable(GL.GL_LIGHTING);
        gl.glEnable(GL.GL_LIGHT0);
        gl.glEnable(GL.GL_TEXTURE_2D);
        gl.glEnable(GL.GL_COLOR_MATERIAL);

        gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, new float[]
        {
            0, 1, 0
        }, 0);
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_AMBIENT, new float[]
        {
            1, 1, 1
        }, 0);

        gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);

        gl.glLoadIdentity();
    }

    @Override
    public void displayChanged(GLAutoDrawable glad, boolean bln, boolean bln1)
    {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
