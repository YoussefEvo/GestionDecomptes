package ma.co.marsamaroc.gestion.decomptes.dao;


import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import ma.co.marsamaroc.gestion.decomptes.dao.interfaces.IDao;



public class Dao<T> implements IDao<T> {

	@PersistenceContext
	public EntityManager entityManager;
	@Autowired
	@Qualifier("jdbcTemplateDecompte")
	public JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	Class<T> maClasse;

	public Dao(Class<T> pClasse) {
		this.maClasse = pClasse;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	/**
	 * Méthode de création des objets entités
	 * 
	 * @param objet
	 */
	@Transactional
	public void creer(T objet) throws Exception {

		entityManager.persist(objet);

	}

	/**
	 * Méthode de MAJ des objets entités
	 * 
	 * @param objet
	 */
	@Transactional
	public void modifier(T objet) {
		entityManager.merge(objet);

	}

	/**
	 * Méthode de suppression des objets entités
	 * 
	 * @param objet
	 */
	@Transactional
	public void supprimer(T objet) {
		entityManager.remove(objet);
		//System.out.println("supprimer dao objet : "+objet.toString());

	}

	/**
	 * Méthode de recherche par KP des objets entités
	 * 
	 * @param id
	 *            l'identifiant de l'objet
	 * @param c
	 *            :la classe de l'objet
	 * @return objet : l'objet recherché
	 */

	public T rechercherParKP(Object id) {

		return entityManager.find(maClasse, id);

	}

	/**
	 * Méthode de recherche tous les objets entités
	 * 
	 * @param id
	 *            l'identifiant de l'objet
	 * @param c
	 *            :la classe de l'objet
	 * @return objet : l'objet recherché
	 */

	@SuppressWarnings("unchecked")
	public List<T> rechercherTous() {

		Query query = entityManager.createQuery("select a from "
				+ maClasse.getName() + " a");
		List<T> resultat = query.getResultList();
		return resultat;

	}

	//@Override
	public void creerSet(Set<T> objet) throws Exception {
		for (Iterator<T> iter = objet.iterator() ; iter.hasNext() ; ) {
			entityManager.persist(iter.next());
		}
	}

}
