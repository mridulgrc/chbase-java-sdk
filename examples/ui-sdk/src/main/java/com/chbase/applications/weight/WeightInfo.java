/*
 * Copyright 2011 Microsoft Corp.
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.chbase.applications.weight;

import java.io.Serializable;

public class WeightInfo implements Serializable
{
	public static final String WeightType = "3d34d87e-7fc1-4153-800f-f56592cb0d17";
	
	private String id;
	private String version;
	private String value;
	
	public WeightInfo()
	{
	}
	
	public WeightInfo(String id, String value, String version) {
		this.id = id;
		this.value = value;
		this.version = version;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public void setVersion(String version)
	{
		this.version=version;
	}
	
	public String getVersion()
	{
		return this.version;
	}
}
