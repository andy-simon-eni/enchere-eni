package fr.eni.javaee.enchere.dal;

import fr.eni.javaee.enchere.BusinessException;
import fr.eni.javaee.enchere.bo.Retraits;

public interface RetraitsDAO {
	public void insert(Retraits retrait) throws BusinessException;
}
