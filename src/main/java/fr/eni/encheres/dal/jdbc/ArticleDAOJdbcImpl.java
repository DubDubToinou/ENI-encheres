package fr.eni.encheres.dal.jdbc;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.Articles;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.ArticleDAO;
import fr.eni.encheres.dal.CodesResultatDAL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArticleDAOJdbcImpl implements ArticleDAO {

    private static final String INSERT_ARTICLE = "INSERT INTO Articles(nom_article, description, date_debut_encheres, date_fin_encheres, " +
            "prix_initial, prix_vente, no_utilisateur, no_categorie) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String INSERT_RETRAIT = "INSERT INTO RETRAITS(no_article, rue, code_postal, ville) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_ARTICLE = "UPDATE Articles SET nom_article = ?, description = ?, date_debut_encheres = ?, " +
            "date_fin_encheres = ?, prix_initial = ?, prix_vente = ?, no_categorie = ? WHERE no_article = ?";
    private static final String UPDATE_RETRAIT = "UPDATE Retraits SET rue = ?, code_postal = ?, ville = ? WHERE no_article = ?";
    private static final String DELETE_ARTICLE = "DELETE FROM Articles WHERE no_article = ?";
    private static final String SELECT_BY_NO_ARTICLE = "SELECT a.no_article, a.nom_article, a.description, a.date_debut_encheres, a.date_fin_encheres, " +
            "a.prix_initial, a.prix_vente, u.pseudo, r.rue, r.code_postal, r.ville, c.libelle, c.no_categorie, u.telephone, u.no_utilisateur " +
            "FROM Articles a " +
            "INNER JOIN UTILISATEURS u ON a.no_utilisateur = u.no_utilisateur " +
            "INNER JOIN Retraits r ON a.no_article = r.no_article " +
            "INNER JOIN CATEGORIES c ON a.no_categorie = c.no_categorie " +
            "WHERE a.no_article = ?";
    private static final String SELECT_VENTES_EN_COURS = "SELECT a.no_article, a.nom_article, a.description, a.date_debut_encheres, a.date_fin_encheres, " +
            "a.prix_initial, a.prix_vente, u.pseudo, c.libelle FROM Articles a " +
            "LEFT JOIN Utilisateurs u " +
            "ON a.no_utilisateur = u.no_utilisateur " +
            "LEFT JOIN Categories c " +
            "ON a.no_categorie = c.no_categorie " +
            "WHERE a.date_debut_encheres <= GETDATE() AND a.date_fin_encheres >= GETDATE()";
    private static final String SELECT_BY_CATEGORIE = "SELECT a.no_article, a.nom_article, a.description, a.date_debut_encheres, a.date_fin_encheres, " +
            "a.prix_initial, a.prix_vente, u.pseudo, c.libelle FROM Articles a " +
            "LEFT JOIN Utilisateurs u " +
            "ON a.no_utilisateur = u.no_utilisateur " +
            "LEFT JOIN Categories c " +
            "ON a.no_categorie = c.no_categorie " +
            "WHERE a.date_debut_encheres <= GETDATE() AND a.date_fin_encheres >= GETDATE() AND c.libelle = ?";

    private static final String SELECT_BY_MOT_CLE = "SELECT a.no_article, a.nom_article, a.description, a.date_debut_encheres, a.date_fin_encheres, " +
            "a.prix_initial, a.prix_vente, u.pseudo, c.libelle FROM Articles a " +
            "LEFT JOIN Utilisateurs u " +
            "ON a.no_utilisateur = u.no_utilisateur " +
            "LEFT JOIN Categories c " +
            "ON a.no_categorie = c.no_categorie " +
            "WHERE a.date_debut_encheres <= GETDATE() AND a.date_fin_encheres >= GETDATE() " +
            "AND (a.nom_article LIKE ? OR a.description LIKE ?)";

    private static final String SELECT_BY_MOT_CLE_AND_CATEGORIE = "SELECT a.no_article, a.nom_article, a.description, a.date_debut_encheres, a.date_fin_encheres, " +
            "a.prix_initial, a.prix_vente, u.pseudo, c.libelle FROM Articles a " +
            "LEFT JOIN Utilisateurs u " +
            "ON a.no_utilisateur = u.no_utilisateur " +
            "LEFT JOIN Categories c " +
            "ON a.no_categorie = c.no_categorie " +
            "WHERE a.date_debut_encheres <= GETDATE() AND a.date_fin_encheres >= GETDATE() " +
            "AND (a.nom_article LIKE ? OR a.description LIKE ?)" +
            "AND c.libelle = ?";

    private static final String SELECT_VENTES_EN_COURS_BY_UTILISATEUR = "SELECT a.no_article, a.nom_article, a.description, a.date_debut_encheres, a.date_fin_encheres, " +
            "a.prix_initial, a.prix_vente, c.libelle FROM Articles a " +
            "LEFT JOIN Categories c " +
            "ON a.no_categorie = c.no_categorie " +
            "WHERE a.date_debut_encheres <= GETDATE() AND a.date_fin_encheres >= GETDATE() " +
            "AND a.no_utilisateur = ?";

    private static final String SELECT_VENTES_NON_DEBUTEES_BY_UTILISATEUR = "SELECT a.no_article, a.nom_article, a.description, a.date_debut_encheres, a.date_fin_encheres, " +
            "a.prix_initial, a.prix_vente, c.libelle FROM Articles a " +
            "LEFT JOIN Categories c " +
            "ON a.no_categorie = c.no_categorie " +
            "WHERE a.date_debut_encheres > GETDATE() " +
            "AND a.no_utilisateur = ?";

    private static final String SELECT_VENTES_TERMINEES_BY_UTILISATEUR = "SELECT a.no_article, a.nom_article, a.description, a.date_debut_encheres, a.date_fin_encheres, " +
            "a.prix_initial, a.prix_vente, c.libelle FROM Articles a " +
            "LEFT JOIN Categories c " +
            "ON a.no_categorie = c.no_categorie " +
            "WHERE a.date_fin_encheres <= GETDATE() " +
            "AND a.no_utilisateur = ?";

    @Override
    public void insert(Articles article) throws BusinessException{
        if(article==null)
        {
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesResultatDAL.INSERT_OBJET_NULL);
            throw businessException;
        }

        try(Connection con = ConnectionProvider.getConnection())  {

            PreparedStatement pstmtArticle = con.prepareStatement(INSERT_ARTICLE, PreparedStatement.RETURN_GENERATED_KEYS);
            PreparedStatement pstmtRetrait = con.prepareStatement(INSERT_RETRAIT);

            //Ajout de l'article
            pstmtArticle.setString(1, article.getNomArticle());
            pstmtArticle.setString(2, article.getDescription());
            pstmtArticle.setObject(3, article.getDateDebutEncheres());
            pstmtArticle.setObject(4, article.getDateFinEncheres());
            pstmtArticle.setInt(5, article.getMiseAPrix());
            pstmtArticle.setInt(6, article.getMiseAPrix());
            pstmtArticle.setInt(7, article.getUtilisateurs().getNoUtilisateur());
            pstmtArticle.setInt(8, article.getCategorieArticle().getNoCategorie());

            pstmtArticle.executeUpdate();

            ResultSet rs = pstmtArticle.getGeneratedKeys();

            if(rs.next()) {
                System.out.println(article);
                article.setNoArticle(rs.getInt(1));

                //ajout du lieu de retrait (par défaut adresse du vendeur)
                pstmtRetrait.setInt(1, article.getNoArticle());
                pstmtRetrait.setString(2, article.getLieuRetrait().getRue());
                pstmtRetrait.setString(3, article.getLieuRetrait().getCodePostal());
                pstmtRetrait.setString(4, article.getLieuRetrait().getVille());

                pstmtRetrait.executeUpdate();
            }

            rs.close();
            pstmtArticle.close();
            pstmtRetrait.close();


        } catch(Exception ex) {
            ex.printStackTrace();
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesResultatDAL.INSERT_OBJET_ECHEC);
            throw businessException;
        }
    }

    @Override
    public void update(Articles article) throws BusinessException{

        if(article.getNoArticle()==null || article.getNoArticle()==0) {
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesResultatDAL.ARTICLE_NULL_ECHEC);
            throw businessException;
        }

        try(Connection con = ConnectionProvider.getConnection()) {

            PreparedStatement pstmtArticle = con.prepareStatement(UPDATE_ARTICLE);
            PreparedStatement pstmtRetrait = con.prepareStatement(UPDATE_RETRAIT);

            pstmtArticle.setString(1, article.getNomArticle());
            pstmtArticle.setString(2, article.getDescription());
            pstmtArticle.setObject(3, article.getDateDebutEncheres());
            pstmtArticle.setObject(4, article.getDateFinEncheres());
            pstmtArticle.setInt(5, article.getMiseAPrix());
            pstmtArticle.setInt(6, article.getPrixVente());
            pstmtArticle.setInt(7, article.getCategorieArticle().getNoCategorie());
            pstmtArticle.setInt(8, article.getNoArticle());

            pstmtArticle.executeUpdate();

            pstmtRetrait.setString(1, article.getLieuRetrait().getRue());
            pstmtRetrait.setString(2, article.getLieuRetrait().getCodePostal());
            pstmtRetrait.setString(3, article.getLieuRetrait().getVille());
            pstmtRetrait.setInt(4, article.getNoArticle());

            pstmtArticle.close();
            pstmtRetrait.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesResultatDAL.UPDATE_ARTICLE_ECHEC);
            throw businessException;
        }
    }

    @Override
    public void delete(Articles article) throws BusinessException{

        if(article.getNoArticle()==null || article.getNoArticle()==0) {
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesResultatDAL.ARTICLE_NULL_ECHEC);
            throw businessException;
        }

        try(Connection con = ConnectionProvider.getConnection()) {
            PreparedStatement pstmtArticle = con.prepareStatement(DELETE_ARTICLE);


            pstmtArticle.setInt(1, article.getNoArticle());
            pstmtArticle.executeUpdate();

            pstmtArticle.close();

        } catch(Exception ex) {
            ex.printStackTrace();
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesResultatDAL.DELETE_ARTICLE_ECHEC);
            throw businessException;
        }
    }

    @Override
    public Articles selectByNoArticle(int noArticle) throws BusinessException {
        Articles article = new Articles();

        try (Connection con = ConnectionProvider.getConnection()){
            PreparedStatement pstmt = con.prepareStatement(SELECT_BY_NO_ARTICLE);
            pstmt.setInt(1,noArticle);

            ResultSet rs = pstmt.executeQuery();

            if(rs.next()) {
                article.setNoArticle(rs.getInt(1));
                article.setNomArticle(rs.getString(2));
                article.setDescription(rs.getString(3));
                article.setDateDebutEncheres(((Timestamp) rs.getObject(4)).toLocalDateTime());
                article.setDateFinEncheres(((Timestamp) rs.getObject(5)).toLocalDateTime());
                article.setMiseAPrix(rs.getInt(6));
                article.setPrixVente(rs.getInt(7));


                Utilisateur utilisateur = new Utilisateur(rs.getString(8));
                utilisateur.setTelephone(rs.getString(14));
                utilisateur.setNoUtilisateur(rs.getInt(15));
                article.setUtilisateurs(utilisateur);


                Retrait retrait = new Retrait(rs.getString(9), rs.getString(10), rs.getString(11));
                article.setLieuRetrait(retrait);

                Categorie categorie = new Categorie(rs.getString(12));
                categorie.setNoCategorie(rs.getInt(13));
                article.setCategorieArticle(categorie);

            }
        } catch(Exception ex) {
            ex.printStackTrace();
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesResultatDAL.SELECT_ARTICLE_BY_NUM_ECHEC);
        }
        return article;
    }

    @Override
    public List<Articles> selectVentesEnCours() throws BusinessException{
        List<Articles> listeArticles = new ArrayList<>();

        try (Connection con = ConnectionProvider.getConnection()){
            Statement selectall = con.createStatement();
            ResultSet rs = selectall.executeQuery(SELECT_VENTES_EN_COURS);

            while(rs.next()) {
              Articles article = new Articles(rs.getInt(1), rs.getString(2), rs.getString(3),
                      ((Timestamp) rs.getObject(4)).toLocalDateTime(), ((Timestamp) rs.getObject(5)).toLocalDateTime(),
                      rs.getInt(6), rs.getInt(7));

              Utilisateur utilisateur = new Utilisateur(rs.getString(8));
              Categorie categorie = new Categorie(rs.getString(9));

              article.setUtilisateurs(utilisateur);
              article.setCategorieArticle(categorie);

              listeArticles.add(article);

            }

            rs.close();
            selectall.close();

        } catch(Exception ex) {
            ex.printStackTrace();
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesResultatDAL.SELECT_VENTES_ENCOURS_ECHEC);
            throw businessException;
        }

        return listeArticles;
    }

    @Override
    public List<Articles> selectByCategorie(String libelle) throws BusinessException{
        List<Articles> listeArticles = new ArrayList<>();

        try (Connection con = ConnectionProvider.getConnection()){
           PreparedStatement pstmt = con.prepareStatement(SELECT_BY_CATEGORIE);
           pstmt.setString(1, libelle);

            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {
                Articles article = new Articles(rs.getInt(1), rs.getString(2), rs.getString(3),
                        ((Timestamp) rs.getObject(4)).toLocalDateTime(), ((Timestamp) rs.getObject(5)).toLocalDateTime(),
                        rs.getInt(6), rs.getInt(7));

                Utilisateur utilisateur = new Utilisateur(rs.getString(8));
                Categorie categorieArticle = new Categorie(rs.getString(9));

                article.setUtilisateurs(utilisateur);
                article.setCategorieArticle(categorieArticle);

                listeArticles.add(article);
            }

            rs.close();
            pstmt.close();
        }catch(Exception ex) {
            ex.printStackTrace();
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesResultatDAL.SELECT_BY_CATEGORIE_ECHEC);
            throw businessException;
        }

        if(libelle==null || libelle.isBlank()) {
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesResultatDAL.CATEGORIE_NULL_ECHEC);
            throw businessException;
        }

        return listeArticles;
    }

    @Override
    public List<Articles> selectByMotCle(String motCle) throws BusinessException{
        List<Articles> listeArticles = new ArrayList<>();

        try (Connection con = ConnectionProvider.getConnection()){
            PreparedStatement pstmt = con.prepareStatement(SELECT_BY_MOT_CLE);
            pstmt.setString(1, "%" + motCle+ "%");
            pstmt.setString(2, "%" + motCle+ "%");

            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {
                Articles article = new Articles(rs.getInt(1), rs.getString(2), rs.getString(3),
                        ((Timestamp) rs.getObject(4)).toLocalDateTime(), ((Timestamp) rs.getObject(5)).toLocalDateTime(),
                        rs.getInt(6), rs.getInt(7));

                Utilisateur utilisateur = new Utilisateur(rs.getString(8));
                Categorie categorie = new Categorie(rs.getString(9));

                article.setUtilisateurs(utilisateur);
                article.setCategorieArticle(categorie);

                listeArticles.add(article);
            }

            rs.close();
            pstmt.close();

        }catch(Exception ex) {
            ex.printStackTrace();
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesResultatDAL.SELECT_BY_MOTCLE_ECHEC);
            throw businessException;
        }

        return listeArticles;
    }

    @Override
    public List<Articles> selectByMotCleAndCategorie(String motCle, String libelle) throws BusinessException{
        List<Articles> listeArticles = new ArrayList<>();

        try (Connection con = ConnectionProvider.getConnection()){
            PreparedStatement pstmt = con.prepareStatement(SELECT_BY_MOT_CLE_AND_CATEGORIE);
            pstmt.setString(1, "%" + motCle+ "%");
            pstmt.setString(2, "%" + motCle+ "%");
            pstmt.setString(3, libelle);

            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {
                Articles article = new Articles(rs.getInt(1), rs.getString(2), rs.getString(3),
                        ((Timestamp) rs.getObject(4)).toLocalDateTime(), ((Timestamp) rs.getObject(5)).toLocalDateTime(),
                        rs.getInt(6), rs.getInt(7));

                Utilisateur utilisateur = new Utilisateur(rs.getString(8));
                Categorie categorieArticle = new Categorie(rs.getString(9));

                article.setUtilisateurs(utilisateur);
                article.setCategorieArticle(categorieArticle);

                listeArticles.add(article);
            }

            rs.close();
            pstmt.close();

        }catch(Exception ex) {
            ex.printStackTrace();
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesResultatDAL.SELECT_BY_MOTCLE_ECHEC);
            throw businessException;
        }

        return listeArticles;
    }

    @Override
    public List<Articles> selectVentesEnCoursParUtilisateur(Utilisateur utilisateur) throws BusinessException{
        List<Articles> listeArticles = new ArrayList<>();

        try (Connection con = ConnectionProvider.getConnection()){

            PreparedStatement pstmt = con.prepareStatement(SELECT_VENTES_EN_COURS_BY_UTILISATEUR);
            pstmt.setInt(1, utilisateur.getNoUtilisateur());
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {
                Articles article = new Articles(rs.getInt(1), rs.getString(2), rs.getString(3),
                        ((Timestamp) rs.getObject(4)).toLocalDateTime(), ((Timestamp) rs.getObject(5)).toLocalDateTime(),
                        rs.getInt(6), rs.getInt(7));

                Categorie categorie = new Categorie(rs.getString(8));

                article.setUtilisateurs(utilisateur);
                article.setCategorieArticle(categorie);

                listeArticles.add(article);
            }

            rs.close();
            pstmt.close();

        } catch(Exception ex) {
            ex.printStackTrace();
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesResultatDAL.SELECT_VENTES_PAR_UTILISATEUR_ECHEC);
            throw businessException;
        }

        if(utilisateur.getNoUtilisateur()==null || utilisateur.getNoUtilisateur()==0) {
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesResultatDAL.UTILISATEUR_INEXISTANT);
            throw businessException;
        }

        return listeArticles;
    }

    public List<Articles> selectVentesNonDebuteesParUtilisateur(Utilisateur utilisateur) throws BusinessException {
        List<Articles> listeArticles = new ArrayList<>();

        try (Connection con = ConnectionProvider.getConnection()){

            PreparedStatement pstmt = con.prepareStatement(SELECT_VENTES_NON_DEBUTEES_BY_UTILISATEUR);
            pstmt.setInt(1, utilisateur.getNoUtilisateur());
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {
                Articles article = new Articles(rs.getInt(1), rs.getString(2), rs.getString(3),
                        ((Timestamp) rs.getObject(4)).toLocalDateTime(), ((Timestamp) rs.getObject(5)).toLocalDateTime(),
                        rs.getInt(6), rs.getInt(7));

                Categorie categorie = new Categorie(rs.getString(8));

                article.setUtilisateurs(utilisateur);
                article.setCategorieArticle(categorie);

                listeArticles.add(article);
            }

            rs.close();
            pstmt.close();

        } catch(Exception ex) {
            ex.printStackTrace();
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesResultatDAL.SELECT_VENTES_NON_DEBUTEES_PAR_UTILISATEUR_ECHEC);
            throw businessException;
        }

        if(utilisateur.getNoUtilisateur()==null || utilisateur.getNoUtilisateur()==0) {
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesResultatDAL.UTILISATEUR_INEXISTANT);
            throw businessException;
        }

        return listeArticles;
    }
    @Override
    public List<Articles> selectVentesTermineesParUtilisateur(Utilisateur utilisateur) throws BusinessException {
        List<Articles> listeArticles = new ArrayList<>();

        try (Connection con = ConnectionProvider.getConnection()){

            PreparedStatement pstmt = con.prepareStatement(SELECT_VENTES_TERMINEES_BY_UTILISATEUR);
            pstmt.setInt(1, utilisateur.getNoUtilisateur());
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {
                Articles article = new Articles(rs.getInt(1), rs.getString(2), rs.getString(3),
                        ((Timestamp) rs.getObject(4)).toLocalDateTime(), ((Timestamp) rs.getObject(5)).toLocalDateTime(),
                        rs.getInt(6), rs.getInt(7));

                Categorie categorie = new Categorie(rs.getString(8));

                article.setUtilisateurs(utilisateur);
                article.setCategorieArticle(categorie);

                listeArticles.add(article);
            }

            rs.close();
            pstmt.close();

        } catch(Exception ex) {
            ex.printStackTrace();
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesResultatDAL.SELECT_VENTES_TERMINEES_PAR_UTILISATEUR_ECHEC);
            throw businessException;
        }

        if(utilisateur.getNoUtilisateur()==null || utilisateur.getNoUtilisateur()==0) {
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesResultatDAL.UTILISATEUR_INEXISTANT);
            throw businessException;
        }

        return listeArticles;
    }
}
