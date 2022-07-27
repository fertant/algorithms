import java.util.*;

class Program {

	static class LRUCache {
    int maxSize;
		HashMap<String, LinkedList> store = new HashMap<String, LinkedList>();
		LinkedList head;
	  LinkedList tail;
		
    public LRUCache(int maxSize) {
      this.maxSize = maxSize > 1 ? maxSize : 1;
    }

    public void insertKeyValuePair(String key, int value) {
      LinkedList valueNode = new LinkedList(key, value);
			if (this.store.size() == 0) {
				this.head = valueNode;
				this.tail = valueNode;
			} else {
        valueNode.next = this.head;
				this.head.parent = valueNode;
			  this.head = valueNode;
			}
      if (this.store.size() == this.maxSize) {
				if (this.tail != null) {
				  this.store.remove(this.tail.key);
		  		if (this.tail.parent != null) {
  		  		this.tail.parent.next = null;				
  			  	this.tail = this.tail.parent;
				  }
				}
			}
			this.store.put(key, valueNode);
    }

    public LRUResult getValueFromKey(String key) {
			if (this.store.containsKey(key)) {
				LinkedList valueNode = this.store.get(key);
        if (valueNode.parent != null) {
					if (valueNode == this.tail) {
            valueNode.parent.next = null;
						this.tail = valueNode.parent;
					} else {
  				  valueNode.parent.next = valueNode.next;						
					}
				}
				if (valueNode.next != null) {
  				valueNode.next.parent = valueNode.parent;
				}
				
				this.head.parent = valueNode;
				valueNode.next = this.head;
  			valueNode.parent = null;
				this.head = valueNode;
  			return new LRUResult(true, valueNode.value);
			}
      return new LRUResult(false, 0);
    }

    public String getMostRecentKey() {
      return head.key;
    }
  }

  static class LinkedList {
    int value;
		String key;
    LinkedList next;
		LinkedList parent;

    LinkedList(String key, int value) {
      this.key = key;
      this.value = value;
      this.next = null;
			this.parent = null;
    }
  }

	static class LRUResult {
    boolean found;
    int value;

    public LRUResult(boolean found, int value) {
      this.found = found;
      this.value = value;
    }
  }
}
