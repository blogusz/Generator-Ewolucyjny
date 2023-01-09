package agh.ics.oop;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import java.util.*;
import java.util.stream.Collectors;

public class GrassField implements IWorldMap {
    public HashMap<Vector2d, Grass> grassAll = new HashMap<>();
    public Multimap<Vector2d, Animal> animals = HashMultimap.create();

    public HashMap<Animal, Animal> partners = new HashMap<>(); //przechowuje partnera1 jako klucz i partnera2 jako jego wartosc
    public HashMap<Vector2d, Integer> whereAnimalDied = new HashMap<>();
    public Vector2d lowerLeft;
    public Vector2d upperRight;
    private final String mapVariant;
    private final String grassGrowVariant;
    int grassEnergy;

    private final int width;
    private final int height;
    //public final SettingsLoader settingsLoader=new SettingsLoader();
    private final int howManyGrassesDaily;

    public GrassField(int width, int height, String mapVariant, String grassGrowVariant, int grassEnergy, int howManyGrassesDaily) {
        this.width = width;
        this.height = height;
        this.mapVariant = mapVariant;
        this.grassGrowVariant = grassGrowVariant;
        this.grassEnergy = grassEnergy;
        this.howManyGrassesDaily = howManyGrassesDaily;
        this.lowerLeft = new Vector2d(0, 0);
        this.upperRight = new Vector2d(width, height);

    }
    /**
    Karolina Klisz
     */
    public void plantGrass() {
        if (this.grassGrowVariant.equals("forested equators")) {
            forestedEquators(this.howManyGrassesDaily);
        } else {
            toxicCorpses(this.howManyGrassesDaily);
        }
    }


    /**
     *Bogusz Laszczyk
     */
    public Vector2d globeEdges(Vector2d oldPosition, Vector2d newPosition, Animal animal) {
        if (newPosition.y > this.upperRight.y || newPosition.y < this.lowerLeft.y) //wyjscie na biegun
        {
            animal.oppositeDirection(animal.getDirection());
            return oldPosition;
        } else if (newPosition.x > this.upperRight.x) //prawy koniec mapy
        {
            return new Vector2d(this.lowerLeft.x, newPosition.y);
        } else if (newPosition.x < this.lowerLeft.x) //lewy koniec mapy
        {
            return new Vector2d(this.upperRight.x, newPosition.y);
        } else {
            return newPosition;
        }
    }



    /**
     *Bogusz Laszczyk
     */
    public Vector2d edges(Vector2d oldPosition, Vector2d nextPosition, Animal animal) {
        if (this.mapVariant.equals("globe")) {
            return globeEdges(oldPosition, nextPosition, animal);
        } else {
            return hellPortalEdges(nextPosition, animal);
        }
    }


    /**
     *Bogusz Laszczyk
     */
    public Vector2d hellPortalEdges(Vector2d nextPosition, Animal animal) {

        int x = nextPosition.x;
        int y = nextPosition.y;
        if (nextPosition.x > this.upperRight.x || nextPosition.x < this.lowerLeft.x || nextPosition.y > upperRight.y || nextPosition.y < lowerLeft.y) {
            animal.changeEnergy((-1) * animal.energyToBreed);
            Random random = new Random();

            x = random.nextInt(this.upperRight.x - this.lowerLeft.x + 1) + this.lowerLeft.x;
            y = random.nextInt(this.upperRight.y - this.lowerLeft.y + 1) + this.lowerLeft.y;

        }

        return new Vector2d(x, y);

    }


