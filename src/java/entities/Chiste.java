/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Esther
 */
@Entity
@Table(name = "CHISTE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Chiste.findAll", query = "SELECT c FROM Chiste c")
    , @NamedQuery(name = "Chiste.findById", query = "SELECT c FROM Chiste c WHERE c.id = :id")
    , @NamedQuery(name = "Chiste.findByAdopo", query = "SELECT c FROM Chiste c WHERE c.adopo = :adopo")
    , @NamedQuery(name = "Chiste.findByDescripcion", query = "SELECT c FROM Chiste c WHERE c.descripcion = :descripcion")
    , @NamedQuery(name = "Chiste.findByTitulo", query = "SELECT c FROM Chiste c WHERE c.titulo = :titulo")})
public class Chiste implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Short id;
    @Column(name = "ADOPO")
    private String adopo;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "TITULO")
    private String titulo;
    @OneToMany(mappedBy = "idchiste")
    private List<Puntos> puntosList;
    @JoinColumn(name = "IDCATEGORIA", referencedColumnName = "ID")
    @ManyToOne
    private Categoria idcategoria;

    public Chiste() {
    }

    public Chiste(Short id) {
        this.id = id;
    }

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getAdopo() {
        return adopo;
    }

    public void setAdopo(String adopo) {
        this.adopo = adopo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @XmlTransient
    public List<Puntos> getPuntosList() {
        return puntosList;
    }

    public void setPuntosList(List<Puntos> puntosList) {
        this.puntosList = puntosList;
    }

    public Categoria getIdcategoria() {
        return idcategoria;
    }

    public void setIdcategoria(Categoria idcategoria) {
        this.idcategoria = idcategoria;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Chiste)) {
            return false;
        }
        Chiste other = (Chiste) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Chiste[ id=" + id + " ]";
    }
    
}
