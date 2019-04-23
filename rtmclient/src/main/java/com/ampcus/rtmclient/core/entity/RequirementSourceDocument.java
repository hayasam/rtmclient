package com.ampcus.rtmclient.core.entity;

import java.io.Serializable;
import java.util.LinkedHashSet;
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
@Table(name = "REQ_SRC_DOC")
public class RequirementSourceDocument implements Serializable {

	public RequirementSourceDocument() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	@Column(name = "REQ_SRC_DOC_ID", updatable = false, nullable = false)
	private Integer reqSrcDocId;
	public Integer getReqSrcDocId() {
		return reqSrcDocId;
	}
	public void setReqSrcDocId(Integer reqSrcDocId) {
		this.reqSrcDocId = reqSrcDocId;
	}
	
	private ProjectRelease release;
	@OneToOne
	@JoinColumn(name="RELEASE_ID")
	public ProjectRelease getRelease() {
		return release;
	}
	public void setRelease(ProjectRelease release) {
		this.release = release;
	}
	
	private SourceType sourceType;
	@ManyToOne
    @JoinColumn(name="SRC_TYP_ID")
	public SourceType getSourceType() {
		return sourceType;
	}
	public void setSourceType(SourceType sourceType) {
		this.sourceType = sourceType;
	}
	
	
	private String sourceDocTitle;
	@Column(name = "SRC_DOC_TITLE", updatable = true, nullable = false)
	public String getSourceDocTitle() {
		return sourceDocTitle;
	}
	public void setSourceDocTitle(String sourceDocTitle) {
		this.sourceDocTitle = sourceDocTitle;
	}
	
	@OneToMany(targetEntity=RequirementSourceDocumentVersion.class, orphanRemoval=true, cascade=CascadeType.DETACH, fetch=FetchType.EAGER)
	@JoinColumn(name="REQ_SRC_DOC_ID")
	private Set<RequirementSourceDocumentVersion> reqSourceDocVersions = new LinkedHashSet();
	public Set<RequirementSourceDocumentVersion> getReqSourceDocVersions() {
		return reqSourceDocVersions;
	}
	public void setReqSourceDocVersions(Set<RequirementSourceDocumentVersion> reqSourceDocVersions) {
		this.reqSourceDocVersions = reqSourceDocVersions;
	}
	

	
}
