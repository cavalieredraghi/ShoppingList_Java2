package org.elevenfifty.shoppinglist.beans;

import java.util.Arrays;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="item_images")
public class ShoppingListItemImage {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private long shoppingListItemId;
	private String contentType;
	private byte[] image;
	

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getShoppingListItemId() {
		return shoppingListItemId;
	}
	public void setShoppingListItemId(long shoppingListItemId) {
		this.shoppingListItemId = shoppingListItemId;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((contentType == null) ? 0 : contentType.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + Arrays.hashCode(image);
		result = prime * result + (int) (shoppingListItemId ^ (shoppingListItemId >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ShoppingListItemImage other = (ShoppingListItemImage) obj;
		if (contentType == null) {
			if (other.contentType != null)
				return false;
		} else if (!contentType.equals(other.contentType))
			return false;
		if (id != other.id)
			return false;
		if (!Arrays.equals(image, other.image))
			return false;
		if (shoppingListItemId != other.shoppingListItemId)
			return false;
		return true;
	}
	
}
