/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.Admin;

import DAO.TbasignaturaJpaController;
import DAO.TbencuestasxusuarioJpaController;
import DAO.TbencuestaxasignaturaJpaController;
import DAO.TbgrupoxasignaturaxprofesorJpaController;
import DAO.TbusuarioJpaController;
import java.net.URL;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import modelo.Tbasignatura;
import modelo.Tbencuestasxusuario;
import modelo.Tbencuestaxasignatura;
import modelo.Tbgrupoxasignaturaxprofesor;
import modelo.Tbusuario;

/**
 * FXML Controller class
 *
 * @author Andres
 */
public class AsignarEstudianteAsignaturaController implements Initializable {
    @FXML
    private TableView tablaasignatura;
    @FXML
    private ComboBox<String> estudiante;
    @FXML
    private ComboBox<String> asignatura;
    List<Tbusuario> usuario;
    List<Tbasignatura> materias;
    List<Tbencuestasxusuario> usuxmat;
    TbusuarioJpaController usuariobd = new TbusuarioJpaController();
    TbasignaturaJpaController materiasbd = new TbasignaturaJpaController();
    TbencuestasxusuarioJpaController usuxmatbd = new TbencuestasxusuarioJpaController();
    TableColumn titulo;
    ObservableList<ObservableList> tbasignatura;
 
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Cargar();
        CargarTabla();
        LlenarEstudiante();
    }    

    @FXML
    private void AccionEstudiante(ActionEvent event) {
        asignatura.setDisable(false);
        Llenarasignatura(estudiante.getValue());
        
    }

    @FXML
    private void Asignar(ActionEvent event) {
        TbencuestasxusuarioJpaController usu = new TbencuestasxusuarioJpaController();
        Tbencuestasxusuario mate = new Tbencuestasxusuario();
        for(int i=0; i<usuario.size();i++){
            if(estudiante.getValue().equals(usuario.get(i).getCodigo().toString())){
                mate.setCodigousuario(usuario.get(i));
            }
        }
        
        mate.setComentario(" ");
        Date d = new Date();
        mate.setFecha(d);
        TbencuestaxasignaturaJpaController encuxasigbd = new TbencuestaxasignaturaJpaController();
        List<Tbencuestaxasignatura> encuasig;
        EntityManager em = encuxasigbd.getEntityManager();
        TypedQuery<Tbencuestaxasignatura> query = em.createNamedQuery("Tbencuestaxasignatura.findAll", Tbencuestaxasignatura.class);
        encuasig = query.getResultList();
        em.close();
        for(int i=0; i<encuasig.size();i++){
            if(encuasig.get(i).getIdasignatura().getCodigo().equals(asignatura.getValue())){
                mate.setIdencuestaxasignatura(encuasig.get(i));
            }
        }
        Tbgrupoxasignaturaxprofesor gap = new Tbgrupoxasignaturaxprofesor();
        TbgrupoxasignaturaxprofesorJpaController gapbd = new TbgrupoxasignaturaxprofesorJpaController();
        em = encuxasigbd.getEntityManager();
//        TypedQuery<Tbencuestaxasignatura> query = em.createNamedQuery("Tbencuestaxasignatura.findAll", Tbencuestaxasignatura.class);
        encuasig = query.getResultList();
        em.close();
        mate.setIdgrupoxasignaturaxprofesor(null);
    }

    @FXML
    private void cargarE(ActionEvent event) {
         tablaasignatura.getColumns().clear();
        String[] titulos = {
            "Codigo",
            "Nombre",
            "Apellido",
            "Correo",
            "Direccion",
            "Telefono",
            "Tipo de cuenta",
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
            tablaasignatura.getColumns().addAll(titulo);
//             Asignamos un tamaño a ls columnnas
            titulo.setMinWidth(160);

//             Centrar los datos de la tabla
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
        for (Tbusuario usua : usuario) {
            ObservableList<String> row = FXCollections.observableArrayList();
            row.add(usua.getCodigo().toString());
            row.add(usua.getNombre());
            row.add(usua.getApellido());
            row.add(usua.getEmail()+ "");
            row.add(usua.getDireccion()+"");
            row.add(usua.getTelefono()+"");
            row.add(usua.getIdtipo().getPrivilegios());
           tbasignatura.addAll(row);
        }
        tablaasignatura.setItems(tbasignatura);
       
    }

    @FXML
    private void cargarA(ActionEvent event) {
    }

    private void Cargar() {
        EntityManager em = usuariobd.getEntityManager();
        TypedQuery<Tbusuario> query = em.createNamedQuery("Tbusuario.findAll", Tbusuario.class);
        usuario = query.getResultList();
        em.close();
        
        em = materiasbd.getEntityManager();
        TypedQuery<Tbasignatura> query2 = em.createNamedQuery("Tbasignatura.findAll", Tbasignatura.class);
        materias = query2.getResultList();
        em.close();
        
        em = usuxmatbd.getEntityManager();
        TypedQuery<Tbencuestasxusuario> query3 = em.createNamedQuery("Tbencuestasxusuario.findAll", Tbencuestasxusuario.class);
        usuxmat = query3.getResultList();
        em.close();
        
    }

    private void CargarTabla() {
        tablaasignatura.getColumns().clear();
        String[] titulos = {
            "Codigo",
            "Nombre",
            "Asignatura",
            "Nombre Asignatura",
            "Estado",
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
            tablaasignatura.getColumns().addAll(titulo);
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
        for (Tbencuestasxusuario encu : usuxmat) {
            ObservableList<String> row = FXCollections.observableArrayList();
            row.add(encu.getCodigousuario().getCodigo().toString());
            row.add(encu.getCodigousuario().getNombre() + " " + encu.getCodigousuario().getApellido());
            row.add(encu.getIdencuestaxasignatura().getIdasignatura().getCodigo());
            row.add(encu.getIdencuestaxasignatura().getIdasignatura().getNombre());
            row.add(encu.getEstado());
           tbasignatura.addAll(row);
        }
        tablaasignatura.setItems(tbasignatura);
    }
    
    private void LlenarEstudiante(){
        for(int i=0; i<usuario.size();i++){
            if(!usuario.get(i).getIdtipo().getPrivilegios().equals("Administrador")){
                estudiante.getItems().add(usuario.get(i).getCodigo().toString());
            }
        }
        
    }
    
    private void Llenarasignatura(String comparar){
        for(int i=0; i<materias.size();i++){
            Boolean agregar = true;
            for(int j=0;j<usuxmat.size();j++){
                if(comparar.equals(usuxmat.get(j).getCodigousuario().getCodigo().toString()) && usuxmat.get(j).getIdgrupoxasignaturaxprofesor().getIdasignatura().getCodigo().equals(materias.get(i).getCodigo())){
                    agregar = false;
                }
            }
            if(agregar){
                asignatura.getItems().add(materias.get(i).getCodigo());
            }
        }
    }
    
}
