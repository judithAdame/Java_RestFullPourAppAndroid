/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adame.www;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Path("database")
public class GenericResource {

    @Context
    private UriInfo context;


    public GenericResource() {
    }

//http://localhost:8080/WebApplication1/webresources/database/employeeList
    @GET
    @Path("employeeList")
    @Produces("application/json")
    public String getXml() {
        JSONArray mainJSON = new JSONArray();
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@144.217.163.57:1521:XE", "hr", "inf5180");
            Statement stm = con.createStatement();
            String sql = "select * from employees";

            ResultSet rs = stm.executeQuery(sql);

            JSONObject singleEmployee = new JSONObject();

            int id, manager, department;
            String fname, lname, email, phone, hireDate, job;
            double salary, commision;
            System.out.println(rs.toString());
            while (rs.next()) {

                id = rs.getInt("employee_id");
                fname = rs.getString("first_name");
                lname = rs.getString("last_name");
                email = rs.getString("email");
                phone = rs.getString("phone_number");
                hireDate = rs.getString("hire_date");
                job = rs.getString("job_id");
                salary = rs.getDouble("salary");
                commision = rs.getDouble("commission_pct");
                manager = rs.getInt("manager_id");
                department = rs.getInt("department_id");

                singleEmployee.accumulate("id", id);
                singleEmployee.accumulate("fname", fname);
                singleEmployee.accumulate("lname", lname);
                singleEmployee.accumulate("email", email);
                singleEmployee.accumulate("phone", phone);
                singleEmployee.accumulate("hireDate", hireDate);
                singleEmployee.accumulate("job", job);
                singleEmployee.accumulate("salary", salary);
                singleEmployee.accumulate("commision", commision);
                singleEmployee.accumulate("manager", manager);
                singleEmployee.accumulate("department", department);

                mainJSON.add(singleEmployee);
                singleEmployee.clear();
            }

            rs.close();
            stm.close();
            con.close();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("*--*-*-*-*--*-*-*-*--*-*-*-*--*-*-*-"+mainJSON.toString());

        return mainJSON.toString();
    }

    //http://localhost:8080/WebApplication1/webresources/database/singleEmployee&1
    @GET
    @Path("singleEmployee&{id}")
    @Produces("application/json")
    public String getXml1(@PathParam("id") int employeeID) {
        JSONObject singleEmployee = new JSONObject();
        singleEmployee.accumulate("Status", "Error");
        
        singleEmployee.accumulate("Message", "Employee id not exists");        
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@144.217.163.57:1521:XE", "hr", "inf5180");
            Statement stm = con.createStatement();
            String sql = "select * from employees where employee_id=" + employeeID;
            ResultSet rs = stm.executeQuery(sql);

            int id, manager, department;
            String fname, lname, email, phone, hireDate, job;
            double salary, commision;

            while (rs.next()) {
                singleEmployee.clear();
                id = rs.getInt("employee_id");
                fname = rs.getString("first_name");
                lname = rs.getString("last_name");
                email = rs.getString("email");
                phone = rs.getString("phone_number");
                hireDate = rs.getString("hire_date");
                job = rs.getString("job_id");
                salary = rs.getDouble("salary");
                commision = rs.getDouble("commission_pct");
                manager = rs.getInt("manager_id");
                department = rs.getInt("department_id");

                singleEmployee.accumulate("id", id);
                singleEmployee.accumulate("fname", fname);
                singleEmployee.accumulate("lname", lname);
                singleEmployee.accumulate("email", email);
                singleEmployee.accumulate("phone", phone);
                singleEmployee.accumulate("hireDate", hireDate);
                singleEmployee.accumulate("job", job);
                singleEmployee.accumulate("salary", salary);
                singleEmployee.accumulate("commision", commision);
                singleEmployee.accumulate("manager", manager);
                singleEmployee.accumulate("department", department);

                rs.close();
                stm.close();
                con.close();
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
        }

        return singleEmployee.toString();
    }

}
