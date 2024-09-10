package org.scaler.ecommerceuserservice.generators;

import com.fasterxml.uuid.impl.TimeBasedReorderedGenerator;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */
@Component
public class SortedUUIDGenerator implements IdentifierGenerator {

    private final TimeBasedReorderedGenerator timeBasedReorderedGenerator;

    @Autowired
    public SortedUUIDGenerator(TimeBasedReorderedGenerator timeBasedReorderedGenerator) {
        this.timeBasedReorderedGenerator = timeBasedReorderedGenerator;
    }
    @Override
    public Object generate(SharedSessionContractImplementor session, Object object) {
        return timeBasedReorderedGenerator.generate();
    }
}
