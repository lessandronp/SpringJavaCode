package com.solucaocriativa.util;


public class GerenciadorPaginacao {

    private int totalRows;

    // Paging.
    private int firstRow;
    private int rowsPerPage;
    private int totalPages;
    private int pageRange;
    private Integer[] pages;
    private int currentPage;

    public GerenciadorPaginacao(int rowsPerPage) {
	// Default rows per page (max amount of rows to be displayed at once).
	this.rowsPerPage = rowsPerPage;
	// Default page range (max amount of page links to be
	this.pageRange = 5; 
    }

    public GerenciadorPaginacao() {
	// Default rows per page (max amount of rows to be displayed at once).
	this.rowsPerPage = 5;
	// Default page range (max amount of page links to be
	this.pageRange = 5; 
    }

    public void pageFirst() {
	page(0);
    }

    public int proximaPagina() {
	int nextPage = firstRow + rowsPerPage;
	page(nextPage);
	return nextPage;
    }

    public int paginaAnterior() {
	int pagePrevious = firstRow - rowsPerPage;
	page(pagePrevious);
	return pagePrevious;
    }

    public void pageLast() {
	page(this.totalRows
		- ((this.totalRows % rowsPerPage != 0) ? this.totalRows % rowsPerPage
			: rowsPerPage));
    }

    public int verPagina(int actualPage) {
	int nextNumberPage = (actualPage - 1) * rowsPerPage;
	page(nextNumberPage);
	return nextNumberPage;
    }

    private void page(int firstRow) {
	this.firstRow = firstRow;
    }

    public void carregaListaDados(int totalLinhas) {
	this.totalRows = totalLinhas;
	// Set currentPage, totalPages and pages.
	currentPage = (this.totalRows / rowsPerPage)
		- ((this.totalRows - firstRow) / rowsPerPage) + 1;
	totalPages = (this.totalRows / rowsPerPage)
		+ ((this.totalRows % rowsPerPage != 0) ? 1 : 0);
	int pagesLength = Math.min(pageRange, totalPages);
	pages = new Integer[pagesLength];

	// firstPage must be greater than 0 and lesser than
	// totalPages-pageLength.
	int firstPage = Math.min(Math.max(0, currentPage - (pageRange / 2)),
		totalPages - pagesLength);

	// Create pages (page numbers for page links).
	for (int i = 0; i < pagesLength; i++) {
	    pages[i] = ++firstPage;
	}
    }
    
    public boolean isFirstRow() {
	return this.firstRow == 0;
    }
    
    public boolean getHasRows() {
	return this.firstRow + this.rowsPerPage >= this.totalRows;
    }

    public int getTotalRows() {
	return this.totalRows;
    }

    public int getPrimeiraLinha() {
	return firstRow;
    }

    public int getRowsPerPage() {
	return rowsPerPage;
    }

    public Integer[] getPages() {
	return pages;
    }

    public int getCurrentPage() {
	return currentPage;
    }

    public int getTotalPages() {
	return totalPages;
    }

    public void setRowsPerPage(int rowsPerPage) {
	this.rowsPerPage = rowsPerPage;
    }
}
