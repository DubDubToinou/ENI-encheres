package fr.eni.encheres.dal.jdbc;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.Articles;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.dal.CategorieDAO;
import fr.eni.encheres.dal.CodesResultatDAL;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategorieDAOJdbcImpl implements CategorieDAO {

        private static final String INSERT_CATEGORIE = "INSERT INTO Categories (libelle) VALUES(?)";
        private static final String UPDATE_CATEGORIE = "UPDATE Categories SET libelle = ? WHERE no_categorie = ?";
        private static final String DELETE_CATEGORIE = "DELETE FROM Categories WHERE no_categorie = ?";
        private static final String SELECT_BY_LIBELLE = "SELECT no_categorie FROM CATEGORIES WHERE libelle = ?";
        private static final String SELECT_ALL = "SELECT no_categorie, libelle FROM Categories";
        private static final String SELECT_BY_LIBELLE_BOOLEAN = "SELECT 1 FROM Categories WHERE libelle = ?";

        public void insert(Categorie categorie) throws BusinessException {

            if(categorie==null)
            {
                BusinessException businessException = new BusinessException();
                businessException.ajouterErreur(CodesResultatDAL.INSERT_OBJET_NULL);
                throw businessException;
            }

            try(Connection con = ConnectionProvider.getConnection()){

                PreparedStatement pstmt = con.prepareStatement(INSERT_CATEGORIE, PreparedStatement.RETURN_GENERATED_KEYS);

                pstmt.setString(1, categorie.getLibelle());
                pstmt.executeUpdate();

                ResultSet rs = pstmt.getGeneratedKeys();

                if(rs.next()) {
                    categorie.setNoCategorie(rs.getInt(1));
                }

                pstmt.close();
                rs.close();


            }catch (Exception ex){
                ex.printStackTrace();
                BusinessException businessException = new BusinessException();
                businessException.ajouterErreur(CodesResultatDAL.INSERT_OBJET_ECHEC);
                throw businessException;
            }
        }

        public void update(Categorie categorie) throws BusinessException{

            if(categorie.getNoCategorie()==null || categorie.getNoCategorie()==0) {
                BusinessException businessException = new BusinessException();
                businessException.ajouterErreur(CodesResultatDAL.CATEGORIE_NULL_ECHEC);
                throw businessException;
            }

            try(Connection con = ConnectionProvider.getConnection()){

                PreparedStatement pstmt = con.prepareStatement(UPDATE_CATEGORIE);

                pstmt.setString(1, categorie.getLibelle());
                pstmt.setInt(2, categorie.getNoCategorie());

                pstmt.executeUpdate();

                pstmt.close();

            }catch (Exception ex){
                ex.printStackTrace();
                BusinessException businessException = new BusinessException();
                businessException.ajouterErreur(CodesResultatDAL.UPDATE_CATEGORIE_ECHEC);
                throw businessException;
            }
        }

        public void delete(Categorie categorie) throws BusinessException {

            if(categorie.getNoCategorie()==null || categorie.getNoCategorie()==0) {
                BusinessException businessException = new BusinessException();
                businessException.ajouterErreur(CodesResultatDAL.CATEGORIE_NULL_ECHEC);
                throw businessException;
            }

            try(Connection con = ConnectionProvider.getConnection()){

               PreparedStatement pstmt = con.prepareStatement(DELETE_CATEGORIE);

               pstmt.setInt(1, categorie.getNoCategorie());
               pstmt.executeUpdate();

               pstmt.close();

            }catch (Exception ex){
                ex.printStackTrace();
                BusinessException businessException = new BusinessException();
                businessException.ajouterErreur(CodesResultatDAL.DELETE_CATEGORIE_ECHEC);
                throw businessException;
            }
        }

        public Categorie selectByLibelle(String libelle) throws BusinessException {
            Categorie c = new Categorie();

            try (Connection cnx = ConnectionProvider.getConnection()) {
                PreparedStatement stmt = cnx.prepareStatement(SELECT_BY_LIBELLE);
                stmt.setString(1, libelle);
                ResultSet result = stmt.executeQuery();

                if(result.next()) {
                    c.setNoCategorie(result.getInt("no_categorie"));
                }

                stmt.close();
                result.close();

            } catch (Exception ex) {
                ex.printStackTrace();
                BusinessException businessException = new BusinessException();
                businessException.ajouterErreur(CodesResultatDAL.SELECT_CATEGORIE_BY_LIBELLE_ECHEC);
                throw businessException;
            }
            return c;
        }

    @Override
    public List<Categorie> selectCategories() throws BusinessException {
            List<Categorie> categories = new ArrayList<>();

        try(Connection con = ConnectionProvider.getConnection()){
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(SELECT_ALL);

            while(rs.next()) {
                Categorie categorie = new Categorie(rs.getInt(1), rs.getString(2));
                categories.add(categorie);
            }
        } catch(Exception ex) {
            ex.printStackTrace();
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesResultatDAL.SELECT_ALL_CATEGORIES_ECHEC);
            throw businessException;
        }

        return categories;
    }

    @Override
    public boolean CategorieIsInBase(String libelle) throws BusinessException {
        boolean IsInBase = false;

        try (Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_LIBELLE_BOOLEAN);
            pstmt.setString(1,libelle);

            ResultSet rs = pstmt.executeQuery();

            if(rs.next()) {
                IsInBase = true;
            }

            rs.close();
            pstmt.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        return IsInBase;
    }


}
