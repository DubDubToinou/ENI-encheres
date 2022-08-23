package fr.eni.encheres.dal.jdbc;

import fr.eni.encheres.bo.Articles;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.dal.CategorieDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CategorieDAOJdbcImpl implements CategorieDAO {

        String sqlInsert = ("INSERT INTO Categories (libelle) VALUES(?)");
        String updateCategorie = ("UPDATE Categories SET libelle ? WHERE no_categorie ?");

        String deleteCategorie = ("DELETE FROM Categories WHERE no_categorie ?");

        public void insert(Categorie categorie){

            try(Connection con = ConnectionProvider.getConnection()){

                PreparedStatement pstmt = con.prepareStatement(sqlInsert);

                pstmt.setString(1, categorie.getLibelle());

                pstmt.executeUpdate();

                pstmt.close();


            }catch (SQLException ex){
                ex.printStackTrace();
            }
        }

        public void update(Categorie categorie){

            try(Connection con = ConnectionProvider.getConnection()){

                PreparedStatement pstmt = con.prepareStatement(updateCategorie);

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

               PreparedStatement pstmt = con.prepareStatement(deleteCategorie);

               pstmt.setInt(1, categorie.getNoCategorie());
               pstmt.executeUpdate();

               pstmt.close();

            }catch (SQLException ex){
                ex.printStackTrace();
            }
        }




}
