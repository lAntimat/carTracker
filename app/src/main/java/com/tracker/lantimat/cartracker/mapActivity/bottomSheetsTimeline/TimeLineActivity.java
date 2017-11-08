package com.tracker.lantimat.cartracker.mapActivity.bottomSheetsTimeline;

import com.tracker.lantimat.cartracker.MainActivity;

/**
 * Created by HP-HP on 05-12-2015.
 */
public class TimeLineActivity extends MainActivity {

    /*
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    private RecyclerView mRecyclerView;
    private TimeLineAdapter mTimeLineAdapter;
    private List<TimeLineModel> mDataList = new ArrayList<>();
    private Orientation mOrientation;
    private boolean mWithLinePadding;
    int positionForScroll = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_timeline);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        //if(getSupportActionBar()!=null)
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FrameLayout v = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_timeline, v);

        ButterKnife.bind(this);

        //mOrientation = (Orientation) getIntent().getSerializableExtra(MainActivity.EXTRA_ORIENTATION);
        //mWithLinePadding = getIntent().getBooleanExtra(MainActivity.EXTRA_WITH_LINE_PADDING, false);

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("Cобытия");

        firstCreateMsg();

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(getLinearLayoutManager());
        mRecyclerView.setHasFixedSize(true);

        initView();
        getEventTimeLine();

        result.setSelection(1, false);



    }

    private LinearLayoutManager getLinearLayoutManager() {
        if (mOrientation == Orientation.HORIZONTAL) {
            return new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        } else {
            return new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        }
    }

    private void initView() {
        //setDataListItems();
        mTimeLineAdapter = new TimeLineAdapter(mDataList, mOrientation, mWithLinePadding);
        mRecyclerView.setAdapter(mTimeLineAdapter);
    }

    public void onFailureMethod() {
        progressBar.setVisibility(MapView.INVISIBLE);
        Toast.makeText(getApplicationContext(), "Ошибка соединения", Toast.LENGTH_SHORT).show();
    }

    private void getEventTimeLine() {
        KFURestClient.getUrl("https://media.kpfu.ru/events/month", null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                new ParseStrFromByte().execute(responseBody);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                onFailureMethod();
            }
        });
    }

    private void firstCreateMsg() {
        SharedPreferences sp = getSharedPreferences("FIRST_CREATE2",
                Context.MODE_PRIVATE);
        // проверяем, первый ли раз открывается программа
        boolean hasVisited = sp.getBoolean("hasVisited", false);

        if (!hasVisited) {
            // выводим нужную активность
            startActivity(new Intent(TimeLineActivity.this, MainIntroActivity.class));
            SharedPreferences.Editor e = sp.edit();
            e.putBoolean("hasVisited", true);
            e.apply(); // не забудьте подтвердить изменения

        }
    }

    private void showEmptyView() {
        TextView textView = (TextView) findViewById(R.id.textView);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        textView.setVisibility(MapView.VISIBLE);
        imageView.setVisibility(MapView.VISIBLE);
    }


    public class ParseStrFromByte extends AsyncTask<byte[], Void, Void> {
        @Override
        protected Void doInBackground(byte[]... params) {

            String str = null;

            //str = new String(params[0], "UTF-8");
            str = new String(params[0]);

            Document doc = Jsoup.parse(str);
            Elements elements = doc.select("div.eventItem.uk-clearfix");

//            Log.d("event-list.uk-active", elements.get(2).toString());

            if(elements.size()==0) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Toast.makeText(getApplicationContext(), R.string.timeline_events_null, Toast.LENGTH_LONG).show();
                        showEmptyView();
                    }
                });
            }
            else {
                Calendar calendar = Calendar.getInstance();
                Date dateCalendar = calendar.getTime();
                String full = new SimpleDateFormat("dd.MM.yyyy").format(dateCalendar);
                int today = calendar.get(Calendar.DAY_OF_MONTH);
                for (int i = 0; i < elements.size(); i++) {
                    String date = elements.get(i).select("div.eventItem-date").text();
                    String title = elements.get(i).select("div.eventItem-title").text();
                    String place = elements.get(i).select("div.eventItem-place").text();
                    String format = elements.get(i).select("div.eventItem-format").text();
                    String[] splitStr = date.split("\\.");

                    if (Integer.parseInt(splitStr[0]) > today)
                        mDataList.add(new TimeLineModel(date, title, place, format, OrderStatus.INACTIVE));
                    else
                        mDataList.add(new TimeLineModel(date, title, place, format, OrderStatus.COMPLETED));
                    if (date.contains(full)) positionForScroll = i;
                    //full = "";
                }
                Collections.reverse(mDataList);
                if (positionForScroll > 0)
                    positionForScroll = mDataList.size() - positionForScroll - 1;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressBar.setVisibility(MapView.INVISIBLE);
            mTimeLineAdapter.notifyDataSetChanged();
            mRecyclerView.scrollToPosition(positionForScroll);
            super.onPostExecute(aVoid);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Menu
        switch (item.getItemId()) {
            //When home is clicked
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        if (mOrientation != null)
            savedInstanceState.putSerializable(MainActivity.EXTRA_ORIENTATION, mOrientation);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(MainActivity.EXTRA_ORIENTATION)) {
                mOrientation = (Orientation) savedInstanceState.getSerializable(MainActivity.EXTRA_ORIENTATION);
            }
        }
        super.onRestoreInstanceState(savedInstanceState);
    }
    */
}
