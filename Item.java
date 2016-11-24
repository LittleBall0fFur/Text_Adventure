
public abstract class Item {
	private String name;
	private int weight;
	private String discription;
	public String useFeedback;
	
	public Item() {
		// TODO Auto-generated constructor stub
	}
	
	public String getName(){
		return name;
	}
	
	public String getDiscription(){
		discription = "\n" + name +  ", it weighs: " + getWeight();
		return discription;
	}
	
	public void setDiscription(String key, int i){
		name = key;
		weight = i;
	}
	
	public int getWeight(){
		return weight;
	}
	
	public abstract String Use();
}
