package fr.fxjavadevblog.xr.artefacts;

/**
 * évenements déclenchés pendant la durée de vie d'un artefact (Observer /
 * Observable pattern).
 * 
 * 
 * @author robin
 *
 */
public enum Event
{
	/**
	 * évenement déclenché quand un artefact est créé.
	 */
	CREATED,
	
	/**
	 * événement déclenché quand un artefact est touché.
	 */
	HIT, 
	
	/**
	 * événement déclenché quand un artefact est détruit.
	 */
	DESTROYED;
}
