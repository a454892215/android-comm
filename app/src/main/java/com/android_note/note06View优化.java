package com.android_note;

/**
 * 1. 布局优化
 *  01. 减少过渡绘制, 减少布局层级，优化xml布局或者自定义 View
 *  02. 减少 xml文件 inflate 时间， 当inflate很多无法避免可以用代码去生成布局
 *  03. 减少View对象的创建
 *  04. 对 ItemView 设置监听器，不要对每个 Item 都调用addXxListener，应该大家公用一个 XxListener
 *  05. RecyclerView与ListView 对比浅析
 *       01. ListView(两级缓存)，RecyclerView(四级缓存)
 *       02. RecyclerView可以做到屏幕外的列表项ItemView进入屏幕内时也无须bindView快速重用
 *       03. RecyclerView提供了局部刷新的接口: 如， notifyItemRemoved(1)
 *  06：RecyclerView的四级缓存
 *   RecycledViewPool:默认的缓存数量是5个Cache满了后，先缓存进去的ViewHolder移出并缓存到RecycledViewPool
 *   Cache: 刚刚移出屏幕的缓存数据，默认大小是2个，当其容量被充满同时又有新的数据添加的时候，会根据FIFO原则，把优先进入的缓存数据移出并放到下一级缓存中
 *   ViewCacheExtension: 开发者自己来自定义缓存
 *   RecycledViewPoolScrap: 对应ListView 的Active View，就是屏幕内的View
 */
public class note06View优化 {
}
