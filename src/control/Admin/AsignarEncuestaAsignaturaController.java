/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.Admin;

import DAO.TbencuestaJpaController;
import DAO.TbencuestaxasignaturaJpaController;
import DAO.TbgrupoxasignaturaxprofesorJpaController;
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
import modelo.Tbencuesta;
import modelo.Tbencuestaxasignatura;
import modelo.Tbgrupoxasignaturaxprofesor;

/**
 * FXML Controller class
 *
 * @author Cecilia Segura
 */
public class AsignarEncuestaAsignaturaController implements Initializable {
// Declaramos los botones
    @FXML private Button asignarbt;
    @FXML private Button asignaturabt;
    @FXML private Button encuestabt;
    
    
       
   // Declaramos la tabla y las columnas
   @FXML
   private TableView tablaencuesta;
   private TableColumn titulo;
   
 
       
    
   //Otros
   ObservableList<ObservableList> tbencuesta;
   ObservableList<ObservableList> tbasignatura;
   ObservableList<ObservableList> tbencuestaxasignatura;
   
   
   // datos Asignatura
   Tbgrupoxasignaturaxprofesor gpa = new   Tbgrupoxasignaturaxprofesor();
    TbgrupoxasignaturaxprofesorJpaController gpabd = new TbgrupoxasignaturaxprofesorJpaController(); 
    List< Tbgrupoxasignaturaxprofesor> resultasignatura;
   //datos encuesta
   
    @FXML
    ComboBox<String> encuesta;
   TbencuestaJpaController encuestabd = new TbencuestaJpaController();
    List<Tbencuesta> resultencuesta;
   
   // datos encuestaxasignatura
   Tbencuestaxasignatura exa = new Tbencuestaxasignatura();
   TbencuestaxasignaturaJpaController exabd = new TbencuestaxasignaturaJpaController();
   List< Tbencuestaxasignatura> results;
   
  
   //Fecha
   

