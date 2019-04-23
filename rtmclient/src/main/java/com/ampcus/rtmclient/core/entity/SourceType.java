package com.ampcus.rtmclient.core.entity;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "SOURCE_TYP_MSTR")
public class SourceType implements java.io.Serializable{

	public SourceType() {
		// TODO Auto-generated constructor stub
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	@Column(name = "SRC_TYP_ID", updatable = false, nullable = false)
	private Integer sourceTypeId;
	public Integer getSourceTypeId() {
		return sourceTypeId;
	}
	public void setSourceTypeId(Integer sourceTypeId) {
		this.sourceTypeId = sourceTypeId;
	}
	
	@Column(name = "SRC_TYP_NM", updatable = true, nullable = false)
	private String sourceTypeName;
	public String getSourceTypeName() {
		return sourceTypeName;
	}
	public void setSourceTypeName(String sourceTypeName) {
		this.sourceTypeName = sourceTypeName;
	}
	
	@OneToMany(targetEntity=RequirementSourceDocument.class, orphanRemoval=true, cascade=CascadeType.DETACH, fetch=FetchType.EAGER)
	@JoinColumn(name="SRC_TYP_ID")
	private Set<RequirementSourceDocument> reqSrcDocs = new LinkedHashSet();
	public Set<RequirementSourceDocument> getReqSrcDocs() {
		return reqSrcDocs;
	}
	public void setReqSrcDocs(Set<RequirementSourceDocument> reqSrcDocs) {
		this.reqSrcDocs = reqSrcDocs;
	}
	
	
	
}
