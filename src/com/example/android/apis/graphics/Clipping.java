/*
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.apis.graphics;

import android.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.os.Bundle;
import android.view.View;

//android:label="Graphics/Clipping"
public class Clipping extends GraphicsActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new SampleView(this));
    }

    private static class SampleView extends View {
        private Paint mPaint;
        private Path mPath;

        public SampleView(Context context) {
            super(context);
            setFocusable(true);

            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setStrokeWidth(6);
            mPaint.setTextSize(16);
            mPaint.setTextAlign(Paint.Align.RIGHT);

            mPath = new Path();

        }

        private void drawScene(Canvas canvas) {
            canvas.clipRect(0, 0, 100, 100);

            canvas.drawColor(Color.WHITE);

            mPaint.setColor(Color.RED);
            canvas.drawLine(0, 0, 100, 100, mPaint);

            mPaint.setColor(Color.GREEN);
            canvas.drawCircle(30, 70, 30, mPaint);

            mPaint.setColor(Color.BLUE);
            canvas.drawText("Clipping", 50, 30, mPaint);
        }

        @Override protected void onDraw(Canvas canvas) {
//            canvas.setBitmap(drawBackground(2,800,480));
//            canvas.drawBitmap(drawBackground(2,800,480), null, null);

            Paint background = new Paint();
            background.setColor(Color.RED);
            background.setAntiAlias(true);
            int width=480, cellSize=10, height=800;
            for (int i = 0; i < width / cellSize; i++) {
                canvas.drawLine(cellSize * i, 0, cellSize * i, height, background);
            }

            for (int i = 0; i < height / cellSize; i++) {
                canvas.drawLine(0, cellSize * i, width, cellSize * i, background);
            }

            canvas.save();
            //Preconcat the current matrix with the specified translation
            canvas.translate(10, 10);
            drawScene(canvas);
            canvas.restore();

            canvas.save();
            canvas.translate(160, 20);
            //clipRect(int left, int top, int right, int bottom)
            //Intersect the current clip with the specified rectangle,
            //which is expressed in local coordinates.

//            canvas.drawColor(Color.RED);
//            canvas.clipRect(10, 10, 90, 90,Region.Op.XOR);
            canvas.clipRect(10, 10, 90, 90);
            canvas.drawColor(Color.GRAY);
            canvas.clipRect(30, 30, 70, 70, Region.Op.DIFFERENCE);

            drawScene(canvas);
            canvas.restore();

            canvas.save();
            canvas.translate(10, 160);
            mPath.reset();
            canvas.clipPath(mPath); // makes the clip empty
            mPath.addCircle(50, 50, 50, Path.Direction.CCW);
            canvas.clipPath(mPath, Region.Op.REPLACE);
            drawScene(canvas);
            canvas.restore();

            canvas.save();
            canvas.translate(160, 160);
            canvas.clipRect(0, 0, 60, 60);
            canvas.clipRect(40, 40, 100, 100, Region.Op.UNION);
            drawScene(canvas);
            canvas.restore();

            canvas.save();
            canvas.translate(10, 310);
            canvas.clipRect(0, 0, 60, 60);
            canvas.clipRect(40, 40, 100, 100, Region.Op.XOR);
            drawScene(canvas);
            canvas.restore();

            canvas.save();
            canvas.translate(160, 310);
            canvas.clipRect(0, 0, 60, 60);
            canvas.clipRect(40, 40, 100, 100, Region.Op.REVERSE_DIFFERENCE);
            drawScene(canvas);
            canvas.restore();
        }
    }

    public static Bitmap drawBackground(int cellSize, int height, int widht) {
        Bitmap bitmap = Bitmap.createBitmap(widht, height, Config.ARGB_8888);
        Canvas cv = new Canvas(bitmap);
        Paint background = new Paint();
        background.setColor(R.color.background_light);
        cv.drawRect(0, 0, widht, height, background);
        background.setAntiAlias(true);
        background.setColor(R.color.black);
        for (int i = 0; i < widht / cellSize; i++) {
            cv.drawLine(cellSize * i, 0, cellSize * i, height, background);
        }
        for (int i = 0; i < height / cellSize; i++) {
            cv.drawLine(0, cellSize * i, widht, cellSize * i, background);
        }
        return bitmap;
    }
}

