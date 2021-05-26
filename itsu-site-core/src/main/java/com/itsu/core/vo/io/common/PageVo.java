package com.itsu.core.vo.io.common;

import org.apache.ibatis.annotations.Select;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @ClassName: DataVo.java
 * @Description: 分页VO,需要分页的VO继承该类
 * @author liqingquan
 * @Date 2021年1月4日 下午3:59:22
 */
public class PageVo implements Serializable {

	private static final long serialVersionUID = -7416400710423549083L;

	/** 第几页 */
	@NotNull(message = "page param 'current' is not null", groups = { Select.class })
	private Long current;

	/** 每页数量 */
	@NotNull(message = "page param 'size' is not null", groups = { Select.class })
	private Long size;

	/** 总数量 */
	private Long total;

	public Long getCurrent() {
		return current;
	}

	public void setCurrent(Long current) {
		this.current = current;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

}
