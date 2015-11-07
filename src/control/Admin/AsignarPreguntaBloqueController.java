/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.Admin;

import DAO.TbbloqueJpaController;
import DAO.TbbloquesxpreguntaJpaController;
import DAO.TbpreguntaJpaController;
import java.net.URL;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import modelo.Tbbloque;
import modelo.Tbbloquesxpregunta;
import modelo.Tbpregunta;

/**
 * FXML Controller class
 *
 * @author Cecilia Segura
 */
public class AsignarPreguntaBloqueController implements Initializable {

    @FXML private Button preguntabt;
    @FXML private Button bloquebt;
    @FXML private Button asignarbt;
    
 
    
    @FXML private ComboBox<String> bloque;
    @FXML private ComboBox<String> pregunta;
    
    List<Tbbloque> resultsbloque;
    List<Tbpregunta> resultspregunta;
    List<Tbbloquesxpregunta> resultsbloqxpreg;
    
  
    // Declaramos la tabla y las columnas
    @FXML
    private TableView tablabloque;
    private TableColumn titulo;
  

    // Datos pregunta
    ObservableList<ObservableList> tbpregunta;
    TbpreguntaJpaController preguntabd = new TbpreguntaJpaController(); 
   
    //Datos bloque
    ObservableList<ObservableList> tbbloque;
    TbbloqueJpaController bloquebd = new TbbloqueJpaController(); 
    Date fecha = new Date();
    DateFormat df1 = DateFormat.getDateInstance(DateFormat.SHORT);
    
