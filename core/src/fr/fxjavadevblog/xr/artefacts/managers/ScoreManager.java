package fr.fxjavadevblog.xr.artefacts.managers;

import fr.fxjavadevblog.xr.commons.fonts.bitmap.FontUtils;
import fr.fxjavadevblog.xr.commons.fonts.bitmap.GdxBitmapString;

public class ScoreManager
{
	private int score = 0;
	private GdxBitmapString scoreBitmapString;

	private static ScoreManager sm = new ScoreManager();
	
	private ScoreManager()
	{
		this.generateBitmapString();
	}
	
	public static ScoreManager getInstance()
	{
		return sm;
	}

	public void add(int points)
	{
		score += points;
		generateBitmapString();
	}

	/**
	 * génère la bitmapString représentant le score à des fin d'optimisation (éviter String.format à chaque image).
	 * Cette méthode n'est appelée que quand le score change.
	 */
	private void generateBitmapString()
	{
		String scoreAsString = String.format("%06d", score);
		scoreBitmapString = new GdxBitmapString(FontUtils.FONT_BLUE, scoreAsString);
	}

	public Integer getScore()
	{
		return score;
	}
	
	public GdxBitmapString getScoreBitmapString()
	{
		return scoreBitmapString;
	}
}
