//------------------------------------------------------------------------------------------------//
//                                                                                                //
//                                      P a r t B a r l i n e                                     //
//                                                                                                //
//------------------------------------------------------------------------------------------------//
// <editor-fold defaultstate="collapsed" desc="hdr">
//  Copyright © Herve Bitteur and others 2000-2014. All rights reserved.
//  This software is released under the GNU General Public License.
//  Goto http://kenai.com/projects/audiveris to report bugs or suggestions.
//------------------------------------------------------------------------------------------------//
// </editor-fold>
package omr.sheet;

import omr.math.GeoUtil;
import omr.math.PointUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Class {@code PartBarline} represents a logical barline for a part, that is composed
 * of several {@link StaffBarline} instances when the part comprises several staves.
 * <p>
 * In the case of "back to back" repeat configuration, we use two instances of this class, one
 * for the backward repeat and one for the forward repeat.
 *
 * @author Hervé Bitteur
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "part-barline")
public class PartBarline
{
    //~ Static fields/initializers -----------------------------------------------------------------

    private static final Logger logger = LoggerFactory.getLogger(PartBarline.class);

    //~ Enumerations -------------------------------------------------------------------------------
    /**
     * Barline style.
     * Identical to (or subset of) MusicXML BarStyle, to avoid strict dependency on MusicXML.
     */
    public static enum Style
    {
        //~ Enumeration constant initializers ------------------------------------------------------

        REGULAR,
        DOTTED,
        DASHED,
        HEAVY,
        LIGHT_LIGHT,
        LIGHT_HEAVY,
        HEAVY_LIGHT,
        HEAVY_HEAVY,
        TICK,
        SHORT,
        NONE;
    }

    //~ Instance fields ----------------------------------------------------------------------------
    /** * Underlying {@link StaffBarline} instances, one per staff in the part. */
    @XmlElement(name = "staff-barline")
    private final List<StaffBarline> staffBarlines = new ArrayList<StaffBarline>();

    //~ Constructors -------------------------------------------------------------------------------
    public PartBarline ()
    {
    }

    //~ Methods ------------------------------------------------------------------------------------
    //-----------------//
    // addStaffBarline //
    //-----------------//
    public void addStaffBarline (StaffBarline staffBarline)
    {
        Objects.requireNonNull(staffBarline, "Trying to add a null StaffBarline");

        staffBarlines.add(staffBarline);
    }

    //------------//
    // getBarline //
    //------------//
    public StaffBarline getBarline (Part part,
                                    Staff staff)
    {
        int index = part.getStaves().indexOf(staff);

        if (index != -1) {
            return staffBarlines.get(index);
        }

        return null;
    }

    //----------//
    // getLeftX //
    //----------//
    /**
     * Report the center abscissa of the left bar
     *
     * @param part  the containing part
     * @param staff the staff for precise ordinate
     * @return abscissa of the left side
     */
    public int getLeftX (Part part,
                         Staff staff)
    {
        StaffBarline bar = getBarline(part, staff);

        if (bar != null) {
            return bar.getLeftX();
        } else {
            throw new IllegalStateException("Part Barline with no proper StaffBarline");
        }
    }

    //-----------//
    // getRightX //
    //-----------//
    /**
     * Report the center abscissa of the right bar
     *
     * @param part  the containing part
     * @param staff the staff for precise ordinate
     * @return abscissa of the right side
     */
    public int getRightX (Part part,
                          Staff staff)
    {
        StaffBarline bar = getBarline(part, staff);

        if (bar != null) {
            return bar.getRightX();
        } else {
            throw new IllegalStateException("Part Barline with no proper StaffBarline");
        }
    }

    //----------//
    // getStyle //
    //----------//
    public Style getStyle ()
    {
        if (staffBarlines.isEmpty()) {
            return null;
        }

        return staffBarlines.get(0).getStyle();
    }

    //--------------//
    // isLeftRepeat //
    //--------------//
    public boolean isLeftRepeat ()
    {
        for (StaffBarline sb : staffBarlines) {
            if (sb.isLeftRepeat()) {
                return true;
            }
        }

        return false;
    }

    //---------------//
    // isRightRepeat //
    //---------------//
    public boolean isRightRepeat ()
    {
        for (StaffBarline sb : staffBarlines) {
            if (sb.isRightRepeat()) {
                return true;
            }
        }

        return false;
    }

    //----------//
    // toString //
    //----------//
    @Override
    public String toString ()
    {
        StringBuilder sb = new StringBuilder("{PartBarline");
        StaffBarline first = staffBarlines.get(0);
        StaffBarline last = staffBarlines.get(staffBarlines.size() - 1);

        Style style = first.getStyle();
        sb.append(" ").append(style);

        Rectangle box = new Rectangle(first.getCenter());
        box.add(last.getCenter());
        sb.append(" ").append(PointUtil.toString(GeoUtil.centerOf(box)));
        sb.append("}");

        return sb.toString();
    }
}
