
public class Bandage extends Item implements FirstAid{
	int recovery = 0;
	
	public Bandage(){
		setDiscription("bandage", 1);
		setRecovery(10);
		useFeedback = "You bandage your wounds, and heal yourself";
	}
	
	@Override
	public void setRecovery(int value) {
		recovery = value;
	}

	@Override
	public String Use() {
		Player player = Player.getInstance();
		player.heal(recovery);
		player.getPlayerInventory().getItems().remove(getName());
		return useFeedback;
	}

}
