package fiuba.mda.ui.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.printing.PrintDialog;
import org.eclipse.swt.printing.Printer;
import org.eclipse.swt.printing.PrinterData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import com.google.inject.Inject;

import fiuba.mda.model.GraficInterfaceDiagram;
import fiuba.mda.ui.utilities.ImageLoader;

public class PrintDiagramAction extends Action {
	private ExportableToImage boundDiagram;
	private final Shell shell;

	/**
	 * Creates a new {@link PrintDiagramAction} instance
	 * 
	 * @param imageLoader
	 *            the image loader used to provide the image of this action
	 */
	@Inject
	public PrintDiagramAction(final Shell shell, final ImageLoader imageLoader) {
		this.shell = shell;
		setupPresentation(imageLoader);
	}
	
	private void setupPresentation(final ImageLoader imageLoader) {
		setText("Imprimir");
		setToolTipText("Imprimir el diagrama actual");
		setImageDescriptor(imageLoader.descriptorOf("printer"));
	}

	/**
	 * Binds this action to work on a given {@link GraficInterfaceDiagram} instance 
	 * 
	 * @param diagram
	 *            the diagram to bind this action to
	 * @return this for method chaining
	 */
	public PrintDiagramAction boundTo(final ExportableToImage diagram) {
        boundDiagram = diagram;
        return this;
    }

	@Override
	public void run() {
	    try {
	      // Prompt the user for an image file
	      FileDialog fileChooser = new FileDialog(shell, SWT.OPEN);
	      String fileName = fileChooser.open();

	      if (fileName == null) { return; }

	      // Load the image
	      org.eclipse.swt.graphics.ImageLoader loader = new org.eclipse.swt.graphics.ImageLoader();
	      ImageData[] imageData = loader.load(fileName);

	      if (imageData.length > 0) {
	        // Show the Choose Printer dialog
	        PrintDialog dialog = new PrintDialog(shell, SWT.NULL);
	        PrinterData printerData = dialog.open();

	        if (printerData != null) {
	          // Create the printer object
	          Printer printer = new Printer(printerData);
	  
	          // Resolution
	          int scaleFactor = 3;
	  
	          // Determine the bounds of the entire area of the printer
	          Rectangle trim = printer.computeTrim(0, 0, 0, 0);

	          // Start the print job
	          if (printer.startJob(fileName)) {
	            if (printer.startPage()) {
	              GC gc = new GC(printer);
	              Image printerImage = new Image(printer, imageData[0]);
	              
	              // Draw the image
	              gc.drawImage(printerImage, 0, 0, imageData[0].width,
	                imageData[0].height, -trim.x, -trim.y, 
	                scaleFactor * imageData[0].width, 
	                scaleFactor * imageData[0].height);
	  
	              // Clean up
	              printerImage.dispose();
	              gc.dispose();
	              printer.endPage();
	            }
	          }
	          // End the job and dispose the printer
	          printer.endJob();
	          printer.dispose();
	        }
	      }
	    } catch (Exception e) {
	      MessageBox messageBox = new MessageBox(shell, SWT.ICON_ERROR);
	      messageBox.setMessage("Error al imprimir imagen");
	      messageBox.open();
	    }
	  }
	
}
