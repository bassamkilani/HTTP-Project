/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package httpjavafx;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 *
 * @author bassamkilani
 */
public class FXMLDocumentController extends Window implements Initializable {
    public static String php_url = "http://localhost/http_test/";
    public static String servlet = "http://localhost:8080/JSP_server/Servlet";
    public static String Username = "";
    public static String Password = "";
    public static int curr_server = 2;
    
    @FXML
    private Label noOfCasesHealed, noOfCasesActive, StaticNoCases, StaticNoHealed,RangeNoCases,RangeNoHealed;
    
    @FXML
    private DatePicker  StaticDate,StartDate,EndDate,NewDate,UpdateDate;
    
    @FXML
    private ChoiceBox firstCityCB, secondCityCB, cityCB1, cityCB2, serverCB;
    
    @FXML
    private Pane loginPane;
     
    @FXML
    private TextField newActive,newHealed,UpdateActive,UpdateHealed,ID,usernameTF,passwordTF;

    
    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        String user = usernameTF.getText();
        String pass = passwordTF.getText();
        
        if(user.isEmpty() || pass.isEmpty()){
            System.out.println("Enter all fields");
            return;
        }
        int val = login(user, pass)-'0';
        System.out.println(val);
        if(val == 1){
            Username = user;
            Password = pass;
            loginPane.setManaged(false);
            loginPane.setVisible(false);
        }
        else{
            System.out.println("Invalid Username or password");
        }
        
    }
 
    
    @FXML
    private void handleUdpateAction(ActionEvent event) throws ParseException, IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd");
        HashMap<String,List<Integer>> vals;
      
        String city = cityCB2.getValue().toString();
        String date = UpdateDate.getValue().format(formatter).toString();
        int active , healed;

        if(ID.getText().isEmpty()){
            System.out.println("Invalid ID");
            return;
        }
        int id = Integer.parseInt(ID.getText());
        if(UpdateActive.getText().isEmpty())
             active = -1;
        else
             active = Integer.parseInt(UpdateActive.getText());
        
        if(UpdateHealed.getText().isEmpty())
             healed = -1;
        else
             healed = Integer.parseInt(UpdateHealed.getText());
         Edit_Record(id, city, date, active, healed);

    }
    
    
    
    @FXML
    private void handleInsertAction(ActionEvent event) throws ParseException, IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd");
        HashMap<String,List<Integer>> vals;
          if(newActive.getText().isEmpty() || newHealed.getText().isEmpty() || cityCB1.getValue().toString().isEmpty() || NewDate.getValue().toString().isEmpty()){
            System.out.println("you must fill all fields ");
            return;
          }
        String city = cityCB1.getValue().toString();
        String date = NewDate.getValue().format(formatter).toString();
        int active = Integer.parseInt(newActive.getText()), 
        healed = Integer.parseInt(newHealed.getText());

            Insert_Record(city, date, active, healed);
    }
    
    
    @FXML
    private void handleRangeAction(ActionEvent event) throws ParseException, IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd");
        String choice = secondCityCB.getValue().toString();
        HashMap<String,List<Integer>> vals;     
        String start = StartDate.getValue().format(formatter).toString();
        String end = EndDate.getValue().format(formatter).toString();           
        
        if(choice.equals("Palestine")){
            vals = get_total_range(start,end);
        }
        else{
            vals = get_Range(start,end,choice);
        }
//        int active = vals.get(end).get(0)-vals.get(start).get(0);
//        int healed = vals.get(end).get(1)-vals.get(start).get(1);
        
        LocalDate next = LocalDate.parse(start).minusDays(1);
        while ((next = next.plusDays(1)).isBefore(LocalDate.parse(end).plusDays(1))) {
            System.out.println(next.toString() + ":" + vals.get(next.toString()).get(0) + "," + vals.get(next.toString()).get(1));
        }
//        RangeNoCases.setText(Integer.toString(active));
//        RangeNoHealed.setText(Integer.toString(healed));
    }
    
    @FXML
    private void handleStaticAction(ActionEvent event) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd");
        String choice = firstCityCB.getValue().toString();
        List<Integer> vals;
        if(StaticDate.getValue().toString().isEmpty())
            return;
