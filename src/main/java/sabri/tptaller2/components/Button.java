package sabri.tptaller2.components;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.TransferHandler;

import sabri.tptaller2.menus.RightClickListener;

public class Button extends JButton implements Transferable, DragSourceListener, DragGestureListener {
	private static final long serialVersionUID = -8207526176412412851L;
	
	private DragSource source;
    private TransferHandler handler;
    private String id;
	
    public Button(String text){
    	super(text);
    	this.setName(text);
    	this.addMouseListener(new RightClickListener());
    	
	    /** 
	     * The TransferHandler returns a new Button to be transferred in the Drag
	     */
	    handler = new TransferHandler(){
	    	
			private static final long serialVersionUID = -4971053151576772162L;

			public Transferable createTransferable(JComponent c){
	    		return new Button(getText());
	        }};
	        setTransferHandler(handler);
	 
	        //The Drag will copy the Button rather than moving it
	        source = new DragSource();
	        source.createDefaultDragGestureRecognizer(this, DnDConstants.ACTION_COPY, this);
	    }
	 
	 
    /**
     * when a DragGesture is recognized, initiate the Drag
     */
	@Override
	public void dragGestureRecognized(DragGestureEvent dge) {
		source.startDrag(dge, DragSource.DefaultMoveDrop, this, this);

	}

	@Override
	public void dragDropEnd(DragSourceDropEvent arg0) {
		this.setVisible(false);
		
		// TODO change constants!! (canvas limits)
		this.setBounds(arg0.getX() - 250, arg0.getY() - 60, 100, 30);
		this.setVisible(true);
	}

	@Override
	public void dragEnter(DragSourceDragEvent arg0) {
		// Don't care about its behaviour
	}

	@Override
	public void dragExit(DragSourceEvent arg0) {
		// Don't care about its behaviour
	}

	@Override
	public void dragOver(DragSourceDragEvent arg0) {
		// Don't care about its behaviour
	}

	@Override
	public void dropActionChanged(DragSourceDragEvent arg0) {
		// Don't care about its behaviour
	}

	@Override
	public Object getTransferData(DataFlavor arg0)
			throws UnsupportedFlavorException, IOException {
		return this;
	}

    /**
     * The DataFlavor is a marker to let the DropTarget know how to handle the Transferable
     */
	@Override
	public DataFlavor[] getTransferDataFlavors() {
		return new DataFlavor[]{new DataFlavor(Button.class, "Button")};
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor arg0) {
		return true;
	}

	/**
	 * Sets an id for the component
	 * @param id id to set
	 */
	public void setId(String id){
		this.id = id;
	}
	
	/**
	 * Returns the component id
	 * @return the id
	 */
	public String getId(){
		return this.id;
	}
	
}
