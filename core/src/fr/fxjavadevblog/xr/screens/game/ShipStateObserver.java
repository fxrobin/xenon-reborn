package fr.fxjavadevblog.xr.screens.game;

import java.util.Observable;

import fr.fxjavadevblog.xr.artefacts.Artefact;
import fr.fxjavadevblog.xr.artefacts.Event;
import fr.fxjavadevblog.xr.artefacts.managers.ExplosionManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fr.fxjavadevblog.xr.commons.libs.AnimationAsset;
import fr.fxjavadevblog.xr.commons.libs.SoundAsset;

/**
 * fournit des méthodes déclencher en observation d'un changement d'état d'un
 * artefact.
 * 
 * @author robin
 *
 */
public final class ShipStateObserver
{
	private static final Log log = LogFactory.getLog(ShipStateObserver.class);

	private ShipStateObserver()
	{
		// protection
	}

	/**
	 * observer d'explosion pour l'instance de Ship. Refactorisation à terme pour
	 * sortir le choix de l'explosion (et le son).
	 * 
	 * @param o
	 * @param event
	 */
	public static void shipExplosion(Observable o, Object event)
	{
		if (o instanceof Artefact && event instanceof Event)
		{
			if (log.isInfoEnabled())
			{
				log.info("Event : " + event);
			}
			ExplosionManager.addExplosion((Artefact) o, AnimationAsset.EXPLOSION_BIG);
			SoundAsset.SHIP_EXPLOSION.play();
		}
	}

}
