package fr.fxjavadevblog.xr.commons;

/**
 * permet d'exécuter un runnable une seule et une seule fois, même si le
 * singleExecutor est rappelé.
 * 
 * @author robin
 *
 */
public class SingleExecutor
{
	private Runnable runnable;
	private boolean ran = false;

	public SingleExecutor(Runnable runnable)
	{
		this.runnable = runnable;
	}

	/**
	 * lance le runnable fournit à la construit, une seule fois. Cette méthode
	 * garantit que le runnable n'est pas relancé.
	 */
	public void execute()
	{
		if (!ran && runnable != null)
		{
			ran = true;
			runnable.run();
		}
	}

}