    // Datos Clase 
    ObservableList<ObservableList> Tbbloquesxpregunta;
    Tbbloquesxpregunta bloqxpre = new  Tbbloquesxpregunta();
    TbbloquesxpreguntaJpaController bloqxpregbd = new  TbbloquesxpreguntaJpaController();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Cargar();
        CargarTabla();
        CargarComboBloq();
        pregunta.setDisable(true);
        
    }

    @FXML
    private void asignar(ActionEvent event) {
        Tbbloque bloq = new Tbbloque();
        Tbpregunta preg = new Tbpregunta();
        
        
        if(bloque.getValue() != null && pregunta.getValue() != null){
            for(int i=0; i<resultsbloque.size();i++){
                if(bloque.getValue().equals(resultsbloque.get(i).getNombre())){
                    bloq = resultsbloque.get(i);
                }
            }
            for(int i=0; i<resultspregunta.size();i++){
                if(pregunta.getValue().equals(resultspregunta.get(i).getNombre())){
                    preg = resultspregunta.get(i);
                }
            }
            bloqxpre.setIdbloque(bloq);
            bloqxpre.setIdpregunta(preg);
            bloqxpregbd.create(bloqxpre);
        }
        limpiar();
    }

    @FXML
    private void genararb(ActionEvent event) {
        tablabloque.getColumns().clear();
        String[] titulos = {
            "Numero",
            "bloque",
            "Fecha De Creacion",
            "Fecha De Modificacion",
        };

        for (int i = 0; i < titulos.length; i++) {
            final int j = i;
            this.titulo = new TableColumn(titulos[i]);
            this.titulo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> parametro) {
                    return new SimpleStringProperty((String) parametro.getValue().get(j));
                }
            });
            tablabloque.getColumns().addAll(titulo);
            // Asignamos un tamaño a ls columnnas
            titulo.setMinWidth(120);

            // Centrar los datos de la tabla
            titulo.setCellFactory(new Callback<TableColumn<String, String>, TableCell<String, String>>() {
                @Override
                public TableCell<String, String> call(TableColumn<String, String> p) {
                    TableCell cell = new TableCell() {
                        @Override
                        protected void updateItem(Object t, boolean bln) {
                            if (t != null) {
                                super.updateItem(t, bln);
                                setText(t.toString());
                                setAlignment(Pos.CENTER); //Setting the Alignment
                            }
                        }
                    };
                    return cell;
                }
            });
        }

        tbbloque = FXCollections.observableArrayList();
        for (Tbbloque bloq : resultsbloque) {
            ObservableList<String> row = FXCollections.observableArrayList();
            row.add(bloq.getId().toString());
            row.add(bloq.getNombre());
            row.add(df1.format(bloq.getFechacreacion()));
            System.out.print(bloq.getFechacreacion());
            String fecha = bloq.getFechamodificacion() == null ? bloq.getFechamodificacion()+"":df1.format(bloq.getFechamodificacion());
            row.add(fecha);
           tbbloque.addAll(row);
        }
        tablabloque.setItems(tbbloque);
    }

    @FXML
    private void genararp(ActionEvent event) {
        tablabloque.getColumns().clear();
        EntityManager em = preguntabd.getEntityManager();
        TypedQuery<Tbpregunta> query = em.createNamedQuery("Tbpregunta.findAll", Tbpregunta.class);
        List<Tbpregunta> results = query.getResultList();

        String[] titulos = {
            "Numero",
            "Pregunta",
            "Fecha De Creacion",
            "Fecha De Modificacion",

        };

        for (int i = 0; i < titulos.length; i++) {
            final int j = i;
            this.titulo = new TableColumn(titulos[i]);
            this.titulo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> parametro) {
                    return new SimpleStringProperty((String) parametro.getValue().get(j));
                }
            });
            tablabloque.getColumns().addAll(titulo);
            // Asignamos un tamaño a ls columnnas
            titulo.setMinWidth(170);

            // Centrar los datos de la tabla
            titulo.setCellFactory(new Callback<TableColumn<String, String>, TableCell<String, String>>() {
                @Override
                public TableCell<String, String> call(TableColumn<String, String> p) {
                    TableCell cell = new TableCell() {
                        @Override
                        protected void updateItem(Object t, boolean bln) {
                            if (t != null) {
                                super.updateItem(t, bln);
                                setText(t.toString());
                                setAlignment(Pos.CENTER); //Setting the Alignment
                            }
                        }
                    };
                    return cell;
                }
            });
        }

        tbpregunta = FXCollections.observableArrayList();
        for (Tbpregunta pregu : resultspregunta) {
            ObservableList<String> row = FXCollections.observableArrayList();
            row.add(pregu.getId().toString());
            row.add(pregu.getNombre());
            row.add(df1.format(pregu.getFechacreacion()));
            System.out.print(pregu.getFechacreacion());
            String fecha = pregu.getFechamodificacion() == null ? pregu.getFechamodificacion()+"":df1.format(pregu.getFechamodificacion());
            row.add(fecha);


            
            
           tbpregunta.addAll(row);
          
        }
        tablabloque.setItems(tbpregunta);
    }

    @FXML
    private void seleccionarencuesta(MouseEvent event) {
    }

    private void Cargar(){
        EntityManager em = preguntabd.getEntityManager();
        TypedQuery<Tbpregunta> query = em.createNamedQuery("Tbpregunta.findAll", Tbpregunta.class);
        resultspregunta = query.getResultList();
        em.close();
        
        em = bloquebd.getEntityManager();
        TypedQuery<Tbbloque> query2 = em.createNamedQuery("Tbbloque.findAll", Tbbloque.class);
        resultsbloque = query2.getResultList();
        em.close();
        
        em = bloqxpregbd.getEntityManager();
        TypedQuery<Tbbloquesxpregunta> query3 = em.createNamedQuery("Tbbloquesxpregunta.findAll", Tbbloquesxpregunta.class);
        resultsbloqxpreg = query3.getResultList();
        em.close();
        
    }
    
    private void CargarTabla(){
        tablabloque.getColumns().clear();
        String[] titulos = {
            "Bloque",
            "Pregunta",
        };

        for (int i = 0; i < titulos.length; i++) {
            final int j = i;
            this.titulo = new TableColumn(titulos[i]);
            this.titulo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> parametro) {
                    return new SimpleStringProperty((String) parametro.getValue().get(j));
                }
            });
            tablabloque.getColumns().addAll(titulo);
            // Asignamos un tamaño a ls columnnas
            if (i==0){
            titulo.setMinWidth(120);
            }else{
                titulo.setMinWidth(450);
            }

            // Centrar los datos de la tabla
            titulo.setCellFactory(new Callback<TableColumn<String, String>, TableCell<String, String>>() {
                @Override
                public TableCell<String, String> call(TableColumn<String, String> p) {
                    TableCell cell = new TableCell() {
                        @Override
                        protected void updateItem(Object t, boolean bln) {
                            if (t != null) {
                                super.updateItem(t, bln);
                                setText(t.toString());
                                setAlignment(Pos.CENTER); //Setting the Alignment
                            }
                        }
                    };
                    return cell;
                }
            });
        }

        tbbloque = FXCollections.observableArrayList();
        for (Tbbloquesxpregunta bloqxpreg : resultsbloqxpreg) {
            ObservableList<String> row = FXCollections.observableArrayList();
            row.add(bloqxpreg.getIdbloque().getNombre());
            row.add(bloqxpreg.getIdpregunta().getNombre());
           tbbloque.addAll(row);
        }
        tablabloque.setItems(tbbloque);
        
    }

    private void CargarComboBloq() {
        for(int i=0; i<resultsbloque.size();i++){
            bloque.getItems().add(resultsbloque.get(i).getNombre());
        }
    }

    private void CargarComboPreg() {
        pregunta.getItems().clear();
        for(int i=0; i<resultspregunta.size();i++){
            Boolean agregar = true;
            for(int j=0; j<resultsbloqxpreg.size();j++){
                if(resultsbloqxpreg.get(j).getIdpregunta().getNombre().equals(resultspregunta.get(i).getNombre()) && resultsbloqxpreg.get(j).getIdbloque().getNombre().equals(bloque.getValue())){
                    agregar = false;
                }
            }
            if(agregar){
                pregunta.getItems().add(resultspregunta.get(i).getNombre());
            }
            
        }
    }
    
    @FXML
    private void AccionBloque(ActionEvent event) {
        pregunta.setValue(null);
        pregunta.setDisable(false);
        CargarComboPreg();
    }
    
    private void limpiar(){
        bloque.setValue(null);
        pregunta.setValue(null);
        pregunta.setDisable(true);
    }
    
}