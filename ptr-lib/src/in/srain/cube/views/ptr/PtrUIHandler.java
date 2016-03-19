package in.srain.cube.views.ptr;

import in.srain.cube.views.ptr.indicator.PtrIndicator;

/**
 *
 */
public interface PtrUIHandler {

    /**
     * 下拉刷新 UI 接口，对下拉刷新 UI 变化的抽象。
     * 一般情况下， Header 会实现此接口，处理下拉过程中的头部 UI 的变化。
     * When the content view has reached top and refresh has been completed, view will be reset.
     *
     * @param frame
     */
    public void onUIReset(PtrFrameLayout frame);

    /**
     * prepare for loading
     * Content 重新回到顶部， Header消失，整个下拉刷新过程完全结束以后，重置 View
     * @param frame
     */
    public void onUIRefreshPrepare(PtrFrameLayout frame);

    /**
     * perform refreshing UI
     * 准备刷新，Header 将要出现时调用
     */
    public void onUIRefreshBegin(PtrFrameLayout frame);

    /**
     * perform UI after refresh
     * 开始刷新，Header 进入刷新状态之前调用
     */
    public void onUIRefreshComplete(PtrFrameLayout frame);

    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator);
}
