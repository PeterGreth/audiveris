//----------------------------------------------------------------------------//
//                                                                            //
//                           B a r l i n e T a s k                            //
//                                                                            //
//  Copyright (C) Herve Bitteur 2000-2009. All rights reserved.               //
//  This software is released under the GNU General Public License.           //
//  Please contact users@audiveris.dev.java.net to report bugs & suggestions. //
//----------------------------------------------------------------------------//
//
package omr.script;

import omr.glyph.Glyph;
import omr.glyph.Shape;

import omr.sheet.Sheet;

import omr.step.Step;

import java.util.Collection;

/**
 * Class <code>BarlineTask</code> is a script task which assigns (or deassign)
 * a barline shape to a collection of glyphs.
 *
 * <p>Il the compound flag is set, a compound glyph may is composed from the
 * provided glyphs and assigned the shape. Otherwise, each provided glyph is
 * assigned the shape.</p>
 *
 * @author Herv&eacute Bitteur
 * @version $Id$
 */
public class BarlineTask
    extends AssignTask
{
    //~ Constructors -----------------------------------------------------------

    //-------------//
    // BarlineTask //
    //-------------//
    /**
     * Create a barline assignment task
     *
     * @param shape the assigned shape (or null for a de-assignment)
     * @param compound true if all glyphs are to be merged into one compound
     * which is assigned to the given shape, false if each and every glyph is to
     * be assigned to the given shape
     * @param glyphs the collection of concerned glyphs
     */
    public BarlineTask (Shape             shape,
                        boolean           compound,
                        Collection<Glyph> glyphs)
    {
        super(shape, compound, glyphs);
    }

    //-------------//
    // BarlineTask //
    //-------------//
    /**
     * Convenient way to create a barline deassignment task
     *
     * @param glyphs the collection of glyphs to deassign
     */
    public BarlineTask (Collection<Glyph> glyphs)
    {
        super(glyphs);
    }

    //-------------//
    // BarlineTask //
    //-------------//
    /** No-arg constructor needed for JAXB */
    protected BarlineTask ()
    {
    }

    //~ Methods ----------------------------------------------------------------

    //--------//
    // epilog //
    //--------//
    @Override
    public void epilog (Sheet sheet)
    {
        sheet.getSystemsBuilder()
             .rebuildAllSystems();
        sheet.rebuildAfter(Step.SYSTEMS, sheet.getSystems());
    }

    //-----------------//
    // internalsString //
    //-----------------//
    @Override
    protected String internalsString ()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(" barline");

        return sb + super.internalsString();
    }
}