package com.example.crm.document;

import java.util.List;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.example.validation.Iban;
import com.example.validation.TcKimlikNo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

@Document(collection = "customers")
public class CustomerDocument {
	@Id
	@TcKimlikNo
	private String identityNo;
	@Field("fname")
	@Indexed
	@Size(min = 3)
	private String fullName;
	@Indexed(unique = true)
	@Email
	private String email;
	private List<Address> addresses;
	private List<Phone> phones;
	@Iban
	private String iban;

	public String getIdentityNo() {
		return identityNo;
	}

	public void setIdentityNo(String identityNo) {
		this.identityNo = identityNo;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}



	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	public List<Phone> getPhones() {
		return phones;
	}

	public void setPhones(List<Phone> phones) {
		this.phones = phones;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	@Override
	public int hashCode() {
		return Objects.hash(identityNo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomerDocument other = (CustomerDocument) obj;
		return Objects.equals(identityNo, other.identityNo);
	}

	@Override
	public String toString() {
		return "CustomerDocument [identityNo=" + identityNo + ", fullName=" + fullName + ", email=" + email
				+ ", address=" + addresses + ", phones=" + phones + ", iban=" + iban + "]";
	}

}
