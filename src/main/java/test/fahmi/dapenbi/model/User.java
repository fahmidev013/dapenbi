package test.fahmi.dapenbi.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "user")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, allowGetters = true)

public class User implements Serializable{

@Id
@GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
private Long id;

@NotBlank
private String nip;

@NotBlank
private String name;

@NotBlank
private String dob;

@NotBlank
private String gender;

@NotBlank
private String address;

@NotBlank
private int idNumber;

@NotBlank
private int phone;


@Column(nullable = false, updatable = false)
@Temporal (TemporalType.TIMESTAMP)
@CreatedDate
private Date createdAt;

@Column(nullable = false)
@Temporal(TemporalType.TIMESTAMP)
@LastModifiedDate
private Date updatedAt;

public Long getId() {
return id;
}

public String getNip() {
return nip;
}

public String getName() {
return name;
}

public String getDob() {
return dob;
}

public String getGender() {
return gender;
}

public String getAddress() {
return address;
}

public int getIdNumber() {
return idNumber;
}

public int getPhone() {
return phone;
}

public Date getCreatedAt() {
return createdAt;
}

public Date getUpdatedAt() {
return updatedAt;
}

public void setId(Long id) {
this.id = id;
}

public void setNip(String nip) {
this.nip = nip;
}

public void setName(String name) {
this.name = name;
}

public void setDob(String dob) {
this.dob = dob;
}

public void setGender(String gender) {
this.gender = gender;
}

public void setAddress(String address) {
this.address = address;
}

public void setIdNumber(int idNumber) {
this.idNumber = idNumber;
}

public void setPhone(int phone) {
this.phone = phone;
}

public void setCreatedAt(Date createdAt) {
this.createdAt = createdAt;
}

public void setUpdatedAt(Date updatedAt) {
this.updatedAt = updatedAt;
}
}

