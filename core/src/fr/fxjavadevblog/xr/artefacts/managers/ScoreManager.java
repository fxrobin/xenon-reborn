package fr.fxjavadevblog.xr.artefacts.managers;

public class ScoreManager
{
	private int score = 0;

	private static ScoreManager sm = new ScoreManager();

	public static ScoreManager getInstance()
	{
		return sm;
	}

	public synchronized void add(int points)
	{
		score += points;
	}

	public Integer getScore()
	{
		return score;
	}
}
