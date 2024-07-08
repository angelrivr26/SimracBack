package pe.sernanp.ws_api.entity;

public class PaginatorEntity {

	private int _offset;
	private String _sort;
	private int _limit;
	private String _order;
	private int _total;
	
	public int getOffset(){
		return this._offset;
	}
	public void setOffset(int value){
		this._offset=value;
	}
	public int getLimit(){
		return this._limit;
	}
	public void setLimit(int value){
		this._limit=value;
	}
	public String getSort(){
		return this._sort;
	}
	public void setSort(String value){
		value = value==null? "":value;
		 //this._sort = StringUtils.isEmpty(value) ? "": value.replace(".", "").toLowerCase();
	}
	public String getOrder(){
		return this._order;
	}
	public void setOrder(String value){
		this._order=value;
	}
	public int getTotal(){
		return this._total;
	}
	public void setTotal(int value){
		this._total=value;
	}
}
