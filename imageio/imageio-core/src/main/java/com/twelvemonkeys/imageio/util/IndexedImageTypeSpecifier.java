package com.twelvemonkeys.imageio.util;

import javax.imageio.ImageTypeSpecifier;
import java.awt.image.IndexColorModel;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.Hashtable;

/**
 * IndexedImageTypeSpecifier
 *
 * @author <a href="mailto:harald.kuhr@gmail.com">Harald Kuhr</a>
 * @author last modified by $Author: haraldk$
 * @version $Id: IndexedImageTypeSpecifier.java,v 1.0 May 19, 2008 11:04:28 AM haraldk Exp$
 */
public class IndexedImageTypeSpecifier extends ImageTypeSpecifier {
    IndexedImageTypeSpecifier(IndexColorModel pColorModel) {
        // For some reason, we need a sample model
        super(pColorModel, pColorModel.createCompatibleSampleModel(1, 1));
    }

    public static ImageTypeSpecifier createFromIndexColorModel(final IndexColorModel pColorModel) {
        return new IndexedImageTypeSpecifier(pColorModel);
    }

    @Override
    public final BufferedImage createBufferedImage(int pWidth, int pHeight) {
        try {
            // This is a fix for the super-method, that first creates a sample model, and then
            // creates a raster from it, using Raster.createWritableRaster. The problem with
            // that approach, is that it always creates a TYPE_CUSTOM BufferedImage for indexed images.
            WritableRaster raster = colorModel.createCompatibleWritableRaster(pWidth, pHeight);
            return new BufferedImage(colorModel, raster, colorModel.isAlphaPremultiplied(), new Hashtable());
        }
        catch (NegativeArraySizeException e) {
            // Exception most likely thrown from a DataBuffer constructor
            throw new IllegalArgumentException("Array size > Integer.MAX_VALUE!");
        }
    }
}
