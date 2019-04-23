package com.ampcus.rtmclient.core.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="REQUIREMENT")
public class Requirement implements Serializable {

	public Requirement() {
		// TODO Auto-generated constructor stub
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	@Column(name = "REQ_ID", updatable = false, nullable = false)
	private Integer requirementId;
	public Integer getRequirementId() {
		return requirementId;
	}
	public void setRequirementId(Integer requirementId) {
		this.requirementId = requirementId;
	}
	
	@ManyToOne
	@JoinColumn(name="REQ_SRC_DOC_VER_ID")
	private RequirementSourceDocumentVersion reqSrcDocVersion;
	public RequirementSourceDocumentVersion getReqSrcDocVersion() {
		return reqSrcDocVersion;
	}
	public void setReqSrcDocVersion(RequirementSourceDocumentVersion reqSrcDocVersion) {
		this.reqSrcDocVersion = reqSrcDocVersion;
	}
	
	@Column(name = "REQ_TXT_CONTENT", updatable = true, nullable = false)
	private String requirementText;
	public String getRequirementText() {
		return requirementText;
	}
	public void setRequirementText(String requirementText) {
		this.requirementText = requirementText;
	}
	
	@Column(name = "REQ_IMG_CONTENT", updatable = true, nullable = false)
	private byte[] reqImageContent;
	public byte[] getReqImageContent() {
		return reqImageContent;
	}
	public void setReqImageContent(byte[] reqImageContent) {
		this.reqImageContent = reqImageContent;
	}
	
	
	

	
}
