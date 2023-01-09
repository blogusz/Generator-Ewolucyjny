package agh.ics.oop;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class Animal
{
    public int breedEnergyCost;
    public int energyToBreed;
    public int genotypeLength;
    public String mutationVariant;
    public String animalsBehavior;
    MapDirection direction=MapDirection.NORTH;
    private Vector2d position;
    private final IWorldMap map;
    private final ArrayList<IPositionChangeObserver> observers = new ArrayList<>();
    int energy;
    int howMuchGrassEaten =0;
    public int numberOfChildren=0;
    int age=0;
    public Genotype genotype;
    public boolean isAlive =true;



    /**
     *Bogusz Laszczyk
     */
    public Animal(IWorldMap map, Vector2d initialPosition, int energy, int breedEnergyCost, int energyToBreed, int genotypeLength,String mutationVariant, String animalsBehavior) // tworzenie na początku symulacji
    {
        this.map = map;
        this.position = initialPosition;
        this.energy = energy;
        this.breedEnergyCost = breedEnergyCost;
        this.energyToBreed = energyToBreed;
        this.mutationVariant = mutationVariant;

        this.animalsBehavior = animalsBehavior;
        this.genotype = new Genotype(genotypeLength, mutationVariant);

        randomDirection();
    }


    /**
     *Bogusz Laszczyk
     */
    public Animal(IWorldMap map, Vector2d initialPosition, int energy, Animal parent1, Animal parent2) // tworzenie potomka
    {
        this.map = map;
        this.position = initialPosition;
        this.energy = energy;
        this.breedEnergyCost = parent1.breedEnergyCost;
        this.energyToBreed = parent1.energyToBreed;
        this.genotypeLength = parent1.genotypeLength;
        this.mutationVariant = parent1.mutationVariant;
        this.animalsBehavior = parent1.animalsBehavior;
        this.genotype = new Genotype(parent1,parent2, parent1.genotype.minNumOfMutations, parent1.genotype.maxNumOfMutations);

        randomDirection();
        int firstGen=this.genotype.startFromRandomGen();
        setDirection(this.genotype.genotype[firstGen]);
    }



    /**
     *Bogusz Laszczyk
     */
    boolean isAlive()
    {
        return this.isAlive;
    }


    /**
     *Bogusz Laszczyk
     */
    void changeEnergy(int value)
    {
        this.energy = this.energy + value;
        if(this.energy<0)
        {
            this.energy=0;
            this.isAlive =false;
        }
    }


    /**
     *Bogusz Laszczyk
     */
    public void move(int moveCost)
    {
        int nextGen;
        if(Objects.equals(this.animalsBehavior, "full predestination")) //pelna predestynacja
        {
            System.out.println("full predestination");
            nextGen=this.genotype.nextGenFromGenotype();
        }
        else //nieco szaleństwa
        {
            Random random=new Random();
            int probability = random.nextInt(100);
            if(probability<80)
            {
                nextGen=this.genotype.nextGen;
            }
            else
            {
                nextGen=this.genotype.getRandomGen();
            }
        }

        setDirection(this.genotype.genotype[nextGen]);

        Vector2d nextPosition = this.position.add(this.direction.toUnitVector());
        nextPosition=this.map.edges(this.position,nextPosition,this);

        if (this.map.canMoveTo(nextPosition))
        {
            positionChanged(this.position, nextPosition);
            this.position = nextPosition;
            this.energy-=moveCost;
            if(this.energy<=0)
            {
                this.energy=0;
                this.isAlive=false;
            }
        }

    }


    /**
     *Bogusz Laszczyk
     */
    public void oppositeDirection(MapDirection direction)
    {
        this.direction=direction.oppositeDirection();
    }


    /**
     *Bogusz Laszczyk
     */
    public void setDirection(int genotype)
    {
        for (int i = 0; i < genotype; i++) //wykonujemy tyle obrotow o 45 stopni ile wynosi obecny gen
        {
            this.direction=this.direction.next();
        }
    }


    /**
     *Bogusz Laszczyk
     */
    public void randomDirection()
    {
        Random random = new Random();
        int rotation = random.nextInt(8);

        this.direction = this.direction.rotate(rotation);
    }


    /**
    Karolina Klisz
     */
    public Animal breeding(Animal partner1, Animal partner2)
    {
        Animal child=null;
        if(partner1.energy>this.energyToBreed && partner2.energy>this.energyToBreed) //teoretycznie moga byc tez rowne, ale troche by to bylo przykre urodzic sie przy martwych rodzicach
        {
            int childEnergy = 2 * this.energyToBreed;
            partner1.changeEnergy((-1)*this.breedEnergyCost);
            partner2.changeEnergy((-1)*this.breedEnergyCost);

            if (partner1.energy < partner2.energy) // sprawdzamy, ktory rodzic jest silniejszy
            {
                child = new Animal(map, this.position, childEnergy, partner1, partner2);
            } else {
                child = new Animal(map, this.position, childEnergy, partner2, partner1);
            }
        }
        return child;
    }

    int getEnergy()
    {
        return this.energy;
    }

    int getNumberOfChildren()
    {
        return this.numberOfChildren;
    }

    public int getAge()
    {
        return this.age;
    }

    public Vector2d getPosition() {
        return this.position;
    }

    public MapDirection getDirection() {
        return this.direction;
    }

    public Genotype getGenotype()
    {
        return this.genotype;
    }

    void addObserver(IPositionChangeObserver observer) {
        this.observers.add(observer);
    }

    void removeObserver(IPositionChangeObserver observer) {
        this.observers.remove(observer);
    }

    void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        for (IPositionChangeObserver observer : this.observers) {
            observer.positionChanged(oldPosition, newPosition);
        }
    }

}