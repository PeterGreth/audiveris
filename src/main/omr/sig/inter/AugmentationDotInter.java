//------------------------------------------------------------------------------------------------//
//                                                                                                //
//                             A u g m e n t a t i o n D o t I n t e r                            //
//                                                                                                //
//------------------------------------------------------------------------------------------------//
// <editor-fold defaultstate="collapsed" desc="hdr">
//  Copyright © Herve Bitteur and others 2000-2014. All rights reserved.
//  This software is released under the GNU General Public License.
//  Goto http://kenai.com/projects/audiveris to report bugs or suggestions.
//------------------------------------------------------------------------------------------------//
// </editor-fold>
package omr.sig.inter;

import omr.glyph.Glyph;
import omr.glyph.Shape;

import omr.sheet.Part;
import omr.sheet.rhythm.Voice;

import omr.sig.relation.AugmentationRelation;
import omr.sig.relation.DoubleDotRelation;
import omr.sig.relation.Relation;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Class {@code AugmentationDotInter} represent an augmentation dot for a note or a rest.
 *
 * @author Hervé Bitteur
 */
@XmlRootElement(name = "augmentation-dot")
public class AugmentationDotInter
        extends AbstractInter
{
    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new {@code AugmentationDotInter} object.
     *
     * @param glyph underlying glyph
     * @param grade evaluation value
     */
    public AugmentationDotInter (Glyph glyph,
                                 double grade)
    {
        super(glyph, null, Shape.AUGMENTATION_DOT, grade);
    }

    /**
     * No-arg constructor meant for JAXB.
     */
    private AugmentationDotInter ()
    {
    }

    //~ Methods ------------------------------------------------------------------------------------
    //---------//
    // getPart //
    //---------//
    @Override
    public Part getPart ()
    {
        if (part == null) {
            for (Relation rel : sig.getRelations(
                    this,
                    AugmentationRelation.class,
                    DoubleDotRelation.class)) {
                return part = sig.getOppositeInter(this, rel).getPart();
            }
        }

        return part;
    }

    //--------------------------//
    // getSecondAugmentationDot //
    //--------------------------//
    /**
     * Report the second augmentation dot, if any, that is linked to this (first)
     * augmentation dot.
     *
     * @return the second dot, if any, or null
     */
    public AugmentationDotInter getSecondAugmentationDot ()
    {
        for (Relation dd : sig.getRelations(this, DoubleDotRelation.class)) {
            return (AugmentationDotInter) sig.getOppositeInter(this, dd);
        }

        return null;
    }

    //----------//
    // getVoice //
    //----------//
    @Override
    public Voice getVoice ()
    {
        for (Relation rel : sig.getRelations(
                this,
                AugmentationRelation.class,
                DoubleDotRelation.class)) {
            return sig.getOppositeInter(this, rel).getVoice();
        }

        return null;
    }
}
