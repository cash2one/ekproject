package com.ctfzh.tabdmeo.others;

import com.ctfzh.tabdmeo.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridViewAdapter extends BaseAdapter {

	Context mContext;
	LayoutInflater mLayoutInflater;
	int mImageviewArray[];
	String mTextViewArray[];

	public GridViewAdapter(Context context, int imageViewArray[],
			String textViewArray[]) {
		mContext = context;
		mLayoutInflater = LayoutInflater.from(context);

		mImageviewArray = imageViewArray;

		mTextViewArray = textViewArray;

	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = mLayoutInflater.inflate(R.layout.tab_item_view, null);
		}

		ImageView imageView = (ImageView) convertView
				.findViewById(R.id.imageview);
		imageView.setBackgroundResource(mImageviewArray[position]);

		TextView textView = (TextView) convertView.findViewById(R.id.textview);
		textView.setText(mTextViewArray[position]);

		return convertView;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mImageviewArray.length;
	}

}
