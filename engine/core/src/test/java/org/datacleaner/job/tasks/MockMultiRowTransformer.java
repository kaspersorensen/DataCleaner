/**
 * DataCleaner (community edition)
 * Copyright (C) 2014 Neopost - Customer Information Management
 *
 * This copyrighted material is made available to anyone wishing to use, modify,
 * copy, or redistribute it subject to the terms and conditions of the GNU
 * Lesser General Public License, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution; if not, write to:
 * Free Software Foundation, Inc.
 * 51 Franklin Street, Fifth Floor
 * Boston, MA  02110-1301  USA
 */
package org.datacleaner.job.tasks;

import javax.inject.Inject;
import javax.inject.Named;

import org.datacleaner.beans.api.Configured;
import org.datacleaner.beans.api.OutputColumns;
import org.datacleaner.beans.api.OutputRowCollector;
import org.datacleaner.beans.api.Provided;
import org.datacleaner.beans.api.Transformer;
import org.datacleaner.data.InputColumn;
import org.datacleaner.data.InputRow;
import org.junit.Ignore;

/**
 * Simple transformer with output row collector, that counts to eg.
 * (1,42)-(2,42)-(3,42), if the input is 3.
 */
@Ignore
@Named("Mock multi row transformer")
public class MockMultiRowTransformer implements Transformer {

    @Inject
    @Configured("Count to what?")
    InputColumn<Number> countToColumn;

    @Inject
    @Provided
    OutputRowCollector outputRowCollector;

    @Override
    public OutputColumns getOutputColumns() {
        return new OutputColumns(2, Number.class);
    }

    @Override
    public Number[] transform(InputRow inputRow) {
        Number countTo = inputRow.getValue(countToColumn);
        if (countTo != null) {
            int max = Math.abs(countTo.intValue());
            for (int i = 0; i < max; i++) {
                outputRowCollector.putValues(i + 1, 42);
            }
        }
        return null;
    }

}
