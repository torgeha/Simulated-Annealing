
public class Schedule {
	
	//Decreasing rate of the temperature
	private final double DT = 0.01;
	//set by the user to determine how quickly the exploring should subside
	private double temperature;
	
	public Schedule(double temperature) {
		this.temperature = temperature;
	}
	
	//decrease the temperature
	public double scheduleTemp() {
		return temperature = temperature - DT;
	}

}
