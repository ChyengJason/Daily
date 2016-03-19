package in.srain.cube.views.ptr;

import android.view.View;
/**
 *下拉刷新功能接口，对下拉刷新功能的抽象
 **/
public interface PtrHandler {
    /**
     * Check can do refresh or not. For example the content is empty or the first child is in view.
     * <p/>
     * {@link in.srain.cube.views.ptr.PtrDefaultHandler#checkContentCanBePulledDown}
     *
     * 判断是否可以下拉刷新。 UltraPTR 的 Content 可以包含任何内容，用户在这里判断决定是否可以下拉。
     * 例如，如果 Content 是 TextView ，则可以直接返回 true ，表示可以下拉刷新。
     * 如果 Content 是 ListView ，当第一条在顶部时返回 true ，表示可以下拉刷新。
     * 如果 Content 是 ScrollView ，当滑动到顶部时返回 true ，表示可以刷新。
     */
    public boolean checkCanDoRefresh(final PtrFrameLayout frame, final View content, final View header);

    /**
     * When refresh begin
     * 刷新回调函数，用户在这里写自己的刷新功能实现，处理业务数据的刷新
     * @param frame
     */
    public void onRefreshBegin(final PtrFrameLayout frame);
}