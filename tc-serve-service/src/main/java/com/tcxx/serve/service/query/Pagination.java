package com.tcxx.serve.service.query;

import lombok.Data;

@Data
public class Pagination {

    private Integer pagingPageCurrent;

    private Integer pagingRecordStart = 0;

    private Integer pagingPageSize = 20;

    public Integer getPagingRecordStart() {
        if (pagingPageCurrent != null && pagingPageCurrent > 0 && pagingPageSize != null && pagingPageSize > 0) {
            pagingRecordStart = (pagingPageCurrent - 1) * pagingPageSize;
        }
        return pagingRecordStart;
    }

}
