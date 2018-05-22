package edvard.projektuppgift;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;


public class GameView extends SurfaceView implements Runnable,View.OnTouchListener{


    Paint greenBrush,redBrush;
    Thread thread;
    private boolean run=true;
    int circleGreenX,circleGreenY,circleRedY;
    int circleRedX;
    Canvas canvas;
    SurfaceHolder surfaceHolder;
    boolean gameOn=false;
    boolean touched=false;

    public GameView(Context context){
        super(context);
        thread=new Thread(this);
        thread.start();
        surfaceHolder = getHolder();

        final Context finalContext= context;

        this.setOnClickListener(new OnClickListener() {@Override
        public void onClick(View v) {
            Intent newIntent = new Intent(finalContext, MainActivity.class);
            newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            finalContext.startActivity(newIntent);
        }//onClick
        });



    }

    private void paintBrushes(){
        greenBrush=new Paint();
        greenBrush.setColor(Color.GREEN);
        greenBrush.setStyle(Paint.Style.FILL);

        redBrush=new Paint();
        redBrush.setColor(Color.RED);
        redBrush.setStyle(Paint.Style.FILL);


    }

    public void run() {

        paintBrushes();
        circleGreenX=0;


        gameOn=true;



        while (run) {


            if (gameOn==false)
                break;



            if (!surfaceHolder.getSurface().isValid()){
                continue;
            }
            canvas= surfaceHolder.lockCanvas();
            circleGreenY=this.getHeight()/2;
            if (!touched)
            circleRedY=this.getHeight()/2;



            circleRedX=(this.getWidth()/3)*2;

            motionCircleGreen(15);
            canvas.drawCircle(circleGreenX,circleGreenY,50,greenBrush);
            canvas.drawCircle(circleRedX,circleRedY,50,redBrush);
            setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    touched=true;
                    motionCircleRed();

                    return false;
                }
            });
            surfaceHolder.unlockCanvasAndPost(canvas);
            if (circleRedX==circleGreenX&&circleRedY==circleGreenY){
                gameOn=false;
            }


        }
    }


    private void motionCircleRed(){

        if (touched==true) {
            circleRedY = this.getHeight() / 3;
            try {
                thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            touched=false;
        }

    }

    private void motionCircleGreen(int speed){ //direction
        circleGreenX+=speed;
        if (circleGreenX>this.getWidth()) //Moves the circle to its starting position outside of screen
            circleGreenX-=this.getWidth();

    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }


}


