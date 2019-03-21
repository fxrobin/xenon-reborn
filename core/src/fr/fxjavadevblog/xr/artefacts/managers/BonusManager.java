package fr.fxjavadevblog.xr.artefacts.managers;

import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import fr.fxjavadevblog.xr.artefacts.collectables.Bonus;
import fr.fxjavadevblog.xr.artefacts.collectables.BonusType;
import fr.fxjavadevblog.xr.artefacts.friendly.ship.Ship;
import fr.fxjavadevblog.xr.commons.libs.SoundAsset;

/**
 * gestionnaire des bonus.
 * 
 * @author robin
 *
 */
public class BonusManager
{
	private List<Bonus> bonuses = new LinkedList<>();

	private static BonusManager instance = new BonusManager();

	public static BonusManager getInstance()
	{
		return instance;
	}

	private BonusManager()
	{
		// protection
	}

	public void addBonus(BonusType bonusType, float x, float y)
	{
		Bonus bonus = Bonus.newInstance(bonusType, x, y);
		bonuses.add(bonus);
	}

	public void render(SpriteBatch batch, float delta)
	{
		bonuses.forEach(bonus -> bonus.render(batch, delta));
		bonuses.removeIf(e -> e.getBoundingCircle().y < -e.getBoundingCircle().radius || !e.isAlive());
	}

	/**
	 * vérifie les collisions des bonus avec le vaisseau. Si tel est le cas, le
	 * bonus est "capturer" par le vaisseau, et le bonus est traité, en fonction
	 * de son type.
	 * 
	 * @param ship
	 */
	public void checkBonus(Ship ship)
	{
		if (!bonuses.isEmpty())
		{
			bonuses.stream().filter(b -> b.isCollision(ship)).forEach(b -> processBonus(ship, b));
		}
	}

	/**
	 * traite le bonus en fonction de son type. modifie l'état du vaisseau en
	 * fonction du bonus.
	 * 
	 * @param ship
	 * 
	 * @param bonus
	 */
	public void processBonus(Ship ship, Bonus bonus)
	{
		switch (bonus.getType())
		{
			case NORMAL_BONUS:
				break;
			case POWER_UP_BONUS:
				ship.increaseLife(10);
				break;
			default:
		}
		bonus.decreaseLife(100); // on tue le bonus...
		SoundAsset.BONUS.play();
	}
}
