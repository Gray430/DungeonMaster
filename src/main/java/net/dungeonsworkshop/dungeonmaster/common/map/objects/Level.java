package net.dungeonsworkshop.dungeonmaster.common.map.objects;

@Deprecated
public class Level {
//
//    private String id;
//    private String resourcePacks;
//    private String visualTheme;
//    private String soundTheme;
//    private int propDensity;
//
//    private List<Objective> objectives;
//    private List<Stretch> stretches;
//    private List<MobGroup> mobGroups;
//    private Map<String, LevelTile> tiles;
//
//    private int stretchSpawnNumber = 0;
//
//    public Level(String id) {
//        this.id = id;
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public String getResourcePacks() {
//        return resourcePacks;
//    }
//
//    public void setResourcePacks(String resourcePacks) {
//        this.resourcePacks = resourcePacks;
//    }
//
//    public String getVisualTheme() {
//        return visualTheme;
//    }
//
//    public void setVisualTheme(String visualTheme) {
//        this.visualTheme = visualTheme;
//    }
//
//    public String getSoundTheme() {
//        return soundTheme;
//    }
//
//    public void setSoundTheme(String soundTheme) {
//        this.soundTheme = soundTheme;
//    }
//
//    public int getPropDensity() {
//        return propDensity;
//    }
//
//    public void setPropDensity(int propDensity) {
//        this.propDensity = propDensity;
//    }
//
//    public List<Objective> getObjectives() {
//        return objectives;
//    }
//
//    public void setObjectives(List<Objective> objectives) {
//        this.objectives = objectives;
//    }
//
//    public List<Stretch> getStretches() {
//        return stretches;
//    }
//
//    public void setStretches(List<Stretch> stretches) {
//        this.stretches = stretches;
//    }
//
//    public List<MobGroup> getMobGroups() {
//        return mobGroups;
//    }
//
//    public void setMobGroups(List<MobGroup> mobGroups) {
//        this.mobGroups = mobGroups;
//    }
//
//    public Map<String, LevelTile> getTiles() {
//        return tiles;
//    }
//
//    public void setTiles(Map<String, LevelTile> tiles) {
//        this.tiles = tiles;
//    }
//
//    public void spawnLevel(World world, BlockPos pos, LevelIdEnum levelIdEnum) {
//
//    }
//
//    public static class LevelDeserializer implements JsonDeserializer<Level> {
//
//        @Override
//        public Level deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
//            JsonObject jsonObject = json.getAsJsonObject();
//
//            String id = jsonObject.get("id").getAsString();
//            String resourcepack = jsonObject.getAsJsonArray("resource-packs").get(0).getAsString();
//            String visualTheme = "";
//            if (jsonObject.has("visual-theme"))
//                visualTheme = jsonObject.get("visual-theme").getAsString();
//            String soundTheme = "";
//            if (jsonObject.has("sound-theme"))
//                soundTheme = jsonObject.get("sound-theme").getAsString();
//            int propDensity = 1;
//            if (jsonObject.has("prop-density"))
//                propDensity = jsonObject.get("prop-density").getAsInt();
//
//            List<Objective> objectives = new ArrayList<>();
//            jsonObject.getAsJsonArray("objectives").forEach(jsonElement -> objectives.add(context.deserialize(jsonElement, Objective.class)));
//            List<Stretch> stretches = new ArrayList<>();
//            jsonObject.getAsJsonArray("stretches").forEach(jsonElement -> stretches.add(context.deserialize(jsonElement, Stretch.class)));
//            List<MobGroup> mobGroups = new ArrayList<>();
//            jsonObject.getAsJsonArray("mob-groups").forEach(jsonElement -> mobGroups.add(context.deserialize(jsonElement, MobGroup.class)));
//            Map<String, LevelTile> levelTiles = new HashMap<>();
//            jsonObject.getAsJsonArray("tiles").forEach(jsonElement -> {
//                levelTiles.put(jsonElement.getAsJsonObject().get("id").getAsString(), context.deserialize(jsonElement, LevelTile.class));
//            });
//
//
//            Level level = new Level(id);
//            level.setResourcePacks(resourcepack);
//            level.setVisualTheme(visualTheme);
//            level.setSoundTheme(soundTheme);
//            level.setPropDensity(propDensity);
//
//            level.setObjectives(objectives);
//            level.setStretches(stretches);
//            level.setMobGroups(mobGroups);
//            level.setTiles(levelTiles);
//
//            return level;
//        }
//
//    }

}
