package gte.controller;

import gte.model.Model;
import gte.utils.UnsupportedImageTypeException;
import gte.view.View;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * User: Alan P. Sexton
 * Date: 22/06/13
 * Time: 17:17
 */

/**
 * Recall that the controller and related classes in the controller package are the only classes allowed to update the
 * model.
 */
public class Controller
{
    Model model;
    View view;

    /**
     * In this version of the MVC model, the <code>View</code> has to have a reference to the <code>Controller</code>
     * so that it can inform it of view events. Hence create the <code>View</code> (with the <code>Controller</code>
     * as one of its constructor parameters) after creating the <code>Controller</code>. However, the
     * <code>Controller</code> needs a reference to its <code>View</code> to tell it what to do, so create
     * the <code>Controller</code> without a <code>View</code> first, then create the <code>View</code> with
     * the <code>Controller</code> as a parameter, then add the <code>View</code> to the <code>Controller</code>.
     *
     * @param model the model to be associated with this controller
     */
    public Controller(Model model)
    {
        this.model = model;
    }

    /**
     * This implementation only allows one view to be associated with a controller at a time: it would be easy to
     * modify this to allow multiple views.
     *
     * @param view the <code>View</code> to be added to this controller
     */
    public void addView(View view)
    {
        this.view = view;
    }

    /**
     * A control logic operation: load an image from the file, update the model and tell the view to adapt accordingly
     * @param file the image file to be loaded
     * @throws IOException
     * @throws UnsupportedImageTypeException
     */
    public void loadImage(File file)
            throws IOException, UnsupportedImageTypeException
    {
        model.loadImage(file);
        view.adaptToNewImage();
    }

    /**
     * Add a new rectangle to the model and update the view accordingly
     * @param rect the rectangle to be added
     */
    public void addRect(Rectangle rect)
    {
        model.addRect(rect);
        view.repaint();
    }

    /**
     * Handle an exit request
     * @param exitStatus the exit status to be reported
     */
    public void exit(int exitStatus)
    {
        System.exit(exitStatus);
    }


    /**
     * This dummy method demonstrates how to implement a long running operation. The return value is always
     * the percentage done. The results and partial results are passed by reference parameters. In this case
     * the final results is supposed to be an integer (the number of rectangles in the model), the partial results
     * are the values of percentageDone. For a real application, the partial results could be the rows of data read
     * from a database and the final result could be the last row. Or the final result could be the output of a
     * calculation and there might be no partial results.
     *
     * @param partialResults the partial results to be reported to the caller
     * @return the percentage of the work done
     * @throws InterruptedException
     */
    public int executeLongOperationStep(Integer[] result, List<Integer> partialResults)
            throws InterruptedException
    {
        Thread.sleep(50);
        int currentPercentageDone = percentageDone.get() + 1;
        percentageDone.set(currentPercentageDone);
        if (currentPercentageDone < 100)
            partialResults.add(currentPercentageDone);
        else
            result[0] = model.getRects().size();
        return currentPercentageDone;
    }

    /**
     * This is used by the dummy <code>executeLongOperationStep</code> method. Normally this would be just an
     * integer field variable. However, if the operation is configured in the view action so that multiple instances
     * the long operation can run in parallel, then you need you ensure that variables that persist over multiple
     * steps of the method are local to the thread. If, instead, you disallow such parallelism, e.g.
     * by disabling further invocations of the action while one instance is running, or by making the dialog
     * modal (which has the same effect, then this can be a normal class field.
     */
    private static ThreadLocal<Integer> percentageDone = new ThreadLocal<Integer>()
    {
        @Override
        protected Integer initialValue()
        {
            return 0;
        }
    };


}
