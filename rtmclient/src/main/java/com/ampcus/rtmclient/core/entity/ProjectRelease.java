package com.ampcus.rtmclient.core.entity;

import java.beans.Transient;
import java.util.HashSet;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PROJECT_RELEASE")
public class ProjectRelease implements java.io.Serializable{

	public ProjectRelease() {
		// TODO Auto-generated constructor stub
	}

	private Integer releaseId;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	@Column(name = "RELEASE_ID", updatable = false, nullable = false)
	public Integer getReleaseId() {
		return releaseId;
	}
	public void setReleaseId(Integer releaseId) {
		this.releaseId = releaseId;
	}

	private ProjectRelease parentRelease;
	@ManyToOne(fetch = FetchType.LAZY, optional=true)
	public ProjectRelease getParentRelease() {
		return parentRelease;
	}
	public void setParentRelease(ProjectRelease parentRelease) {
		this.parentRelease = parentRelease;
	}
	
	private Set<ProjectRelease> childReleases = new HashSet<ProjectRelease>();
	@OneToMany(mappedBy="parentRelease", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval=true)
	public Set<ProjectRelease> getChildReleases() {
		return childReleases;
	}
	public void setChildReleases(Set<ProjectRelease> childReleases) {
		this.childReleases = childReleases;
	}
	
	@ManyToOne
    @JoinColumn(name="PROJECT_ID")
	private Project project;
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}

	@Column(name = "CUST_RELEASE_NUM", updatable = true, nullable = false)
	private String customerReleaseNumber;
	public String getCustomerReleaseNumber() {
		return customerReleaseNumber;
	}
	public void setCustomerReleaseNumber(String customerReleaseNumber) {
		this.customerReleaseNumber = customerReleaseNumber;
	}
	
	@Column(name = "RELEASE_TITLE", updatable = true, nullable = false)
	private String releaseTitle;
	public String getReleaseTitle() {
		return releaseTitle;
	}
	public void setReleaseTitle(String releaseTitle) {
		this.releaseTitle = releaseTitle;
	}
	
	private RequirementSourceDocument reqSrcDoc;
	@OneToOne
	@JoinColumn(name ="RELEASE_ID")
	public RequirementSourceDocument getReqSrcDoc() {
		return reqSrcDoc;
	}
	public void setReqSrcDoc(RequirementSourceDocument reqSrcDoc) {
		this.reqSrcDoc = reqSrcDoc;
	}
	
	public RequirementSourceDocument reqSourceDoc;
	@OneToOne(targetEntity=RequirementSourceDocument.class, cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="RELEASE_ID")
	public RequirementSourceDocument getReqSourceDoc() {
		return reqSourceDoc;
	}
	public void setReqSourceDoc(RequirementSourceDocument reqSourceDoc) {
		this.reqSourceDoc = reqSourceDoc;
	}
	
	
}
