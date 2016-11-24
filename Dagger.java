
public class Dagger extends Item implements Weapon{
	int damage;
	
	public Dagger(){
		setDiscription("dagger", 5);
		setDamage(5);
		useFeedback = "while taking a close look at the dagger and sampling the blood on the blade you cut yourself";
	}

	@Override
	public void setDamage(int _damage) {
		damage = _damage;
	}
	
	//bad item
	@Override
	public String Use(){
		Player player = Player.getInstance();
		player.setDamage(damage);
		player.loseLife();
		player.setDamage(0);
		
		return useFeedback;
	}
}
