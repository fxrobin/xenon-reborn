package fr.fxjavadevblog.xr.artefacts.managers;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import fr.fxjavadevblog.xr.artefacts.enemies.Bullet;
import fr.fxjavadevblog.xr.artefacts.enemies.Enemy;
import fr.fxjavadevblog.xr.artefacts.enemies.EnemyType;
import fr.fxjavadevblog.xr.commons.displays.Renderable;
import fr.fxjavadevblog.xr.commons.libs.AnimationAsset;
import fr.fxjavadevblog.xr.commons.utils.DeltaTimeAccumulator;
import fr.fxjavadevblog.xr.commons.utils.GdxCommons;

public class EnemyManager implements Renderable
{
	private DeltaTimeAccumulator deltaTimeAccumulator = new DeltaTimeAccumulator(4f);
	private List<Enemy> enemies = new LinkedList<>();

	private static EnemyManager em = new EnemyManager();

	public static EnemyManager getInstance()
	{
		return em;
	}

	public void generateEnemies(float delta)
	{
		if (deltaTimeAccumulator.addAndCheck(delta))
		{
			/* on génère 4 enemis toutes les 4 secondes */
			for (int i = 0; i < 3; i++)
			{
				Enemy e = EnemyType.random();
				enemies.add(e);
			}

			/* et on génère une escadrille de 3 méchants */
			this.generateEnemySquadron(3);
		}
	}

	public void generateEnemySquadron(int numbers)
	{
		Enemy original = EnemyType.random();
		enemies.add(original);
		for (int i = 1; i < numbers; i++)
		{
			Enemy e = new Enemy(original);
			e.setX(original.getX() - (original.getBoundingCircle().radius * 2));
			e.setY(original.getY() - original.getBoundingCircle().radius);
			enemies.add(e);
			original = e;
		}

	}

	public void act(float delta)
	{
		enemies.forEach(e -> {
			e.update(delta);
			if (!e.isAlive())
			{
				AnimationAsset anim = e instanceof Bullet ? AnimationAsset.EXPLOSION_LITTLE : AnimationAsset.EXPLOSION_BIG;
				ExplosionManager.addExplosion(e, anim);
			}
		});

		enemies.removeIf(e -> e.getY() < -e.getHeight() || !e.isAlive());
	}

	public List<Enemy> getEnemies()
	{
		return enemies;
	}

	@Override
	public void render(SpriteBatch batch, float delta)
	{
		List<Enemy> localEnemies = new ArrayList<>(enemies);
		localEnemies.forEach(e -> {
			if (e.isAlive()) e.render(batch, delta);
		});
	}

	public void generateBullet(Enemy e)
	{
		Bullet b = new Bullet();
		b.setOriginCenter();
		b.setX(GdxCommons.getCenterX(e.getSprite()));
		b.setY(e.getY());
		b.setVectorX(0);
		b.setVectorY(e.getVectorY() * 2f);
		enemies.add(b);
	}

}
