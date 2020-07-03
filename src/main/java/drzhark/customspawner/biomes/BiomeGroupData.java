package drzhark.customspawner.biomes;

import java.util.List;

public class BiomeGroupData {

    private String biomeGroupName;
    private List<String> biomes;

    public BiomeGroupData(String biomeGroup, List<String> biomeList) {
        this.biomeGroupName = biomeGroup;
        this.biomes = biomeList;
    }

    public String getBiomeGroupName() {
        return this.biomeGroupName;
    }

    public List<String> getBiomeList() {
        return this.biomes;
    }

    public void addBiome(String biome) {
        this.biomes.add(biome);
    }

}
