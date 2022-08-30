package fr.eni.encheres.dal.jdbc;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.Articles;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.CodesResultatDAL;
import fr.eni.encheres.dal.UtilisateurDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UtilisateurDAOJdbcImpl implements UtilisateurDAO {
    private static final String SELECT_BY_ID = "SELECT no_utilisateur, nom, prenom, email, telephone FROM UTILISATEURS WHERE pseudo = ?";
    private static final String SELECT_ARTICLE = "SELECT no_article, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_categorie FROM ARTICLES WHERE no_utilisateur = ?";
    private static final String SELECT_PROFIL = "SELECT no_utilisateur, pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur FROM UTILISATEURS WHERE pseudo = ?";
    private static final String INSERT = "INSERT INTO UTILISATEURS (pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE UTILISATEURS SET pseudo = ?, nom = ?, prenom = ?, email = ?, telephone = ?, rue = ?, code_postal = ?, ville = ? WHERE no_utilisateur = ?";
    private static final String DELETE = "DELETE FROM UTILISATEURS WHERE pseudo = ?";
    private static final String SELECT_PSEUDO_EXISTANT = "SELECT pseudo FROM UTILISATEURS WHERE pseudo = ?";
    private static final String SELECT_EMAIL_EXISTANT = "SELECT email FROM UTILISATEURS WHERE email = ?";
    private static final String SELECT_MOT_DE_PASSE = "SELECT mot_de_passe FROM UTILISATEURS WHERE (pseudo = ? or email= ?)";
    private static final String UPDATE_MOT_DE_PASSE = "UPDATE UTILISATEURS SET mot_de_passe = ? WHERE pseudo = ?";
//TODO
    @Override
    public Utilisateur selectByPseudo(String pseudo) throws BusinessException {
        Utilisateur u = new Utilisateur();

        try (Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement stmt = cnx.prepareStatement(SELECT_BY_ID);
            stmt.setString(1, pseudo);
            ResultSet result = stmt.executeQuery();

            // Récupère la liste des articles mis en vente par l'utilisateur
            PreparedStatement stmtArticle = cnx.prepareStatement(SELECT_ARTICLE);


            if (result.next()) {
                stmtArticle.setInt(1, result.getInt("no_utilisateur"));
                ResultSet resultArticle = stmtArticle.executeQuery();

                List<Articles> articles = new ArrayList<>();
                u.setPseudo(pseudo);
                u.setNom(result.getString("nom"));
                u.setPrenom(result.getString("prenom"));
                u.setEmail(result.getString("email"));
                u.setTelephone(result.getString("telephone"));

                while (resultArticle.next()) {
                    Articles article = new Articles(resultArticle.getInt("no_article"), resultArticle.getString("nom_article"),
                            resultArticle.getString("description"), ((Timestamp) resultArticle.getObject("date_debut_encheres")).toLocalDateTime(),
                            ((Timestamp) resultArticle.getObject("date_fin_encheres")).toLocalDateTime(), resultArticle.getInt("prix_initial"),
                            resultArticle.getInt("prix_vente"));
                    u.addArticleVendu(article);
                }
                resultArticle.close();
            }
            result.close();

            stmt.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesResultatDAL.SELECT_UTILISATEUR_PAR_PSEUDO_ECHEC);
            throw businessException;
        }
        return u;
    }

    @Override
    public Utilisateur selectOwnProfile(Utilisateur utilisateur) throws BusinessException {
        Utilisateur u = new Utilisateur();

        try (Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement stmt = cnx.prepareStatement(SELECT_PROFIL);
            stmt.setString(1, utilisateur.getPseudo());
            ResultSet result = stmt.executeQuery();

            if(result.next()) {

                u.setNoUtilisateur(result.getInt("no_utilisateur"));
                u.setPseudo(result.getString("pseudo"));
                u.setNom(result.getString("nom"));
                u.setPrenom(result.getString("prenom"));
                u.setEmail(result.getString("email"));
                u.setTelephone(result.getString("telephone"));
                u.setRue(result.getString("rue"));
                u.setCodePostal(result.getString("code_postal"));
                u.setVille(result.getString("ville"));
                u.setMotDePasse(result.getString("mot_de_passe"));
                u.setCredit(result.getInt("credit"));
                u.setAdministrateur(result.getInt("administrateur"));
            }
            result.close();
            stmt.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesResultatDAL.SELECT_OWN_PROFILE_ECHEC);
            throw businessException;
        }
        return u;
    }

    @Override
    public void insert(Utilisateur utilisateur) throws BusinessException {

        if(utilisateur==null)
        {
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesResultatDAL.INSERT_OBJET_NULL);
            throw businessException;
        }

        try (Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement stmt = cnx.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1, utilisateur.getPseudo());
            stmt.setString(2, utilisateur.getNom());
            stmt.setString(3, utilisateur.getPrenom());
            stmt.setString(4, utilisateur.getEmail());
            stmt.setString(5, utilisateur.getTelephone());
            stmt.setString(6, utilisateur.getRue());
            stmt.setString(7, utilisateur.getCodePostal());
            stmt.setString(8, utilisateur.getVille());
            stmt.setString(9, utilisateur.getMotDePasse());
            stmt.execute();


            // Récupère le numéro de l'utilisateur généré par la BDD
            ResultSet dernierUtilisateur = stmt.getGeneratedKeys();
            dernierUtilisateur.next();
            utilisateur.setNoUtilisateur(dernierUtilisateur.getInt(1));

            stmt.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesResultatDAL.INSERT_OBJET_ECHEC);
            throw businessException;
        }
    }

    @Override
    public void update(Utilisateur utilisateur) throws BusinessException {

        if(utilisateur.getNoUtilisateur()==null || utilisateur.getNoUtilisateur()==0) {
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesResultatDAL.UTILISATEUR_INEXISTANT);
            throw businessException;
        }

        try (Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement stmt = cnx.prepareStatement(UPDATE);
            stmt.setString(1, utilisateur.getPseudo());
            stmt.setString(2, utilisateur.getNom());
            stmt.setString(3, utilisateur.getPrenom());
            stmt.setString(4, utilisateur.getEmail());
            stmt.setString(5, utilisateur.getTelephone());
            stmt.setString(6, utilisateur.getRue());
            stmt.setString(7, utilisateur.getCodePostal());
            stmt.setString(8, utilisateur.getVille());
            stmt.setInt(9, utilisateur.getNoUtilisateur());
            stmt.execute();

            stmt.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesResultatDAL.UPDATE_UTILISATEUR_ECHEC);
            throw businessException;
        }
    }

    public void updateMotDePasse(Utilisateur utilisateur) throws BusinessException {

        if (utilisateur.getNoUtilisateur() == null || utilisateur.getNoUtilisateur() == 0) {
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesResultatDAL.UTILISATEUR_INEXISTANT);
            throw businessException;
        }

        try(Connection cnx = ConnectionProvider.getConnection()){
            System.out.println(utilisateur);
            PreparedStatement stmt = cnx.prepareStatement(UPDATE_MOT_DE_PASSE);
            stmt.setString(1, utilisateur.getMotDePasse());
            stmt.setString(2, utilisateur.getPseudo());
            stmt.execute();

            stmt.close();

        }catch (Exception ex) {
            ex.printStackTrace();
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesResultatDAL.UPDATE_UTILISATEUR_ECHEC);
            throw businessException;
        }
    }



    @Override
    public void delete(Utilisateur utilisateur) throws BusinessException {

        if(utilisateur.getNoUtilisateur()==null || utilisateur.getNoUtilisateur()==0) {
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesResultatDAL.UTILISATEUR_INEXISTANT);
            throw businessException;
        }

        try (Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement stmt = cnx.prepareStatement(DELETE);
            stmt.setString(1, utilisateur.getPseudo());
            stmt.execute();

            stmt.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesResultatDAL.DELETE_UTILISATEUR_ECHEC);
            throw businessException;
        }
    }

    @Override
    public boolean pseudoIsInBase(Utilisateur utilisateur) throws BusinessException {

        boolean isInBase = false;

        try (Connection cnx = ConnectionProvider.getConnection()) {

            PreparedStatement pstmt = cnx.prepareStatement(SELECT_PSEUDO_EXISTANT);
            pstmt.setString(1, utilisateur.getPseudo().trim());
            ResultSet rs = pstmt.executeQuery();

            if(rs.next()) {
                isInBase = true;
            }

            rs.close();
            pstmt.close();

        } catch(Exception ex) {
            ex.printStackTrace();
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesResultatDAL.PSEUDO_IN_BASE_ECHEC);
            throw businessException;
        }
        return isInBase;
    }

    @Override
    public boolean emailIsInBase(Utilisateur utilisateur) throws BusinessException {
        boolean isInBase = false;

        try (Connection cnx = ConnectionProvider.getConnection()) {

            PreparedStatement pstmt = cnx.prepareStatement(SELECT_EMAIL_EXISTANT);
            pstmt.setString(1, utilisateur.getEmail().trim());
            ResultSet rs = pstmt.executeQuery();

            if(rs.next()) {
                isInBase = true;
            }

            rs.close();
            pstmt.close();

        } catch(Exception ex) {
            ex.printStackTrace();
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesResultatDAL.EMAIL_IN_BASE_ECHEC);
            throw businessException;
        }
        return isInBase;
    }

    @Override
    public String selectMotDePasse(String login) throws BusinessException {
        String password = "";
        try (Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement pstmt = cnx.prepareStatement(SELECT_MOT_DE_PASSE);
            pstmt.setString(1, login);
            pstmt.setString(2, login);

            ResultSet rs = pstmt.executeQuery();

            if(rs.next()) {
               password = password.concat(rs.getString(1));
            }

        } catch(SQLException ex) {
            ex.printStackTrace();
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesResultatDAL.SELECT_PASSWORD_ECHEC);
            throw businessException;
        }

        return password;
    }
}