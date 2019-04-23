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
import javax.persistence.Table;

@Entity
@Table(name = "REQ_SRC_DOC_VERSION")
public class RequirementSourceDocumentVersion implements Serializable {

	public RequirementSourceDocumentVersion() {
		// TODO Auto-generated constructor stub
	}

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	@Column(name = "REQ_SRC_DOC_VER_ID", updatable = false, nullable = false)
	private Integer reqSrcDocVerId;
	public Integer getReqSrcDocVerId() {
		return reqSrcDocVerId;
	}

	public void setReqSrcDocVerId(Integer reqSrcDocVerId) {
		this.reqSrcDocVerId = reqSrcDocVerId;
	}

	@ManyToOne
	@JoinColumn(name="REQ_SRC_DOC_ID")
	private RequirementSourceDocument requirementSourceDoc;
	public RequirementSourceDocument getRequirementSourceDoc() {
		return requirementSourceDoc;
	}

	public void setRequirementSourceDoc(RequirementSourceDocument requirementSourceDoc) {
		this.requirementSourceDoc = requirementSourceDoc;
	}
	
	@OneToMany(targetEntity=Requirement.class, orphanRemoval=true, cascade=CascadeType.DETACH, fetch=FetchType.EAGER)
	@JoinColumn(name="REQ_SRC_DOC_VER_ID")
	private Set<Requirement> requirements = new LinkedHashSet();
	public Set<Requirement> getRequirements() {
		return requirements;
	}

	public void setRequirements(Set<Requirement> requirements) {
		this.requirements = requirements;
	}
	
}
