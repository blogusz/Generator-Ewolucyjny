package agh.ics.oop;

public enum MapDirection
{
    NORTH,
    SOUTH,
    WEST,
    EAST,
    NORTHWEST,
    SOUTHWEST,
    NORTHEAST,
    SOUTHEAST;

    @Override
    public String toString()
    {
        return switch (this) {
            case NORTH -> "Północ";
            case SOUTH -> "Południe";
            case WEST -> "Zachód";
            case EAST -> "Wschód";
            case NORTHWEST -> "Północny zachód";
            case SOUTHWEST -> "Południowy zachód";
            case NORTHEAST -> "Północny wschód";
            case SOUTHEAST -> "Południowy wschód";
        };
    }

    public MapDirection next()
    {
        return switch (this) {
            case NORTH -> NORTHEAST;
            case SOUTH -> SOUTHWEST;
            case WEST -> NORTHWEST;
            case EAST -> SOUTHEAST;
            case NORTHWEST -> NORTH;
            case SOUTHWEST -> WEST;
            case NORTHEAST -> EAST;
            case SOUTHEAST -> SOUTH;
        };
    }
    public MapDirection previous()
    {
        return switch (this) {
            case NORTH -> NORTHWEST;
            case SOUTH -> SOUTHEAST;
            case WEST -> SOUTHWEST;
            case EAST -> NORTHEAST;
            case NORTHWEST -> WEST;
            case SOUTHWEST -> SOUTH;
            case NORTHEAST -> NORTH;
            case SOUTHEAST -> EAST;
        };
    }

//    public MapDirection setDirection(int genotype)
//    {
//        return switch (genotype) {
//            case 0 -> NORTH;
//            case 1 -> NORTHEAST;
//            case 2 -> EAST;
//            case 3 -> SOUTHEAST;
//            case 4 -> SOUTH;
//            case 5 -> SOUTHWEST;
//            case 6 -> WEST;
//            case 7 -> NORTHWEST;
//            default -> NORTH;
//        };
//    }

    public Vector2d toUnitVector()
    {
        return switch (this)
                {
                    case NORTH -> new Vector2d(0, 1);
                    case SOUTH -> new Vector2d(0, -1);
                    case WEST -> new Vector2d(-1, 0);
                    case EAST -> new Vector2d(1, 0);
                    case NORTHWEST -> new Vector2d(-1, 1);
                    case SOUTHWEST -> new Vector2d(-1, -1);
                    case NORTHEAST -> new Vector2d(1, 1);
                    case SOUTHEAST -> new Vector2d(1, -1);
                };
    }

    /**
     *Bogusz Laszczyk
     */
    public MapDirection rotate(int genotype)
    {
        return switch (genotype) {
            case 1 -> NORTHEAST;
            case 2 -> EAST;
            case 3 -> SOUTHEAST;
            case 4 -> SOUTH;
            case 5 -> SOUTHWEST;
            case 6 -> WEST;
            case 7 -> NORTHWEST;
            default -> NORTH;
        };
    }

    /**
     *Bogusz Laszczyk
     */
    public MapDirection oppositeDirection()
    {
        return switch (this) {
            case NORTH -> SOUTH;
            case SOUTH -> NORTH;
            case WEST -> EAST;
            case EAST -> WEST;
            case NORTHWEST -> SOUTHEAST;
            case SOUTHWEST -> NORTHEAST;
            case NORTHEAST -> SOUTHWEST;
            case SOUTHEAST -> NORTHWEST;
        };
    }

}