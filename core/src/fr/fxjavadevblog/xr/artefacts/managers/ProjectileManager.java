package fr.fxjavadevblog.xr.artefacts.managers;

import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import fr.fxjavadevblog.xr.artefacts.Artefact;
import fr.fxjavadevblog.xr.artefacts.friendly.weapons.Position;
import fr.fxjavadevblog.xr.artefacts.friendly.weapons.Shoot;
import fr.fxjavadevblog.xr.artefacts.friendly.weapons.ShootType;
import fr.fxjavadevblog.xr.commons.Global;
import fr.fxjavadevblog.xr.commons.libs.AnimationAsset;
import fr.fxjavadevblog.xr.commons.utils.RandomUtils;

public class ProjectileManager
{
	private List<Artefact> shoots = new LinkedList<>();

	private static ProjectileManager pm = new ProjectileManager();

	public static ProjectileManager getInstance()
	{
		return pm;
	}
	
	private ProjectileManager() 
	{
		// protection du singleton
	}	

	public void renderShoots(SpriteBatch batch, float delta)
	{
		shoots.forEach(s -> {
			s.render(batch, delta);
			if (!s.isAlive()) ExplosionManager.addExplosion(s, AnimationAsset.EXPLOSION_LITTLE);
		});
		shoots.removeIf(s -> (s.getSprite().getY() > Global.SCREEN_HEIGHT || !s.isAlive()));
	}

	public void addShoot(ShootType shootType, float centerX, float centerY)
	{
		// on va décaller le tir au hasard un peu à droite ou un peu à gauche pour
		// faire joli.
		float decallage = RandomUtils.randomRange(-4, 4);
		Shoot s = new Shoot(shootType.createAnimatedSprite(), shootType.getLifeForce(), shootType.getImpactForce(), new Position(centerX + decallage, centerY), shootType.getVX(), shootType.getVY());
		shoots.add(s);
		shootType.getSound().play();
	}

	public List<Artefact> getShoots()
	{
		return this.shoots;
	}

}
