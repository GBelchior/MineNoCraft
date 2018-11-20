/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minenocraft.utils;

/**
 *
 * @author gabri
 */
public class MapKeyTest
{
    private final int x;
    private final int z;

    public MapKeyTest(int x, int z)
    {
        this.x = x;
        this.z = z;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 67 * hash + this.x;
        hash = 67 * hash + this.z;
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final MapKeyTest other = (MapKeyTest) obj;
        if (this.x != other.x)
            return false;
        if (this.z != other.z)
            return false;
        return true;
    }
}
