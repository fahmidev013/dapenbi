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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "user")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, allowGetters = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
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
private String idNumber;

@NotBlank
private String phone;


@Column(nullable = false, updatable = false)
@Temporal (TemporalType.TIMESTAMP)
@CreatedDate
private Date createdAt;

@Column(nullable = false)
@Temporal(TemporalType.TIMESTAMP)
@LastModifiedDate
private Date updatedAt;
public User() {}
public User(String nip, String name, String dob, String gender, String address, String id_number, String phone) {
	this.nip = nip;
	this.name = name;
	this.dob = dob;
	this.gender = gender;
	this.address = address;
	this.idNumber = id_number;
	this.phone = phone;
}

public Long getId() {
return this.id;
}

public String getNip() {
return this.nip;
}

public String getName() {
return this.name;
}

public String getDob() {
return this.dob;
}

public String getGender() {
return this.gender;
}

public String getAddress() {
return this.address;
}

public String getIdNumber() {
return this.idNumber;
}

public String getPhone() {
return this.phone;
}

public Date getCreatedAt() {
return this.createdAt;
}

public Date getUpdatedAt() {
return this.updatedAt;
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

public void setIdNumber(String idNumber) {
this.idNumber = idNumber;
}

public void setPhone(String phone) {
this.phone = phone;
}

public void setCreatedAt(Date createdAt) {
this.createdAt = createdAt;
}

public void setUpdatedAt(Date updatedAt) {
this.updatedAt = updatedAt;
}
}

