package fr.eni.encheres.bll;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.Articles;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.EncheresDAO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class EnchereManager {
    private final EncheresDAO enchereDAO;

    public EnchereManager() {
        this.enchereDAO = DAOFactory.getEncheresDAO();
    }

    public void ajouterEnchere(Enchere enchere) throws BusinessException {
        BusinessException businessException = new BusinessException();
        this.validateEnchere(enchere, businessException);

        if (!businessException.hasErreurs()) {

            this.enchereDAO.insert(enchere);

        } else {
            throw businessException;
        }
    }

    public List<Enchere> listeEnchereEnCoursParArticle(Articles article) throws BusinessException {
        return this.enchereDAO.selectByArticle(article);
    }

    public List<Articles> listeArticleEnchereEnCoursParUtilisateur(Utilisateur utilisateur) throws BusinessException {
        return this.enchereDAO.selectEncheresEnCoursByUtilisateurs(utilisateur);
    }

    public List<Articles> listeArticleEnchereGagneParUtilisateur(Utilisateur utilisateur) throws BusinessException {
        return this.enchereDAO.selectEncheresGagneByUtilisateur(utilisateur);
    }

    public Enchere enchereGagnanteParArticle(int noArticle) throws BusinessException {
        return this.enchereDAO.selectEnchereGagnanteByArticle(noArticle);
    }

    // Validation des données :

    public void validateEnchere(Enchere enchere, BusinessException businessException) {
        if (enchere.getUtilisateur() == null) {
            businessException.ajouterErreur(CodesResultatBLL.REGLE_ENCHERE_UTILISATEUR_ERREUR);
        }

        if (enchere.getArticleVendu() == null) {
            businessException.ajouterErreur(CodesResultatBLL.REGLE_ENCHERE_ARTICLE_ERREUR);
        }

        if (enchere.getDateEnchere() == null) {
            businessException.ajouterErreur(CodesResultatBLL.REGLE_ENCHERE_DATE_ERREUR);
        }

        if (enchere.getMontant_enchere() == null || enchere.getMontant_enchere() <= enchere.getArticleVendu().getPrixVente()) {
            businessException.ajouterErreur(CodesResultatBLL.REGLE_ENCHERE_MONTANT_ERREUR);
        }
    }
}