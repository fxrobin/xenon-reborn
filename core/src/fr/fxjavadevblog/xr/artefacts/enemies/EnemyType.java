package fr.fxjavadevblog.xr.artefacts.enemies;

import com.badlogic.gdx.graphics.Texture;

import fr.fxjavadevblog.xr.artefacts.ArtefactData;
import fr.fxjavadevblog.xr.commons.Global;
import fr.fxjavadevblog.xr.commons.libs.TextureAsset;
import fr.fxjavadevblog.xr.commons.utils.RandomUtils;

public enum EnemyType
{
	NORMAL(TextureAsset.ENEMY, 10, 10), 
	BUG(TextureAsset.BUG, 10, 10), 
	PERFORATOR(TextureAsset.PERFORATOR, 10, 10), 
	BIG_ENEMY(TextureAsset.BIG_ENEMY, 20, 20), 
	RAFALE(TextureAsset.RAFALE, 15, 15), 
	BLACK_BIRD(TextureAsset.BLACK_BIRD, 15,	15), 
	XENON_SHIP(TextureAsset.XENON_SHIP, 15, 15);
	
	
	// optimisation pour ne pas générer un tableau à chaque appel.
	private static final EnemyType[] enemyTypes = EnemyType.values();

	private TextureAsset textureRef;

	private ArtefactData data;

	private EnemyType(TextureAsset textureRef, int lifeForce, int impactForce)
	{
		this.textureRef = textureRef;
		data = new ArtefactData(lifeForce, impactForce, 0, 0);
	}

	public int getImpactForce()
	{
		return data.getImpactForce();
	}

	public int getLifeForce()
	{
		return data.getLifePoints();
	}

	public TextureAsset getTextureRef()
	{
		return textureRef;
	}

	/**
	 * contruit un enemy au hasard.
	 * 
	 * @return
	 */
	public static Enemy createRandom()
	{
		EnemyType enemyType = RandomUtils.pick(enemyTypes);
		Enemy enemy = create(enemyType);
		randomCoords(enemy);
		return enemy;
	}
	
	private static Enemy create(EnemyType enemyType)
	{
		Texture texture = enemyType.getTextureRef().get();
		return new Enemy(texture, enemyType.getLifeForce(), enemyType.getImpactForce(), texture.getWidth() / 2f);
	}

	private static void randomCoords(Enemy e)
	{
		e.setOriginCenter();
		e.setX((float) Math.random() * Global.SCREEN_WIDTH);
		e.setY((float) Math.random() * 100 + Global.SCREEN_HEIGHT);
		e.setVectorX((float) Math.random() * 200f - 100);
		e.setVectorY(-((float) Math.random() * 500f + 100f));
	}

}
