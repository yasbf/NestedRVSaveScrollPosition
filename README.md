# NestedRVSaveScrollPosition
Saving Scroll Positions of Nested RecyclerViews

One of the most annoying things when using RecyclerViews is that RecyclerViews by nature don’t store item states upon recycling. Thus, each item is reset to its original state from creation when you scroll off-view. The issue here is that scroll positions are reset to position zero when you have nested RecyclerView carousels. Makes you wonder how other apps (i.e., Netflix, Google Play Store) handle this problem…

### Storing the carousel states:

The idea behind this solution is that we will save the current scroll index of each horizontal carousel in some data structure before it is recycled. That way when the views return to foreground we can retrieve the saved indices and set the scroll positions for the carousels.

Few things to consider:

* All changes should be done in the outer adapter.
* We should store a LinearLayoutManager in the holder and instantiate it when we create the inner RecyclerView there.
* Fun fact: you can get the parent context in an adapter without passing it through the constructor inside onCreateViewHolder with mContext = viewGroup.getContext()
* Create a SparseIntArray or map to hold the scroll position of each carousel.

### Saving carousel state
We must override the onViewRecycled to store the position of the first visible item in that carousel after the view holder recycles.

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder viewHolder) {
        
        // Store position
        final int position = viewHolder.getAdapterPosition();
        int firstVisiblePosition = viewHolder.layoutManager.findFirstVisibleItemPosition();
        positionList.put(position, firstVisiblePosition);

        super.onViewRecycled(viewHolder);
    }

### Retrieving carousel states
Now that the states are stored we can retrieve them dynamically in onBindViewHolder per position.

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        viewHolder.setData(mDataList.get(position));

        // Retrieve and set the saved position 
        int lastSeenFirstPosition = positionList.get(position, 0);
        if (lastSeenFirstPosition >= 0) {
            viewHolder.layoutManager.scrollToPositionWithOffset(lastSeenFirstPosition, 0);
        }
    }

 ### Librairies
* [AndroidX][AndroidX] 
* [Kotlin][Kotlin]

[AndroidX]: https://developer.android.com/jetpack/androidx
[Kotlin]: https://kotlinlang.org/docs/tutorials/kotlin-android.html
