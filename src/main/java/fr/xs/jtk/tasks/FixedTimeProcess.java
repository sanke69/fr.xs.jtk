package fr.xs.jtk.tasks;

public interface FixedTimeProcess extends Runnable {

	public int  getProgression();
	public void setProgression(int _percent);

}