   Date fecha = new Date();
   DateFormat df1 = DateFormat.getDateInstance(DateFormat.SHORT);
    @FXML
    private ComboBox<String> asignatura;
 
   
    public void generara (ActionEvent event) throws Exception {
       tablaencuesta.getColumns().clear();
        EntityManager em = gpabd.getEntityManager();
        TypedQuery<Tbgrupoxasignaturaxprofesor> query = em.createNamedQuery("Tbgrupoxasignaturaxprofesor.findAll", Tbgrupoxasignaturaxprofesor.class);
       List<Tbgrupoxasignaturaxprofesor> results   = query.getResultList();

        String[] titulos = {
            "Codigo",
            "Nombre",
            "Cedula",
            "Profesor",
            "Grupo",
            "Cupo",
            
            
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
            tablaencuesta.getColumns().addAll(titulo);
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

        tbasignatura = FXCollections.observableArrayList();
        for (Tbgrupoxasignaturaxprofesor gap : results) {
            ObservableList<String> row = FXCollections.observableArrayList();
            String codigo = gap.getIdasignatura().getCodigo();
            row.add(codigo);
            String nombre = gap.getIdasignatura().getNombre();
            row.add(nombre);
            String nombrep = gap.getCedula().getNombre();
            String apellidop = gap.getCedula().getApellido();
            row.add(gap.getCedula().getCedula().toString());
            row.add(nombrep + apellidop);
            row.add(gap.getIdgrupo().getIdentificacion().toString());
            row.add(gap.getCupo() + "");
          
            
           tbasignatura.addAll(row);
        }
        tablaencuesta.setItems(tbasignatura);
        em.close();
    }
   
    @FXML
   public void generare (ActionEvent event) throws Exception {
        tablaencuesta.getColumns().clear();
        EntityManager em = encuestabd.getEntityManager();
        TypedQuery<Tbencuesta> query = em.createNamedQuery("Tbencuesta.findAll", Tbencuesta.class);
        List<Tbencuesta> results = query.getResultList();

        String[] titulos = {
            "Numero",
            "Nombre",
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
            tablaencuesta.getColumns().addAll(titulo);
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

        tbencuesta = FXCollections.observableArrayList();
        for (Tbencuesta encuesta1 : results) {
            ObservableList<String> row = FXCollections.observableArrayList();
            row.add(encuesta1.getId().toString());
            row.add(encuesta1.getNombre());
            row.add(df1.format(encuesta1.getFechacreacion()));
           
            String fecha = encuesta1.getFechamodificacion() == null ? encuesta1.getFechamodificacion()+"":df1.format(encuesta1.getFechamodificacion());
            row.add(fecha);
           tbencuesta.addAll(row);
        }
        tablaencuesta.setItems(tbencuesta);
        em.close();
    }
   


 @FXML
   public void asignar (ActionEvent event) throws Exception {
       
        if(encuesta.getValue() != null && asignatura.getValue() != null ){
            Tbgrupoxasignaturaxprofesor gpa = new Tbgrupoxasignaturaxprofesor();
            Tbencuesta  encu  = new Tbencuesta();
            Tbencuestaxasignatura exa = new Tbencuestaxasignatura();
       
                   
            for(int i=0;i<resultencuesta.size();i++){
                if(resultencuesta.get(i).getId().toString().equals(encuesta.getValue())){
                    encu = resultencuesta.get(i);
                }
            }
        
            for(int i=0;i<resultasignatura.size();i++){
                if(resultasignatura.get(i).getIdasignatura().equals(asignatura.getValue())){
                    gpa = resultasignatura.get(i);
                }
            }
        
            Boolean existencia = false;
            for(int i=0;i<results.size();i++){
                if(results.get(i).getIdencuesta().getId().toString().equals(encu.getId().toString())  && results.get(i).getIdasignatura().getCodigo().equals(gpa.getIdasignatura().getCodigo())){
                    existencia = true;
                }
            }
        
            if(existencia == false){
                
                exa.setIdasignatura(gpa.getIdasignatura());
                exa.setIdencuesta(encu);
                exabd.create(exa);
          
            }
            cargarDatosTabla();
        }
        
        limpiar();
    
   }
   
   
    public void cargarcomboencuesta() {

        EntityManager em = encuestabd.getEntityManager();
        TypedQuery<Tbencuesta> query = em.createNamedQuery("Tbencuesta.findAll", Tbencuesta.class);

        resultencuesta = query.getResultList();

        em.close();
        for(int i=0;i<resultencuesta.size();i++){
           encuesta.getItems().add(resultencuesta.get(i).getId().toString());
        }
    }
    
   
    
    public void cargarcomboasignatura(){

        EntityManager em = gpabd.getEntityManager();
        TypedQuery<Tbgrupoxasignaturaxprofesor> query = em.createNamedQuery("Tbgrupoxasignaturaxprofesor.findAll", Tbgrupoxasignaturaxprofesor.class);

        resultasignatura = query.getResultList();

        em.close();
        for(int i=0;i<resultasignatura.size();i++){
           asignatura.getItems().add(resultasignatura.get(i).getIdasignatura().getCodigo().toString());
        }
    }
    
    public void limpiar() {
       asignatura.setValue(null);
       encuesta.setValue(null);
      
       
    }

    private void cargarDatosTabla() {
        tablaencuesta.getColumns().clear();
        EntityManager em = exabd.getEntityManager();
        TypedQuery<Tbencuestaxasignatura> query = em.createNamedQuery("Tbencuestaxasignatura.findAll", Tbencuestaxasignatura.class);
        List<Tbencuestaxasignatura> results = query.getResultList();

        String[] titulos = {
            "Numero",
            "Codigo",
            "Nombre",
            "Encuesta",
            "Nombre",
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
            tablaencuesta.getColumns().addAll(titulo);
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

        tbencuestaxasignatura = FXCollections.observableArrayList();
        for (Tbencuestaxasignatura exa : results) {
            ObservableList<String> row = FXCollections.observableArrayList();
            row.add(exa.getId().toString());
            row.add(exa.getIdasignatura().getCodigo());
            row.add(exa.getIdasignatura().getNombre());
            row.add(exa.getIdencuesta().getId().toString());
            row.add(exa.getIdencuesta().getNombre());        
           tbencuestaxasignatura.addAll(row);
        }
        tablaencuesta.setItems(tbencuestaxasignatura);
        em.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarcomboencuesta();
        cargarcomboasignatura();
        cargarDatosTabla();
    }    

    @FXML
    private void seleccionarencuesta(MouseEvent event) {
    }

    @FXML
    private void genarara(ActionEvent event) {
    }
    
}
