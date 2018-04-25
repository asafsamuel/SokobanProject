package model.policy;

import common.Level;

/*** Interface SokobanPolicy - checks player movements ***/
public interface SokobanPolicy
{
	public boolean CheckPolicy(Level l , String key);
}
