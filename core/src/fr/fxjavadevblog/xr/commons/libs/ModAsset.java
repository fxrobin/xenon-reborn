package fr.fxjavadevblog.xr.commons.libs;

public enum ModAsset
{
	XENON_REMIX("mods/aa_xenon.xm"),
	FAKTORY("mods/faktory.mod"), 
	DOODLE_DOO("mods/doodle-doo.mod"), 
	TRANCE_124("mods/trance-124.mod"), 
	BREATH_TAKER("mods/breathtaker.mod"), 
	XENON_ORIGINAL("mods/xenon2.mod"), 
	XENON_200("mods/xenon220.xm"), 
	SPACE_MEGAMIX("mods/space-megamix.mod"), 
	INTRO("mods/intro.mod"),;

	final String modName;

	private ModAsset(String modName)
	{
		this.modName = modName;
	}

	@Override
	public String toString()
	{
		return modName;
	}
}
