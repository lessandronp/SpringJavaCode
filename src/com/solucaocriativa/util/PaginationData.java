package com.solucaocriativa.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PaginationData {

	private int actualPage;
	private int nextPage;
	private int numPages;
	
	public PaginationData() {
		this.actualPage = 1;
		this.nextPage = actualPage + 1;
	}

	public PaginationData(int totalRecords, int numPerPages, int actualPage) {
		this.actualPage = actualPage;
		this.numPages = new BigDecimal(totalRecords).divide(new BigDecimal(numPerPages), RoundingMode.UP).intValue();
		this.nextPage = numPages > 1 ? actualPage + 1 : this.nextPage;
	}
	
	public void updateNextPage() {
		this.actualPage = this.nextPage + 1;
		if (this.actualPage < this.numPages && this.nextPage < this.numPages) {
			this.nextPage = this.actualPage + 1;
		} else {
			this.nextPage += 1;
		}
	}

	public void updatePreviousPage() {
		this.actualPage -= 2;
		if (this.actualPage == 1) {
			this.nextPage -= 2;
		} else {
			this.nextPage -= 1;
		}
	}
	
	public boolean isViewNextButton() {
		return this.nextPage > 0 && this.nextPage < this.numPages;
	}
	
	public boolean isViewPreviousButton() {
		return this.numPages > 1 && this.actualPage > 1;
	}

	public int getNextPage() {
		return this.nextPage;
	}
	
	public int getActualPage() {
		return this.actualPage;
	}
	
	public int getNumPages() {
		return this.numPages;
	}
}
