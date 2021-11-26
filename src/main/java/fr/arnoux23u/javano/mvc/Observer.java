package fr.arnoux23u.javano.mvc;

/**
 * Interface observateur
 * Architecture MVC
 *
 * @author arnoux23u
 */
public interface Observer {

    /**
     * Acutalisation
     *
     * @param m modele
     */
    void update(Model m);

}