    /**
    Karolina Klisz
     */
    public void forestedEquators(int howManyGrassesDaily) {
        int x;
        int y;
        int numberOfPreferredFields = (this.height * this.width) / 5;

        Random random = new Random();


        for (int i = 0; i < howManyGrassesDaily; i++) { //tyle trawy dziennie przybywa
            int probability = random.nextInt(100); //losujemy prawdopodobienstwo
            do {
                x = random.nextInt(width); //pozycja x trawy losowana
                if (probability < 80) { //wyrośnie na preferowanym polu czyli 20% mapy
                    if (numberOfPreferredFields > 0) { //jak sie pola wyczerpia to rosnie gdziekolwiek
                        y = height / 2;
                        numberOfPreferredFields -= 1; //jak juz wyroslo to trzeba odjac 1
                    } else {
                        y = random.nextInt(height / 2);
                        int upDown = random.nextInt(2);
                        if (upDown == 0) {//góra mapy, jesli 1 to zostaje to co wcześniej czyli dół mapy
                            y += y;
                        }
                    }
                } else {
                    y = random.nextInt(height / 2);
                    int upDown = random.nextInt(2);
                    if (upDown == 0) {//góra mapy, jesli 1 to zostaje to co wcześniej czyli dół mapy
                        y += y;
                    }
                }
            } while (objectAt(new Vector2d(x, y)) instanceof Grass); //trzeba tu dodac jakis warunek na sprawdzenie, czy przypadkiem nie zostaly juz zajete wszystkie pola na mapie
            //} while (isOccupied(new Vector2d(x, y))); //trzeba tu dodac jakis warunek na sprawdzenie, czy przypadkiem nie zostaly juz zajete wszystkie pola na mapie
            Grass grass = new Grass(new Vector2d(x, y), this.grassEnergy);
            this.grassAll.put(new Vector2d(x, y), grass);

        }
    }


