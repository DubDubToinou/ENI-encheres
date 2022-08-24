package fr.eni.encheres;


import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrator
 *
 * Cette classe permet de recenser l'ensemble des erreurs (par leur code) pouvant survenir lors d'un traitement
 * quel que soit la couche à l'origine.
 */
public class BusinessException extends Exception {
    private List<Integer> listeCodesErreur;

    public BusinessException() {
        super();
        this.listeCodesErreur = new ArrayList<>();
    }

    /**
     *
     * @param code Code de l'erreur.
     * Doit avoir un message associé dans un fichier properties.
     */
    public void ajouterErreur(int code)
    {
        if(!this.listeCodesErreur.contains(code))
        {
            this.listeCodesErreur.add(code);
        }
    }

    public boolean hasErreurs()
    {
        return this.listeCodesErreur.size()>0;
    }

    public List<Integer> getListeCodesErreur()
    {
        return this.listeCodesErreur;
    }

}