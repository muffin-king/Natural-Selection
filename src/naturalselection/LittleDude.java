package naturalselection;

import java.awt.*;
import java.util.*;
import java.util.List;

public class LittleDude implements Drawable {
    private final Vector position, velocity;
    private final TraitMap traitMap;
    private final SimulatorPanel environ;
    private double age, energy, size;
    private Food goal;
    private static final Random RANDOM = new Random();
    private static final int MAX_AGE = 100;
    private Color color;
    private final String name;
    private int generation;
    private State state;

    private enum State {
        SEARCHING, STARVING, AGING;
        private static final String STARVATION = "starved";
        private static final String AGE = "aged";
        private static final String GENERIC = "died";
        private static String getDeathMessage(State state) {
            if(state == STARVING)
                return STARVATION;
            else if(state == AGING)
                return AGE;
            else
                return GENERIC;
        }
    }

    private class DeathMessage {
    }

    public LittleDude(double x, double y, SimulatorPanel environ) {
        position = new Vector(x, y);
        velocity = new Vector(0, 0);
        this.environ = environ;

        age = 0;
        this.energy = 500;
        this.size = 1;

        traitMap = new TraitMap();

        goal = null;

        color = Color.BLUE;

        name = Name.takeName();

        generation = 1;

        state = null;
    }

    public LittleDude(double x, double y, int energy, int size, SimulatorPanel environ, TraitMap traitMap, String name, int generation) {
        position = new Vector(x, y);
        velocity = new Vector(0, 0);
        this.environ = environ;

        age = 0;
        this.energy = energy;
        this.size = size;

        this.traitMap = traitMap;

        goal = null;

        color = Color.BLUE;

        this.name = name;

        this.generation = generation;

        state = null;
    }

    public Vector position() {
        return position;
    }

    public Vector velocity() {
        return velocity;
    }

    private boolean isWilling() {
        return 1/energy < getTrait(Trait.VIRILITY)*0.001 && size >= getTrait(Trait.MAX_SIZE);
    }

    private void reproduce() {
        LittleDude daughter1 = new LittleDude(position.x, position.y, (int) (energy/2), (int) (size/2), environ, traitMap.mutate(), name, generation+1);
        LittleDude daughter2 = new LittleDude(position.x, position.y, (int) (energy/2), (int) (size/2), environ, traitMap.mutate(), name, generation+1);
        environ.removeDrawable(this);
        environ.addDrawable(daughter1);
        environ.addDrawable(daughter2);
        System.out.println(name+" (gen "+generation+") reproduced");
    }

    public double getTrait(Trait trait) {
        return traitMap.getTrait(trait);
    }

    @Override
    public void update() {
        velocity.x *= 0.95;
        velocity.y *= 0.95;

        position.x += velocity.x;
        position.y += velocity.y;

        if(isWilling())
            reproduce();

        if(goal == null) {
            List<Food> foods = environ.getDrawablesType(Food.class);
            if(foods.size() > 0) {
                Food shortest = foods.get(0);
                for (Food food : foods) {
//                double chance = RANDOM.nextDouble(food.getPosition().distance(position));
//                if(chance < 10) {
//                    goal = food;
//                    break;
//                }
                    double distance = food.getPosition().distance(position);
                    if (distance < shortest.getPosition().distance(position))
                        shortest = food;
                }
                goal = shortest;

                state = State.SEARCHING;
            }
        } else {
            if(state == State.SEARCHING) {
                velocity.x -= Math.signum(position.x - goal.getPosition().x) * traitMap.getTrait(Trait.SPEED);
                velocity.y -= Math.signum(position.y - goal.getPosition().y) * traitMap.getTrait(Trait.SPEED);

                if (!environ.getDrawables().contains(goal))
                    goal = null;
            }
        }

        for(int i = 0; i < environ.getDrawables().size(); i++) {
            Drawable drawable = environ.getDrawables().get(i);
            if (drawable != this && getHitbox().intersects(drawable.getHitbox())) {
                if (drawable instanceof Food) {
                    environ.removeDrawable(drawable);
                    double gained = Food.FOOD_VALUE;
                    if (size <= traitMap.getTrait(Trait.MAX_SIZE)) {
                        energy += gained / 2;
                        size += gained / 2;
                        if(size > traitMap.getTrait(Trait.MAX_SIZE))
                            size = traitMap.getTrait(Trait.MAX_SIZE);
                    } else {
                        energy += gained;
                    }
                    color = Color.GREEN;
                }
            }
        }

        if(energy >= 0)
            energy -= traitMap.getTrait(Trait.SPEED)/2 + size/60;
        else
            state = State.STARVING;

        if(age < MAX_AGE)
            age += traitMap.getTrait(Trait.SPEED)/2
                - Math.min(traitMap.getTrait(Trait.SPEED)/2, traitMap.getTrait(Trait.MAX_SIZE)/1500);
        else
            state = State.AGING;

        if(state == State.STARVING || state == State.AGING) {
            size -= 1;
            color = Color.RED;
        }

        if(size <= 0) {
            die(state);
        }

        int red = (255 - color.getRed() > 0) ? 5 : 0;
        int green = (255 - color.getGreen() > 0) ? 5 : 0;
        int blue = (255 - color.getBlue() > 0) ? 5 : 0;
        color = new Color(color.getRed() + red, color.getGreen() + green, color.getBlue() + blue);
    }

    public void die(State state) {
        System.out.println(name+" (gen "+generation+") "+State.getDeathMessage(state));
        environ.removeDrawable(this);
    }

    @Override
    public Vector getPosition() {
        return position;
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(color);
        g2.drawOval((int) position.x, (int) position.y, (int) size, (int) size);

        Vector offsetMouse = environ.getMousePos().clone();
        offsetMouse.x -= environ.getCamOffset().x;
        offsetMouse.y -= environ.getCamOffset().y;

        if(getHitbox().contains(offsetMouse.getPoint())) {
            g2.setColor(Color.WHITE);
            int centerX = (int) (position.x+size/2);
            int centerY = (int) (position.y+size/2);
            g2.drawLine(centerX, centerY, centerX+20, centerY);

            g2.setFont(new Font("Lucida Console", Font.ITALIC+Font.BOLD, 10));
            g2.drawString(name + " (gen "+generation+")", centerX+25, centerY+10);

            g2.setFont(new Font("Lucida Console", Font.PLAIN, 10));
            g2.drawString("Position: ("+(int)position.x+", "+(int)position.y+")", centerX+25, centerY+20);
            g2.drawString("Velocity: ("+(int)position.x+", "+(int)position.y+")", centerX+25, centerY+30);
            g2.drawString("Max Size: "+traitMap.getTrait(Trait.MAX_SIZE), centerX+25, centerY+45);
            g2.drawString("Speed: "+traitMap.getTrait(Trait.SPEED), centerX+25, centerY+55);
            g2.drawString("Virility: "+traitMap.getTrait(Trait.VIRILITY), centerX+25, centerY+65);

            if(environ.isLeftMouseDown())
                environ.setFocused(this);
        }
    }

    @Override
    public Rectangle getHitbox() {
        return new Rectangle((int) position.x, (int) position.y, (int) size, (int) size);
    }
}
