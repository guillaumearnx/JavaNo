package mvc;

/**
 * Inteface modele
 * Architecture: MVC
 *
 * @author arnoux23u
 */
public interface Model {

    /**
     * Ajoute un observateur
     */
    void addObserver(Observer o);

    /**
     * Supprime un observateur
     */
    void removeObserver(Observer o);

    /**
     * Notifie les observateurs
     */
    void notifyObservers();

}
