package net.dungeonsworkshop.dungeonmaster.util;

public enum ResourcePackEnum {
    Vanilla("vanilla"),
    SquidCoast("squidcoast");

    private String id;

    ResourcePackEnum(String id){
        this.id = id;
    }

    public static ResourcePackEnum getFromID(String string){
        for(int i = 0; i < ResourcePackEnum.values().length; i++){
            if(ResourcePackEnum.values()[i].id.equalsIgnoreCase(string)){
                return ResourcePackEnum.values()[i];
            }
        }
        return Vanilla;
    }

    public String getId() {
        return id;
    }
}
