package fr.eni.encheres.dal.jdbc;

import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.dal.CategorieDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CategorieDAOJdbcImpl implements CategorieDAO {

        private static final String INSERT_CATEGORIE = "INSERT INTO Categories (libelle) VALUES(?)";
        private static final String UPDATE_CATEGORIE = "UPDATE Categories SET libelle ? WHERE no_categorie ?";
        private static final String DELETE_CATEGORIE = "DELETE FROM Categories WHERE no_categorie ?";

        public void insert(Categorie categorie){

            try(Connection con = ConnectionProvider.getConnection()){

                PreparedStatement pstmt = con.prepareStatement(INSERT_CATEGORIE, PreparedStatement.RETURN_GENERATED_KEYS);

                pstmt.setString(1, categorie.getLibelle());
                pstmt.executeUpdate();

                ResultSet rs = pstmt.getGeneratedKeys();

                if(rs.next()) {
                    categorie.setNoCategorie(rs.getInt("no_categorie"));
                }

                pstmt.close();
                rs.close();


            }catch (SQLException ex){
                ex.printStackTrace();
            }
        }

        public void update(Categorie categorie){

            try(Connection con = ConnectionProvider.getConnection()){

                PreparedStatement pstmt = con.prepareStatement(UPDATE_CATEGORIE);

                pstmt.setString(1, categorie.getLibelle());
                pstmt.setInt(2, categorie.getNoCategorie());

                pstmt.executeUpdate();

                pstmt.close();

            }catch (SQLException ex){
                ex.printStackTrace();
            }
        }

        public void delete(Categorie categorie){

            try(Connection con = ConnectionProvider.getConnection()){

               PreparedStatement pstmt = con.prepareStatement(DELETE_CATEGORIE);

               pstmt.setInt(1, categorie.getNoCategorie());
               pstmt.executeUpdate();

               pstmt.close();

            }catch (SQLException ex){
                ex.printStackTrace();
            }
        }




}
