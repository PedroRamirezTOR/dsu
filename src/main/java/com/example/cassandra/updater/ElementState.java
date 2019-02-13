package com.example.cassandra.updater;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

/**
 * ElementState class
 */
@Table(name="elementstate", keyspace="States")
public class ElementState {

	/**
	 * Type of element
	 */
	@PartitionKey
	private String elementtypeelement;

	/**
	 * State of element
	 */
	@Column
	private String state;

	/**
	 * Default constructor
	 */
	public ElementState() {
		//
	}

	/**
	 * Get the ElementTypeElement
	 */
	public String getElementTypeElement() {
		return elementtypeelement;
	}

	/**
	 * Set the elementtypeelement
	 * 
	 * @param elementtypeelement
	 */
	public void setElementTypeElement(String elementtypeelement) {
		this.elementtypeelement = elementtypeelement;
	}

	/**
	 * Get the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * Set the state
	 * 
	 * @param state
	 */
	public void setState(String state) {
		this.state = state;
	}
	
	@Override
	public String toString() {
		return "ElementState:"+elementtypeelement+" State:"+state;
	}
}
