package com.ampcus.rtmclient.core.entity;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "PROJECT")
public class Project  implements java.io.Serializable{

	public Project() {
		// TODO Auto-generated constructor stub
	}
	
	private Integer projectId;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	@Column(name = "PROJECT_ID", updatable = false, nullable = false)
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	private Client client;
    @ManyToOne
    @JoinColumn(name="CLIENT_ID")
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}

	private String projectName;
	@Column(name = "PROJECT_NAME", updatable = true, nullable = false)
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	private java.sql.Date startDate;
	@Column(name = "START_DT", updatable = true, nullable = false)
	public java.sql.Date getStartDate() {
		return startDate;
	}
	public void setStartDate(java.sql.Date startDate) {
		this.startDate = startDate;
	}

	private java.sql.Date endDate;
	@Column(name = "END_DT", updatable = true, nullable = false)
	public java.sql.Date getEndDate() {
		return endDate;
	}
	public void setEndDate(java.sql.Date endDate) {
		this.endDate = endDate;
	}
	
	@OneToMany(mappedBy="project", orphanRemoval=true, cascade=CascadeType.DETACH, fetch=FetchType.EAGER)
	private Set<ProjectRelease> projectReleases = new LinkedHashSet();
	public Set<ProjectRelease> getProjectReleases() {
		return projectReleases;
	}
	public void setProjectReleases(Set<ProjectRelease> projectReleases) {
		this.projectReleases = projectReleases;
	}
	
	
}
