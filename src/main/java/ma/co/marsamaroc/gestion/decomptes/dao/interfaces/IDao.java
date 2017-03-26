package ma.co.marsamaroc.gestion.decomptes.dao.interfaces;

import java.util.List;
import java.util.Set;

public interface IDao<T> {

	public List<T> rechercherTous();
	public T rechercherParKP(Object id);
	public void supprimer(T objet);
	public void modifier(T objet);
	public void creer(T objet) throws Exception;
	public void creerSet(Set<T> objet) throws Exception;
	
	
}
