package naturalselection;

import betterthreadpool.ScheduledThreadPool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class SimulatorPanel extends JPanel {
    private final List<Drawable> drawables;
    private final Timer timer;
    private final ScheduledThreadPool pool;
    private int tick;
    private static final Random RANDOM = new Random();
    private final Vector mousePos;
    private final Vector camOffset;
    private boolean leftMouseDown;
    private boolean rightMouseDown;
    private Drawable focused;
    private int simSpeed;
    public SimulatorPanel(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.BLACK);

        drawables = new ArrayList<>();

        tick = 0;

        timer = new Timer(1, new RepaintTask());
        timer.start();

        for(int i = 0; i < 5; i++) {
            LittleDude dude = new LittleDude(RANDOM.nextInt(width), RANDOM.nextInt(height), this);
            addDrawable(dude);
        }

        mousePos = new Vector(0, 0);
        leftMouseDown = false;
        rightMouseDown = false;
        Mouse mouseListener = new Mouse();
        addMouseListener(mouseListener);
        addMouseMotionListener(mouseListener);
        addMouseWheelListener(mouseListener);
        setFocusable(true);

        camOffset = new Vector(0, 0);
        focused = null;

        simSpeed = 1;
        pool = new ScheduledThreadPool(1);
        pool.scheduleTask(new UpdateTask(), 1, 10, TimeUnit.MILLISECONDS);
    }

    public Vector getMousePos() {
        return mousePos;
    }

    public boolean isLeftMouseDown() {
        return leftMouseDown;
    }

    public boolean isRightMouseDown() {
        return rightMouseDown;
    }

    public void setFocused(Drawable focused) {
        this.focused = focused;
    }

    public Vector getCamOffset() {
        return camOffset;
    }

    public void addDrawable(Drawable drawable) {
        drawables.add(drawable);
    }

    public void removeDrawable(Drawable drawable) {
        drawables.remove(drawable);
    }

    public List<Drawable> getDrawables() {
        return drawables;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        g2.setColor(Color.WHITE);

        g2.setFont(new Font("Lucida Console", Font.PLAIN, 12));

        List<LittleDude> dudes = getDrawablesType(LittleDude.class);

        double avgSpeed = 0;
        for(LittleDude dude : dudes)
            avgSpeed += dude.getTrait(Trait.SPEED);
        avgSpeed /= dudes.size();

        double avgSize = 0;
        for(LittleDude dude : dudes)
            avgSize += dude.getTrait(Trait.MAX_SIZE);
        avgSize /= dudes.size();

        double avgVirility = 0;
        for(LittleDude dude : dudes)
            avgVirility += dude.getTrait(Trait.VIRILITY);
        avgVirility /= dudes.size();

        g2.drawString("Avg. Speed: "+avgSpeed, 10, 15);
        g2.drawString("Avg. Max Size: "+avgSize, 10, 30);
        g2.drawString("Avg. Virility: "+avgVirility, 10, 45);
        g2.drawString("x"+simSpeed, 10, 60);

        g2.translate(camOffset.x, camOffset.y);

        for(int i = 0; i < drawables.size(); i++)
            drawables.get(i).draw(g2);
    }

    private void spawnFood() {
        addDrawable(new Food(RANDOM.nextInt(getWidth()), RANDOM.nextInt(getHeight())));
    }

    @SuppressWarnings("unchecked")
    public <E extends Drawable> List<E> getDrawablesType(Class<E> clazz) {
        List<E> list = new ArrayList<>();
        for(int i = 0; i < drawables.size(); i++) {
            Drawable drawable = drawables.get(i);
            if (drawable.getClass().equals(clazz))
                list.add((E) drawable);
        }
        return list;
    }

    private class RepaintTask implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            repaint();
        }
    }

    private class UpdateTask implements Runnable {
        @Override
        public void run() {
            for(int i = 0; i < simSpeed; i++)
                updateSim();
        }
    }

    private void updateSim() {
        tick ++;

        if(tick % 10 == 0)
            spawnFood();

        for(int i = 0; i < drawables.size(); i++) {
            drawables.get(i).update();
        }

        if (focused != null) {
            camOffset.x += ((focused.getPosition().x - (double)getWidth() / 2) / -1 - camOffset.x) / 1.5;
            camOffset.y += ((focused.getPosition().y - (double)getHeight() / 2) / -1 - camOffset.y) / 1.5;
        } else {
            camOffset.x += (0 - camOffset.x) / 1.5;
            camOffset.y += (0 - camOffset.y) / 1.5;
        }
        if(rightMouseDown || !drawables.contains(focused))
            focused = null;
    }

    private class Mouse implements MouseListener, MouseMotionListener, MouseWheelListener {
        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
            if(SwingUtilities.isLeftMouseButton(e))
                leftMouseDown = true;
            else if(SwingUtilities.isRightMouseButton(e))
                rightMouseDown = true;
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if(SwingUtilities.isLeftMouseButton(e))
                leftMouseDown = false;
            else if(SwingUtilities.isRightMouseButton(e))
                rightMouseDown = false;
        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

        @Override
        public void mouseDragged(MouseEvent e) {
            mousePos.setPoint(e.getPoint());
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            mousePos.setPoint(e.getPoint());
        }

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            simSpeed -= e.getPreciseWheelRotation();
            if(simSpeed < 0)
                simSpeed = 0;
        }
    }
}
