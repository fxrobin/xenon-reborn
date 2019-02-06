package fr.fxjavadevblog.xr.artefacts;

import java.util.List;

/**
 * représente une scène composée d'artefacts.
 * 
 * @author robin
 *
 */
public interface ArtefactsScene
{
	/**
	 * retourne la liste des artefacts de la scène.
	 * 
	 * @return
	 */
	List<Artefact> getArtefacts();
}
