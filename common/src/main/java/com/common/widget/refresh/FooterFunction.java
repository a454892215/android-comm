package com.common.widget.refresh;

/**
 * Author:  Pan
 * CreateDate: 2019/1/4 9:51
 * Description: No
 */

public enum FooterFunction {
    load_more(RefreshLayout.load_state_up_load),
    forbid_scroll(RefreshLayout.load_state_forbid_scroll),
    only_display(RefreshLayout.load_state_only_display);

    FooterFunction(int footerState) {
        this.footerState = footerState;
    }

    private int footerState;

    public int getFooterState() {
        return footerState;
    }


}
