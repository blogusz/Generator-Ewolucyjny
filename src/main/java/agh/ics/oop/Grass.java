package agh.ics.oop;

public class Grass
{
    private final Vector2d position;
    int grassEnergy;

    public Grass(Vector2d position, int grassEnergy)
    {
        this.position=position;
        this.grassEnergy=grassEnergy;
    }

    public Vector2d getPosition()
    {
        return this.position;
    }

    @Override
    public String toString()
    {
        return "*";
    }
}