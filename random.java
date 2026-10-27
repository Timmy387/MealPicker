class MinecraftBlock {
    private String name;
    private Image blockDesign;
    private int x, y, z;
    private String tool;
    private double breakSpeed;
    private int stackLimit;
    private int etc;
    public MinecraftBlock(String name, Image blockDesign,
                           int x, int y, int z,
                           String tool, double breakSpeed, int stackLimit) {
        this.name = name;
        this.blockDesign = blockDesign;
        this.x = x;
        this.y = y;
        this.z = z;
        this.tool = tool;
        this.breakSpeed = breakSpeed;
        this.stackLimit = stackLimit;
    }
    public void breakBlock(){
        this.x, this.y, this.z = 0;
    }
    public placeBlock(int x, int y, int z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public boolean fullStack?(int stackSize){
        return stackSize == this.stackLimit;
    }
    public void breakable(String toolType){
        return toolType == this.tool;
    }
    public void playerOnBLock(String player){}
}


class Cobblestone extends MinecraftBlock{
    public Cobblestone(){
        super("Cobblestone", new Image("Cobblestone.png"), 0, 0, 0, "pickaxe", 1, 64);
    }
}

class MagmaBlock extends MinecraftBlock{
    public MagmaBlock(){
        super("Magma", new Image("Magma.png"), 0, 0, 0, "pickaxe", 1, 64);
    }

    @java.lang.Override
    
    public void playerOnBLock(String player) {
        super.playerOnBLock(player);
        dealDamage(player);
    }
}