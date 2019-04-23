package com.ampcus.rtmclient.core.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "CLIENT")
public class Client  implements java.io.Serializable{

	private Integer clientId;
	private String clientName;
	private List<Project> projects = new ArrayList();
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	@Column(name = "CLIENT_ID", updatable = false, nullable = false)
	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	@Column(name = "CLIENT_NAME", updatable = true, nullable = false)
	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public Client() {
		// TODO Auto-generated constructor stub
	}

	@OneToMany(targetEntity=Project.class, orphanRemoval=true, cascade=CascadeType.DETACH, fetch=FetchType.EAGER)
	@JoinColumn(name="CLIENT_ID")
	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	

}
