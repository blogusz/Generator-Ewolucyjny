package agh.ics.oop;

/**
 *Bogusz Laszczyk
 */
public class Tracker
{
    private final Simulation simulation;
    public Animal trackedAnimal;

    private int deathDate;
    public Tracker(Simulation simulation)
    {
        this.simulation=simulation;
    }
    public void startTracing(Animal trackedAnimal)
    {
        this.trackedAnimal=trackedAnimal;
    }
    public boolean isBeingTracked()
    {
        return true; //zdecydowanie powinno to inaczej wygladac
    }
    public Genotype getGenotype()
    {
        return this.trackedAnimal.getGenotype();
    }
    public int currentGen()
    {
        return this.trackedAnimal.genotype.nextGen;
    }
    public int currentEnergy()
    {
        return this.trackedAnimal.getEnergy();
    }
    public int howMuchEaten()
    {
        return this.trackedAnimal.howMuchGrassEaten;
    }
    public int howManyChildren()
    {
        return this.trackedAnimal.numberOfChildren;
    }
    public int howOld()
    {
        return this.trackedAnimal.age;
    }
    public void setDeathDate(int round)
    {
        this.deathDate=simulation.rounds;
    }



}
