package com.common.widget.refresh;

/**
 * Author:  L
 * CreateDate: 2019/1/4 9:50
 * Description: No
 */

public enum HeaderFunction {
    refresh(RefreshLayout.refresh_state_pull_down),
    forbid_scroll(RefreshLayout.refresh_state_forbid_scroll),
    only_display(RefreshLayout.refresh_state_only_display);
    private int headerState;

    HeaderFunction(int header_state) {
        this.headerState = header_state;
    }

    public int getHeaderState() {
        return headerState;
    }


}
