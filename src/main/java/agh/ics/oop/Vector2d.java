package agh.ics.oop;

import java.util.Objects;

public class Vector2d {
    final int x;
    final int y;
    public Vector2d(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    public String toString()
    {
        return ("("+this.x+","+this.y+")");
    }
    public boolean precedes(Vector2d other)
    {
        return this.x <= other.x && this.y <= other.y;
    }
    public boolean follows(Vector2d other)
    {
        return this.x >= other.x && this.y >= other.y;
    }
    public Vector2d add(Vector2d other)
    {
        return new Vector2d(this.x+ other.x,this.y+ other.y);
    }
    public Vector2d subtract(Vector2d other)
    {
        return new Vector2d(this.x- other.x,this.y- other.y);
    }
    public Vector2d upperRight(Vector2d other)
    {
        if(this.x>= other.x)
        {
            if(this.y>= other.y)
                return new Vector2d(this.x, this.y);
            else
                return new Vector2d(this.x, other.y);
        }
        else
        {
            if(this.y>= other.y)
                return new Vector2d(other.x, this.y);
            else
                return new Vector2d(other.x, other.y);
        }
    }

    public Vector2d lowerLeft(Vector2d other)
    {
        if(this.x<= other.x)
        {
            if(this.y<= other.y)
                return new Vector2d(this.x, this.y);
            else
                return new Vector2d(this.x, other.y);
        }
        else
        {
            if(this.y<= other.y)
                return new Vector2d(other.x, this.y);
            else
                return new Vector2d(other.x, other.y);
        }
    }

    public Vector2d opposite()
    {
        return new Vector2d((-1)*this.x,(-1)*this.y);
    }

    public boolean equals(Object other)
    {
        if (this.x == ((Vector2d) other).x && this.y==((Vector2d) other).y)
        {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(this.x,this.y);
    }
}