    /**
    Karolina Klisz
     */
    public void toxicCorpses(int howManyGrassesDaily) {
        Map<Vector2d, Integer> deadAnimals = new HashMap<>();
        ArrayList<Vector2d> positionMap = new ArrayList<>(); //wszystkie pozycje na mapie
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                positionMap.add(new Vector2d(i, j));
            }
        }
        for (Vector2d position : positionMap) {

            for (Vector2d deadPosition : this.whereAnimalDied.keySet()) {
                if (deadPosition.equals(position)) //zwierzeta tu umarly
                {
                    deadAnimals.put(position, this.whereAnimalDied.get(position));

                } else {
                    deadAnimals.put(position, 0); //zadne zwierze nie umarlo na tym polu
                }
            }
        }


        List<Map.Entry<Vector2d, Integer>> list = new ArrayList<>(deadAnimals.entrySet());
        list.sort(Map.Entry.comparingByValue());
        Map<Vector2d, Integer> sortedDeaths = new LinkedHashMap<>();
        for (Map.Entry<Vector2d, Integer> entry : list) { //sortowanie tablicy po wartościach
            sortedDeaths.put((entry.getKey()), entry.getValue());
        }
        int numberOfPreferredFields = (this.height * this.width) / 5;


        Random random = new Random();


        for (int i = 0; i <howManyGrassesDaily; i++) {
            if(!sortedDeaths.isEmpty()) {
                int probability = random.nextInt(100);
                Vector2d currentPosition;
                if (probability < 80) {
                    if (numberOfPreferredFields > 0) {
                        for (Vector2d position : sortedDeaths.keySet()) {
                            currentPosition = position;
                            numberOfPreferredFields -= 1;
                            Grass grass = new Grass(currentPosition, this.grassEnergy);
                            this.grassAll.put(currentPosition, grass);
                            sortedDeaths.remove(currentPosition);
                            break;
                        }
                    } else {
                        currentPosition = lastElement(sortedDeaths);
                        Grass grass = new Grass(currentPosition, this.grassEnergy);
                        this.grassAll.put(currentPosition, grass);
                        sortedDeaths.remove(currentPosition);

                    }
                } else {
                    currentPosition = lastElement(sortedDeaths);

                    Grass grass = new Grass(currentPosition, this.grassEnergy);
                    this.grassAll.put(currentPosition, grass);
                    sortedDeaths.remove(currentPosition);

                }
            }
        }
    }


    /**
     *Bogusz Laszczyk
     */
    public boolean isOccupied(Vector2d position)
    {
        return this.grassAll.get(position) != null;
    }

    public Object objectAt(Vector2d position)
    {
        if (this.animals.get(position) != null)
        {
            return animals.get(position);
        }
        else if (this.grassAll.get(position) != null)
        {
            return this.grassAll.get(position);
        }
        else
        {
            return null;
        }
    }

    public ArrayList<Animal> animalsAt(Vector2d position)
    {
        return new ArrayList<>(this.animals.get(position));
    }


    /**
     *Bogusz Laszczyk <3
     */
    public boolean canMoveTo(Vector2d position)
    {
        return true;
    }


    /**
     *Bogusz Laszczyk
     */
    public boolean place(Animal animal)
    {
        if (!canMoveTo(animal.getPosition()))
        {
            return false;
        }
        else {
            this.animals.put(animal.getPosition(), animal);
            //animal.addObserver((IPositionChangeObserver) this);

            return true;
        }
    }

    /**
     *Bogusz Laszczyk
     */
    public void placeChild(Animal child)
    {
        this.animals.put(child.getPosition(), child);
        child.addObserver((IPositionChangeObserver) this);
    }


    /**
    Karolina Klisz
     */
    public ArrayList<Animal> removeDeadAnimals(Multimap<Vector2d, Animal> animals)
    {
        ArrayList<Animal> deadAnimals = new ArrayList<>();
        for (Animal deadAnimal : animals.values())
        {
            if (!deadAnimal.isAlive())
            {
                deadAnimals.add(deadAnimal);
                if(!this.whereAnimalDied.containsKey(deadAnimal.getPosition()))
                {
                    this.whereAnimalDied.put(deadAnimal.getPosition(),1);
                }
                this.whereAnimalDied.replace(deadAnimal.getPosition(),this.whereAnimalDied.get(deadAnimal.getPosition())+1);
            }
        }
        return deadAnimals;
    }


    /**
     *Bogusz Laszczyk
     */
    public void getPartnerAnimals() //przeszukuje wszystkie pozycje zwierzakow i dodaje do hashmapy te, ktore sa w parach na tej samej pozycji. Pare wybieramy kolejno po sile, wieku i liczbie dzieci
    {
        Set<Vector2d> positionsWithAnimals = animals.keySet();

        for(Vector2d position:positionsWithAnimals)
        {
            List<Animal> topTwoAnimals = animals.get(position).stream()
                    .sorted(Comparator.comparingInt(Animal::getEnergy)
                            .thenComparingInt(Animal::getAge)
                            .thenComparingInt(Animal::getNumberOfChildren))
                    .limit(2)
                    .collect(Collectors.toList());

            if(topTwoAnimals.size()>1) //bo na danej pozycji moze byc tez tylko jedno zwierze
            {
                this.partners.put(topTwoAnimals.get(0), topTwoAnimals.get(1));
            }

        }
    }


    /**
     *Bogusz Laszczyk
     */
    public ArrayList<Animal> breed()
    {
        getPartnerAnimals();
        ArrayList<Animal> children = new ArrayList<>();

        for (Animal partner :this.partners.keySet())
        {
            Animal child=partner.breeding(partner, this.partners.get(partner));

            if(child!=null)
            {
                children.add(child);
                partner.numberOfChildren+=1;
                this.partners.get(partner).numberOfChildren+=1;
            }
        }
        this.partners.clear(); //po kazdym cukly kasujemy zawartosc mapy z partnerami
        return children;
    }


    /**
     *Bogusz Laszczyk
     */
    public Vector2d randomAnimalPosition()
    {
        Random random = new Random();
        Vector2d randomPosition;

        do {
            int x = random.nextInt(this.upperRight.x - this.lowerLeft.x + 1) + this.lowerLeft.x;
            int y = random.nextInt(this.upperRight.y - this.lowerLeft.y + 1) + this.lowerLeft.y;
            randomPosition = new Vector2d(x, y);

        } while (!canMoveTo(randomPosition));

        return randomPosition;
    }


    /**
     *Bogusz Laszczyk
     */
    public HashMap<Grass, Animal> eating()
    {
        HashMap<Grass, Animal> strongestAnimals = new HashMap<>();
        Set<Vector2d> positionsWithAnimals = animals.keySet();
        for(Vector2d position:positionsWithAnimals)
        {
            Animal strongestAnimal = animals.get(position).stream()
                    .max(Comparator.comparingInt(Animal::getEnergy)
                            .thenComparingInt(Animal::getAge)
                            .thenComparingInt(Animal::getNumberOfChildren))
                    .orElse(null);

            strongestAnimals.put(grassAt(strongestAnimal != null ? strongestAnimal.getPosition() : null),strongestAnimal);
        }
        return strongestAnimals;
    }

    public Grass grassAt(Vector2d position) {
        return this.grassAll.get(position);
    }


    /**
    Karolina Klisz
     */
    public Vector2d lastElement(Map<Vector2d, Integer> map){
        Map.Entry<Vector2d, Integer> lastPosition = map.entrySet().stream().skip(map.size() - 1).findFirst().get();
        Vector2d lastKey = lastPosition.getKey();

        return lastKey;
    }

}