package indi.sword.util.basic.dataStructure.basic.List;

import indi.sword.util.basic.dataStructure.basic.List.Node;

/*
 * 链表，相当于火车
 */
public class LinkList {
	//头结点
	private Node first;

	public LinkList() {
		first = null;
	}

	/**
	 * 头插法
	 */
	public void insertFirst(long value) {
		Node node = new Node(value);
		node.next = first;
		first = node;
	}

	/**
	 * 删头
	 */
	public Node deleteFirst() {
		first = first.next;
		return first;
	}

	/**
	 * 显示方法
	 */
	public void display() {
		Node current = first;
		while(current != null) {
			current.display();
			current = current.next;
		}
		System.out.println();
	}

	/**
	 * 查找方法
	 */
	public Node find(long value) {
		Node current = first;
		while(current.data != value) {
			if(current.next == null) {
				return null;
			}
			current = current.next;
		}
		return current;
	}

	/**
	 * 删除方法，根据数据域来进行删除
	 */
	public Node deleteByValue(long value) {
		Node current = first;
		Node previous = first;
		while(current.data != value) {
			if(current.next == null) {
				return null;
			}
			previous = current;
			current = current.next;
		}

		if(current == first) {
			first = first.next;
		} else {
			previous.next = current.next;
		}
		return current;
	}

}
