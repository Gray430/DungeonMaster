package net.dungeonsworkshop.dungeonmaster.util;

import net.minecraft.util.math.MathHelper;

import java.util.Objects;

public class Vec2i {
    public static final Vec2i NULL_VECTOR = new Vec2i(0, 0);

    private final int x;
    private final int z;

    public Vec2i(int xIn, int zIn) {
        this.x = xIn;
        this.z = zIn;
    }

    public Vec2i(double xIn, double zIn) {
        this(MathHelper.floor(xIn), MathHelper.floor(zIn));
    }

    public int getX() {
        return this.x;
    }

    public int getZ() {
        return this.z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vec2i)) return false;
        Vec2i vec2i = (Vec2i) o;
        return getX() == vec2i.getX() &&
                getZ() == vec2i.getZ();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getZ());
    }
}