//        
        String date = StaticDate.getValue().format(formatter).toString();
        
        if(choice.equals("Palestine")){
            vals = get_total(date);
        }
        else{
            vals = get_City(choice,date);
        }
        
        StaticNoCases.setText(vals.get(0).toString());
        StaticNoHealed.setText(vals.get(1).toString());

    }

    
    public List<Integer> get_total(String date) throws MalformedURLException, IOException {
       List <Integer> vals = new ArrayList<Integer>();
    String dest_url = null;
    switch(curr_server){
        case 1 :
            dest_url = php_url;
            break;
        case 2:
            dest_url = servlet;
            break; 
    }
    String request = dest_url + "?total=?&date="+date;
    
    URL url = new URL(request);
    
    URLConnection connect = url.openConnection();
    connect.setAllowUserInteraction(true);
    connect.setDoInput(true);
    connect.setDoOutput(true);
    InputStream in = connect.getInputStream();
     int c;
     String  Active= "",Healed = "";
     if((c = in.read()) != -1){
     while(c != ','){
         Active += (char)c;
         c = in.read();
     }
     while((c = in.read())!=-1){
         Healed += (char)c;
     }

//      System.out.println(Active+" , "+Healed); 
      vals.add(Integer.parseInt(Active.trim()));
      vals.add(Integer.parseInt(Healed.trim()));
     }
     return vals;
    }
    
    public HashMap<String,List<Integer>> get_total_range (String start,String end) throws ParseException, IOException{
        HashMap<String,List<Integer>> Values = new HashMap<String, List<Integer>>();
        
          LocalDate first = LocalDate.parse(start),
          last   = LocalDate.parse(end);

        LocalDate next = first.minusDays(1);
        while ((next = next.plusDays(1)).isBefore(last.plusDays(1))) {    
            String the_date = next.toString();
            List <Integer> x = get_total(the_date);
            Values.put(the_date,x);
        }
        
//      next = first.minusDays(1);
//        while ((next = next.plusDays(1)).isBefore(last.plusDays(1))) {
//            System.out.println(next.toString() + ":" + Values.get(next.toString()).get(0) + "," + Values.get(next.toString()).get(1));
//        }

    return Values;
    }
    
    public HashMap<String,List<Integer>> get_Range(String start,String end,String city) throws MalformedURLException, IOException{
        HashMap<String,List<Integer>> Values = new HashMap<String, List<Integer>>();
        
          LocalDate first = LocalDate.parse(start),
          last   = LocalDate.parse(end);

        LocalDate next = first.minusDays(1);
        while ((next = next.plusDays(1)).isBefore(last.plusDays(1))) {    
            String the_date = next.toString();
            List <Integer> x = get_City(city,the_date);
            Values.put(the_date,x);
        }
        
//        next = first.minusDays(1);
//        while ((next = next.plusDays(1)).isBefore(last.plusDays(1))) {
//            System.out.println(next.toString() + ":" + Values.get(next.toString()).get(0) + "," + Values.get(next.toString()).get(1));
//        }
    return Values;
    }
    
    public  List<Integer> get_City(String city,String date) throws MalformedURLException, IOException{
        
        List <Integer> vals = new ArrayList<Integer>();
        String dest_url = null;
        switch(curr_server){
            case 1 :
                dest_url = php_url;
                break;
            case 2:
                dest_url = servlet;
                break; 
        }
    String request = dest_url + "?city="+city+"&date="+date;
    
    URL url = new URL(request);
    
    URLConnection connect = url.openConnection();
    connect.setAllowUserInteraction(true);
    connect.setDoInput(true);
    connect.setDoOutput(true);
    InputStream in = connect.getInputStream();
     int c;
     String Active = "",Healed = "";
     if((c = in.read()) != -1){
     while(c != ','){
         Active += (char)c;
         c = in.read();
     }
     while((c = in.read())!=-1){
         Healed += (char)c;
     }
     
      System.out.println(Active+" , "+Healed);    
     }
      vals.add(Integer.parseInt(Active.trim()));
      vals.add(Integer.parseInt(Healed.trim()));
      
      return vals;
      
    } 
    
    
    public  void Insert_Record(String city,String date,int active,int healed) throws MalformedURLException, IOException{
     String dest_url = null;
        switch(curr_server){
            case 1 :
                dest_url = php_url;
                break;
            case 2:
                dest_url = servlet;
                break; 
        }
        String request = dest_url + "?city="+city+"&date="+date+"&active="+active+"&healed="+healed;
    
        URL url = new URL(request);

        URLConnection connect = url.openConnection();
        connect.setAllowUserInteraction(true);
        connect.setDoInput(true);
        connect.setDoOutput(true);
        
        BufferedOutputStream out = new BufferedOutputStream(connect.getOutputStream());
   
        out.write(request.getBytes());
        out.close();
        
        InputStream in = connect.getInputStream();
        int c;
        while((c = in.read()) != -1){
            System.out.print((char)c);
        }
    }
    
    public  void Edit_Record(int id,String city,String date , int active , int healed) throws MalformedURLException, IOException{
         String dest_url = null;
        switch(curr_server){
            case 1 :
                dest_url = php_url;
                break;
            case 2:
                dest_url = servlet;
                break; 
        }
        dest_url+="?id="+id;
        
        if(!city.isEmpty()){
            dest_url+="&city="+city; 
            
        }
        if(!date.isEmpty()){
            dest_url+="&date="+date; 
        }
        if(active!=-1){
            dest_url+="&active="+active; 
        }  
        if(healed!=-1){
            dest_url+="&healed="+healed; 
        }
        URL url = new URL(dest_url);

        URLConnection connect = url.openConnection();
        connect.setAllowUserInteraction(true);
        connect.setDoInput(true);
        connect.setDoOutput(true);
        
        BufferedOutputStream out = new BufferedOutputStream(connect.getOutputStream());
   
        out.write(dest_url.getBytes());
        out.close();
        
        InputStream in = connect.getInputStream();
        int c;
        while((c = in.read()) != -1){
            System.out.print((char)c);
        }
}
    
      public static char login (String name ,String pass) throws MalformedURLException, IOException{
         String dest_url = null;
        switch(curr_server){
            case 1 :
                dest_url = php_url;
                break;
            case 2:
                dest_url = servlet;
                break; 
        }
        String request = dest_url + "?name="+name+"&pass="+pass;
    
        URL url = new URL(request);

        URLConnection connect = url.openConnection();
        connect.setAllowUserInteraction(true);
        connect.setDoInput(true);
        connect.setDoOutput(true);
        
        BufferedOutputStream out = new BufferedOutputStream(connect.getOutputStream());
   
        out.write(request.getBytes());
        out.close();
        
        InputStream in = connect.getInputStream();
        int c,result = 1;
        char what = 'a';
//        c = in.read();
//        what = (char)c;
       c = in.read();
//        System.out.print((char)c);
       return (char)c;
//       while((c = in.read()) != -1){
//         what = (char)c;
//        System.out.print((char)c);
//        }
//       System.out.println(what);
//        return result;
      }
      
      @FXML
    private void graphHandler(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("graph.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Range Graph");
        //defining the axes
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Day");
        yAxis.setLabel("# Cases");
        //creating the chart
        final LineChart<String,Number> lineChart = new LineChart<String,Number>(xAxis,yAxis);
                
        lineChart.setTitle("Cases Graph");
        XYChart.Series<String,Number> series1 = new XYChart.Series();
        series1.setName("Active Cases");
        XYChart.Series<String,Number> series2 = new XYChart.Series();
        series2.setName("Healed Cases");
        
        // forloop for 
        
        for( ; ; ){
            series1.getData().add(new XYChart.Data("3/5/3", 23));
        }
        
//        series1.getData().add(new XYChart.Data("3/5/3", 23));
//        series1.getData().add(new XYChart.Data("2", 14));
//        series1.getData().add(new XYChart.Data("3", 15));
//        series1.getData().add(new XYChart.Data("4", 24));
//        series1.getData().add(new XYChart.Data("5", 34));
//        series1.getData().add(new XYChart.Data("6", 36));
//        series1.getData().add(new XYChart.Data("7", 22));
//        series1.getData().add(new XYChart.Data("8", 45));
//        series1.getData().add(new XYChart.Data("9", 43));
//        series1.getData().add(new XYChart.Data("10", 17));
//        series1.getData().add(new XYChart.Data("11", 29));
//        series1.getData().add(new XYChart.Data("12", 25));
//        
//        series2.getData().add(new XYChart.Data("1", 65));
//        series2.getData().add(new XYChart.Data("2", 43));
//        series2.getData().add(new XYChart.Data("3", 23));
//        series2.getData().add(new XYChart.Data("4", 24));
//        series2.getData().add(new XYChart.Data("5", 34));
//        series2.getData().add(new XYChart.Data("6", 36));
//        series2.getData().add(new XYChart.Data("7", 22));
//        series2.getData().add(new XYChart.Data("8", 5));
//        series2.getData().add(new XYChart.Data("9", 43));
//        series2.getData().add(new XYChart.Data("10", 6));
//        series2.getData().add(new XYChart.Data("11", 12));
//        series2.getData().add(new XYChart.Data("12", 53));
        
        Scene scene  = new Scene(lineChart,800,600);
        lineChart.getData().add(series1);
        lineChart.getData().add(series2);
       
        stage.setScene(scene);
        stage.show();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        loginPane.setManaged(true);
        loginPane.setVisible(true);
        
        StaticDate.setValue(LocalDate.now());
        StartDate.setValue(LocalDate.now());
        EndDate.setValue(LocalDate.now());
        NewDate.setValue(LocalDate.now());
        UpdateDate.setValue(LocalDate.now());
        
        firstCityCB.getItems().removeAll(firstCityCB.getItems());
        firstCityCB.getItems().addAll("Palestine", "Nablus", "Hebron", "Jerusalem", "Jenin", "Ramallah", "Tulkarem");
        firstCityCB.getSelectionModel().select("Palestine");
        
        secondCityCB.getItems().removeAll(secondCityCB.getItems());
        secondCityCB.getItems().addAll("Palestine", "Nablus", "Hebron", "Jerusalem", "Jenin", "Ramallah", "Tulkarem");
        secondCityCB.getSelectionModel().select("Palestine");
        
        cityCB1.getItems().removeAll(cityCB1.getItems());
        cityCB1.getItems().addAll("Nablus", "Hebron", "Jerusalem", "Jenin", "Ramallah", "Tulkarem");
        cityCB1.getSelectionModel().select("Nablus");
        
        cityCB2.getItems().removeAll(cityCB2.getItems());
        cityCB2.getItems().addAll("Nablus", "Hebron", "Jerusalem", "Jenin", "Ramallah", "Tulkarem");
        cityCB2.getSelectionModel().select("Nablus");
        
        serverCB.getItems().removeAll(serverCB.getItems());
        serverCB.getItems().addAll("PHP", "Servlet");
        serverCB.getSelectionModel().select("PHP");
    }    
    
}
