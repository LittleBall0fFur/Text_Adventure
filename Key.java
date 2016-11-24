
public class Key extends Item{
	
	public Key(){
		setDiscription("Key", 1);
		useFeedback = "you used the key to open the locked door in the theater";
	}
	
	@Override
	public String Use() {
		return useFeedback;
	}

}
