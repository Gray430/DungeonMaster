package net.dungeonsworkshop.dungeonmaster.common.map.objects;

import net.dungeonsworkshop.dungeonmaster.common.map.editor.EditorManager;

import java.util.HashMap;
import java.util.Map;

public class ObjectGroup {
    public String id;
    private final Map<String, Tile> objects = new HashMap<>();

    public ObjectGroup(String id) {
        this.id = id;
    }

    public ObjectGroup addTile(Tile tile) {
        objects.put(tile.getId().toLowerCase(), tile);
        return this;
    }

    public Map<String, Tile> getObjects() {
        return objects;
    }

    public String getId() {
        return id;
    }

    public Tile getTile(String id) {
        return objects.get(id.toLowerCase());
    }

}
