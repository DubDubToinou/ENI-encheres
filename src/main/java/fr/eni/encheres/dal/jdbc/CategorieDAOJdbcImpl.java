package fr.eni.encheres.dal.jdbc;

import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.dal.CategorieDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CategorieDAOJdbcImpl implements CategorieDAO {

        String sqlInsert = ("INSERT INTO Categories (libelle) VALUES(?)");

        public void insert(Categorie categorie){

            try(Connection con = ConnectionProvider.getConnection()){

                PreparedStatement pstmt = con.prepareStatement(sqlInsert);

                pstmt.setString(1, categorie.getLibelle());

                pstmt.executeUpdate();


            }catch (SQLException ex){
                ex.printStackTrace();
            }
        }




}
